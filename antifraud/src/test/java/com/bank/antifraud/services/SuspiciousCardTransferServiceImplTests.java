package com.bank.antifraud.services;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.mappers.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.service.impl.SuspiciousCardTransferServiceImpl;
import com.bank.utils.TestFactoryServices;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousCardTransferServiceImplTests {

    static final String MESSAGE = "SuspiciousCardTransfer по данному id не существует";

    static final Long id = 1L;

    static final Long id2 = 2L;

    @Mock
    SuspiciousCardTransferRepository cardRepository;

    @Mock
    SuspiciousCardTransferMapper cardMapper;

    @Mock
    ExceptionReturner returner;

    @InjectMocks
    SuspiciousCardTransferServiceImpl cardService;

    static SuspiciousCardTransferDto cardDto, cardResult, cardDto1, cardDto2;

    static EntityNotFoundException exception;

    static SuspiciousCardTransferEntity cardEntity, cardEntity1, cardEntity2;

    @BeforeEach
    public void setUp() {
        cardDto = new SuspiciousCardTransferDto();
        cardEntity = new SuspiciousCardTransferEntity();

        lenient().when(returner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException(MESSAGE));
    }

    @Test
    @DisplayName("Тест на успешное сохранение SuspiciousCardTransfer и возврат DTO")
    public void testPositiveSaveSuccess() {
        when(cardMapper.toEntity(cardDto)).thenReturn(cardEntity);
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);
        when(cardMapper.toDto(cardEntity)).thenReturn(cardDto);

        cardResult = cardService.save(cardDto);

        assertNotNull(cardResult);
        assertEquals(cardDto.getId(), cardResult.getId());
        assertEquals(cardDto.getCardTransferId(), cardResult.getCardTransferId());
        assertEquals(cardDto.getIsBlocked(), cardResult.getIsBlocked());
        assertEquals(cardDto.getIsSuspicious(), cardResult.getIsSuspicious());
        assertEquals(cardDto.getBlockedReason(), cardResult.getBlockedReason());
        assertEquals(cardDto.getSuspiciousReason(), cardResult.getSuspiciousReason());

        verify(cardMapper, times(1)).toEntity(cardDto);
        verify(cardRepository, times(1)).save(cardEntity);
        verify(cardMapper, times(1)).toDto(cardEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное сохранение SuspiciousCardTransfer и возврат DTO")
    public void testNegativeSaveFailure() {
        when(cardMapper.toEntity(cardDto)).thenReturn(cardEntity);
        when(cardRepository.save(cardEntity)).thenThrow(new EntityNotFoundException("Ошибка базы данных"));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            cardService.save(cardDto);
        });

        assertEquals("Ошибка базы данных", exception.getMessage());

        verify(cardMapper, times(1)).toEntity(cardDto);
        verify(cardRepository, times(1)).save(cardEntity);
        verify(cardMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Тест на успешное нахождение SuspiciousCardTransfer по ID и возврат DTO")
    public void testPositiveFindByIdSuccess() {
        when(cardRepository.findById(id)).thenReturn(Optional.of(cardEntity));
        when(cardMapper.toDto(cardEntity)).thenReturn(cardDto);

        cardResult = cardService.findById(id);

        assertNotNull(cardResult);
        assertEquals(cardDto.getId(), cardResult.getId());
        assertEquals(cardDto.getCardTransferId(), cardResult.getCardTransferId());
        assertEquals(cardDto.getIsBlocked(), cardResult.getIsBlocked());
        assertEquals(cardDto.getIsSuspicious(), cardResult.getIsSuspicious());
        assertEquals(cardDto.getBlockedReason(), cardResult.getBlockedReason());
        assertEquals(cardDto.getSuspiciousReason(), cardResult.getSuspiciousReason());

        verify(cardRepository, times(1)).findById(id);
        verify(cardMapper, times(1)).toDto(cardEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение SuspiciousCardTransfer по ID и возврат DTO")
    public void testNegativeFindByIdFailure() {
        when(cardRepository.findById(id)).thenReturn(Optional.empty());

        exception = assertThrows(EntityNotFoundException.class, () -> {
            cardService.findById(id);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(cardRepository, times(1)).findById(id);
        verify(cardMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Тест на успешное обновление SuspiciousCardTransfer и возврат DTO")
    public void testPositiveUpdateSuccess() {
        when(cardRepository.findById(anyLong())).thenReturn(Optional.of(cardEntity));
        when(cardMapper.mergeToEntity(any(SuspiciousCardTransferDto.class), any(SuspiciousCardTransferEntity.class)))
                .thenReturn(cardEntity);
        when(cardRepository.save(any(SuspiciousCardTransferEntity.class))).thenReturn(cardEntity);
        when(cardMapper.toDto(any(SuspiciousCardTransferEntity.class))).thenReturn(cardDto);

        cardResult = cardService.update(id, cardDto);

        assertNotNull(cardResult);
        assertEquals(cardDto.getId(), cardResult.getId());
        assertEquals(cardDto.getCardTransferId(), cardResult.getCardTransferId());

        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).findById(id);
        verify(cardMapper, times(1)).mergeToEntity(cardDto, cardEntity);
        verify(cardRepository, times(1)).save(cardEntity);
        verify(cardMapper, times(1)).toDto(cardEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное обновление SuspiciousCardTransfer и возврат DTO")
    public void testNegativeUpdateEntityNotFound() {
        when(cardRepository.findById(anyLong())).thenReturn(Optional.empty());

        exception = assertThrows(EntityNotFoundException.class, () -> {
            cardService.update(id, cardDto);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(cardRepository, times(1)).findById(id);
        verify(cardMapper, times(0)).mergeToEntity(any(SuspiciousCardTransferDto.class),
                any(SuspiciousCardTransferEntity.class));
        verify(cardRepository, times(0)).save(any(SuspiciousCardTransferEntity.class));
        verify(cardMapper, times(0)).toDto(any(SuspiciousCardTransferEntity.class));
    }

    @Test
    @DisplayName("Тест на успешное нахождение всех SuspiciousCardTransfer по ID и возврат DTO")
    public void testPositiveFindAllByIdSuccess() {
        List<Long> ids = Arrays.asList(id, id2);
        cardEntity1 = TestFactoryServices.createSuspiciousCardTransferEntity();
        cardEntity2 = TestFactoryServices.createSuspiciousCardTransferEntity();
        cardEntity2.setId(id2);

        cardDto1 = TestFactoryServices.createSuspiciousCardTransferDto();
        cardDto2 = TestFactoryServices.createSuspiciousCardTransferDto();
        cardDto2.setId(id2);

        when(cardRepository.findById(id)).thenReturn(Optional.of(cardEntity1));
        when(cardRepository.findById(id2)).thenReturn(Optional.of(cardEntity2));
        when(cardMapper.toListDto(anyList())).thenReturn(Arrays.asList(cardDto1, cardDto2));

        List<SuspiciousCardTransferDto> result = cardService.findAllById(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cardDto1, result.get(0));
        assertEquals(cardDto2, result.get(1));

        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).findById(id2);
        verify(cardMapper, times(1)).toListDto(anyList());
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение всех SuspiciousCardTransfer по ID и возврат DTO")
    public void testNegativeFindAllByIdEntityNotFound() {
        List<Long> ids = Arrays.asList(id, id2);

        when(cardRepository.findById(id))
                .thenReturn(Optional.of(TestFactoryServices.createSuspiciousCardTransferEntity()));
        when(cardRepository.findById(id2)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(anyString())).thenReturn(new EntityNotFoundException(MESSAGE));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            cardService.findAllById(ids);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).findById(id2);
        verify(cardMapper, times(0)).toListDto(anyList());
    }
}
