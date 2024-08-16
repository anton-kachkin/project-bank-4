package com.bank.antifraud.services;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mappers.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.service.impl.SuspiciousPhoneTransferServiceImpl;
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
public class SuspiciousPhoneTransferServiceImplTests {

    static final String MESSAGE = "SuspiciousPhoneTransfer по данному id не существует";

    static final Long id = 1L;

    static final Long id2 = 2L;

    @Mock
    SuspiciousPhoneTransferRepository phoneRepository;

    @Mock
    SuspiciousPhoneTransferMapper phoneMapper;

    @Mock
    ExceptionReturner returner;

    @InjectMocks
    SuspiciousPhoneTransferServiceImpl phoneService;

    static SuspiciousPhoneTransferDto phoneDto, phoneDto1, phoneDto2, phoneResult;

    static SuspiciousPhoneTransferEntity phoneEntity, phoneEntity1, phoneEntity2;

    static EntityNotFoundException exception;

    @BeforeEach
    public void setUp() {
        phoneDto = new SuspiciousPhoneTransferDto();
        phoneEntity = new SuspiciousPhoneTransferEntity();

        lenient().when(returner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException(MESSAGE));
    }

    @Test
    @DisplayName("Тест на успешное сохранение SuspiciousPhoneTransfer и возврат DTO")
    public void testPositiveSaveSuccess() {
        when(phoneMapper.toEntity(phoneDto)).thenReturn(phoneEntity);
        when(phoneRepository.save(phoneEntity)).thenReturn(phoneEntity);
        when(phoneMapper.toDto(phoneEntity)).thenReturn(phoneDto);

        phoneResult = phoneService.save(phoneDto);

        assertNotNull(phoneResult);
        assertEquals(phoneDto.getId(), phoneResult.getId());
        assertEquals(phoneDto.getPhoneTransferId(), phoneResult.getPhoneTransferId());
        assertEquals(phoneDto.getIsBlocked(), phoneResult.getIsBlocked());
        assertEquals(phoneDto.getIsSuspicious(), phoneResult.getIsSuspicious());
        assertEquals(phoneDto.getBlockedReason(), phoneResult.getBlockedReason());
        assertEquals(phoneDto.getSuspiciousReason(), phoneResult.getSuspiciousReason());

        verify(phoneMapper, times(1)).toEntity(phoneDto);
        verify(phoneRepository, times(1)).save(phoneEntity);
        verify(phoneMapper, times(1)).toDto(phoneEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное сохранение SuspiciousPhoneTransfer и возврат DTO")
    public void testNegativeSaveFailure() {
        when(phoneMapper.toEntity(phoneDto)).thenReturn(phoneEntity);
        when(phoneRepository.save(phoneEntity)).thenThrow(new EntityNotFoundException("Ошибка базы данных"));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            phoneService.save(phoneDto);
        });

        assertEquals("Ошибка базы данных", exception.getMessage());

        verify(phoneMapper, times(1)).toEntity(phoneDto);
        verify(phoneRepository, times(1)).save(phoneEntity);
        verify(phoneMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Тест на успешное нахождение SuspiciousPhoneTransfer по ID и возврат DTO")
    public void testPositiveFindByIdSuccess() {
        when(phoneRepository.findById(id)).thenReturn(Optional.of(phoneEntity));
        when(phoneMapper.toDto(phoneEntity)).thenReturn(phoneDto);

        phoneResult = phoneService.findById(id);

        assertNotNull(phoneResult);
        assertEquals(phoneDto.getId(), phoneResult.getId());
        assertEquals(phoneDto.getPhoneTransferId(), phoneResult.getPhoneTransferId());
        assertEquals(phoneDto.getIsBlocked(), phoneResult.getIsBlocked());
        assertEquals(phoneDto.getIsSuspicious(), phoneResult.getIsSuspicious());
        assertEquals(phoneDto.getBlockedReason(), phoneResult.getBlockedReason());
        assertEquals(phoneDto.getSuspiciousReason(), phoneResult.getSuspiciousReason());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneMapper, times(1)).toDto(phoneEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение SuspiciousPhoneTransfer по ID и возврат DTO")
    public void testNegativeFindByIdFailure() {
        when(phoneRepository.findById(id)).thenReturn(Optional.empty());

        exception = assertThrows(EntityNotFoundException.class, () -> {
            phoneService.findById(id);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Тест на успешное обновление SuspiciousPhoneTransfer и возврат DTO")
    public void testPositiveUpdateSuccess() {
        when(phoneRepository.findById(anyLong())).thenReturn(Optional.of(phoneEntity));
        when(phoneMapper.mergeToEntity(any(SuspiciousPhoneTransferDto.class), any(SuspiciousPhoneTransferEntity.class)))
                .thenReturn(phoneEntity);
        when(phoneRepository.save(any(SuspiciousPhoneTransferEntity.class))).thenReturn(phoneEntity);
        when(phoneMapper.toDto(any(SuspiciousPhoneTransferEntity.class))).thenReturn(phoneDto);

        phoneResult = phoneService.update(id, phoneDto);

        assertNotNull(phoneResult);
        assertEquals(phoneDto.getId(), phoneResult.getId());
        assertEquals(phoneDto.getPhoneTransferId(), phoneResult.getPhoneTransferId());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneRepository, times(1)).findById(id);
        verify(phoneMapper, times(1)).mergeToEntity(phoneDto, phoneEntity);
        verify(phoneRepository, times(1)).save(phoneEntity);
        verify(phoneMapper, times(1)).toDto(phoneEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное обновление SuspiciousPhoneTransfer и возврат DTO")
    public void testNegativeUpdateEntityNotFound() {
        when(phoneRepository.findById(anyLong())).thenReturn(Optional.empty());

        exception = assertThrows(EntityNotFoundException.class, () -> {
            phoneService.update(id, phoneDto);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneMapper, times(0)).mergeToEntity(any(SuspiciousPhoneTransferDto.class),
                any(SuspiciousPhoneTransferEntity.class));
        verify(phoneRepository, times(0)).save(any(SuspiciousPhoneTransferEntity.class));
        verify(phoneMapper, times(0)).toDto(any(SuspiciousPhoneTransferEntity.class));
    }

    @Test
    @DisplayName("Тест на успешное нахождение всех SuspiciousPhoneTransfer по ID и возврат DTO")
    public void testPositiveFindAllByIdSuccess() {
        List<Long> ids = Arrays.asList(id, id2);
        phoneEntity1 = TestFactoryServices.createSuspiciousPhoneTransferEntity();
        phoneEntity2 = TestFactoryServices.createSuspiciousPhoneTransferEntity();
        phoneEntity2.setId(id2);

        phoneDto1 = TestFactoryServices.createSuspiciousPhoneTransferDto();
        phoneDto2 = TestFactoryServices.createSuspiciousPhoneTransferDto();
        phoneDto2.setId(id2);

        when(phoneRepository.findById(id)).thenReturn(Optional.of(phoneEntity1));
        when(phoneRepository.findById(id2)).thenReturn(Optional.of(phoneEntity2));
        when(phoneMapper.toListDto(anyList())).thenReturn(Arrays.asList(phoneDto1, phoneDto2));

        List<SuspiciousPhoneTransferDto> result = phoneService.findAllById(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(phoneDto1, result.get(0));
        assertEquals(phoneDto2, result.get(1));

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneRepository, times(1)).findById(id2);
        verify(phoneMapper, times(1)).toListDto(anyList());
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение всех SuspiciousPhoneTransfer по ID и возврат DTO")
    public void testNegativeFindAllByIdEntityNotFound() {
        List<Long> ids = Arrays.asList(id, id2);

        when(phoneRepository.findById(id))
                .thenReturn(Optional.of(TestFactoryServices.createSuspiciousPhoneTransferEntity()));
        when(phoneRepository.findById(id2)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(anyString())).thenReturn(new EntityNotFoundException(MESSAGE));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            phoneService.findAllById(ids);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(phoneRepository, times(1)).findById(id);
        verify(phoneRepository, times(1)).findById(id2);
        verify(phoneMapper, times(0)).toListDto(anyList());
    }
}
