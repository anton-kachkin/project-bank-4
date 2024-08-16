package com.bank.antifraud.services;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mappers.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.service.impl.SuspiciousAccountTransferServiceImpl;
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
public class SuspiciousAccountTransferServiceImplTests {

    static final String MESSAGE = "SuspiciousAccountTransfer по данному id не существует";

    static final Long id = 1L;

    static final Long id2 = 2L;

    @Mock
    SuspiciousAccountTransferRepository accountRepository;

    @Mock
    SuspiciousAccountTransferMapper accountMapper;

    @Mock
    ExceptionReturner returner;

    @InjectMocks
    SuspiciousAccountTransferServiceImpl accountService;

    static SuspiciousAccountTransferDto accountDto, accountResult, accountDto1, accountDto2;

    static SuspiciousAccountTransferEntity accountEntity, accountEntity1, accountEntity2;

    static EntityNotFoundException exception;

    @BeforeEach
    public void setUp() {
        accountDto = TestFactoryServices.createSuspiciousAccountTransferDto();
        accountEntity = TestFactoryServices.createSuspiciousAccountTransferEntity();

        lenient().when(returner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException(MESSAGE));
    }

    @Test
    @DisplayName("Тест на успешное сохранение SuspiciousAccountTransfer и возврат DTO")
    public void testPositiveSaveSuccess() {
        when(accountMapper.toEntity(accountDto)).thenReturn(accountEntity);
        when(accountRepository.save(accountEntity)).thenReturn(accountEntity);
        when(accountMapper.toDto(accountEntity)).thenReturn(accountDto);

        accountResult = accountService.save(accountDto);

        assertNotNull(accountResult);
        assertEquals(accountDto.getId(), accountResult.getId());
        assertEquals(accountDto.getAccountTransferId(), accountResult.getAccountTransferId());
        assertEquals(accountDto.getIsBlocked(), accountResult.getIsBlocked());
        assertEquals(accountDto.getIsSuspicious(), accountResult.getIsSuspicious());
        assertEquals(accountDto.getBlockedReason(), accountResult.getBlockedReason());
        assertEquals(accountDto.getSuspiciousReason(), accountResult.getSuspiciousReason());

        verify(accountMapper, times(1)).toEntity(accountDto);
        verify(accountRepository, times(1)).save(accountEntity);
        verify(accountMapper, times(1)).toDto(accountEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное сохранение SuspiciousAccountTransfer и возврат DTO")
    public void testNegativeSaveFailure() {
        when(accountMapper.toEntity(accountDto)).thenReturn(accountEntity);
        when(accountRepository.save(accountEntity)).thenThrow(new EntityNotFoundException("Ошибка базы данных"));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            accountService.save(accountDto);
        });

        assertEquals("Ошибка базы данных", exception.getMessage());

        verify(accountMapper, times(1)).toEntity(accountDto);
        verify(accountRepository, times(1)).save(accountEntity);
        verify(accountMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Тест на успешное нахождение SuspiciousAccountTransfer по ID и возврат DTO")
    public void testPositiveFindByIdSuccess() {
        when(accountRepository.findById(id)).thenReturn(Optional.of(accountEntity));
        when(accountMapper.toDto(accountEntity)).thenReturn(accountDto);

        accountResult = accountService.findById(id);

        assertNotNull(accountResult);
        assertEquals(accountDto.getId(), accountResult.getId());
        assertEquals(accountDto.getAccountTransferId(), accountResult.getAccountTransferId());
        assertEquals(accountDto.getIsBlocked(), accountResult.getIsBlocked());
        assertEquals(accountDto.getIsSuspicious(), accountResult.getIsSuspicious());
        assertEquals(accountDto.getBlockedReason(), accountResult.getBlockedReason());
        assertEquals(accountDto.getSuspiciousReason(), accountResult.getSuspiciousReason());

        verify(accountRepository, times(1)).findById(id);
        verify(accountMapper, times(1)).toDto(accountEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение SuspiciousAccountTransfer по ID и возврат DTO")
    public void testNegativeFindByIdFailure() {
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        exception = assertThrows(EntityNotFoundException.class, () -> {
            accountService.findById(id);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(accountRepository, times(1)).findById(id);
        verify(accountMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Тест на успешное обновление SuspiciousAccountTransfer и возврат DTO")
    public void testPositiveUpdateSuccess() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(accountEntity));

        when(accountMapper.mergeToEntity(any(SuspiciousAccountTransferDto.class),
                any(SuspiciousAccountTransferEntity.class))).thenReturn(accountEntity);
        when(accountRepository.save(any(SuspiciousAccountTransferEntity.class))).thenReturn(accountEntity);
        when(accountMapper.toDto(any(SuspiciousAccountTransferEntity.class))).thenReturn(accountDto);

        accountResult = accountService.update(id, accountDto);

        assertNotNull(accountResult);
        assertEquals(accountDto.getId(), accountResult.getId());
        assertEquals(accountDto.getAccountTransferId(), accountResult.getAccountTransferId());

        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).findById(id);
        verify(accountMapper, times(1)).mergeToEntity(accountDto, accountEntity);
        verify(accountRepository, times(1)).save(accountEntity);
        verify(accountMapper, times(1)).toDto(accountEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное обновление SuspiciousAccountTransfer и возврат DTO")
    public void testNegativeUpdateEntityNotFound() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        exception = assertThrows(EntityNotFoundException.class, () -> {
            accountService.update(id, accountDto);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(accountRepository, times(1)).findById(id);
        verify(accountMapper, times(0)).mergeToEntity(any(SuspiciousAccountTransferDto.class),
                any(SuspiciousAccountTransferEntity.class));
        verify(accountRepository, times(0)).save(any(SuspiciousAccountTransferEntity.class));
        verify(accountMapper, times(0)).toDto(any(SuspiciousAccountTransferEntity.class));
    }

    @Test
    @DisplayName("Тест на успешное нахождение всех SuspiciousAccountTransfer по ID и возврат DTO")
    public void testPositiveFindAllByIdSuccess() {
        List<Long> ids = Arrays.asList(id, id2);
        accountEntity1 = TestFactoryServices.createSuspiciousAccountTransferEntity();
        accountEntity2 = TestFactoryServices.createSuspiciousAccountTransferEntity();
        accountEntity2.setId(id2);

        accountDto1 = TestFactoryServices.createSuspiciousAccountTransferDto();
        accountDto2 = TestFactoryServices.createSuspiciousAccountTransferDto();
        accountDto2.setId(id2);

        when(accountRepository.findById(id)).thenReturn(Optional.of(accountEntity1));
        when(accountRepository.findById(id2)).thenReturn(Optional.of(accountEntity2));
        when(accountMapper.toListDto(anyList())).thenReturn(Arrays.asList(accountDto1, accountDto2));

        List<SuspiciousAccountTransferDto> result = accountService.findAllById(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(accountDto1, result.get(0));
        assertEquals(accountDto2, result.get(1));

        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).findById(id2);
        verify(accountMapper, times(1)).toListDto(anyList());
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение всех SuspiciousAccountTransfer по ID и возврат DTO")
    public void testNegativeFindAllByIdEntityNotFound() {
        List<Long> ids = Arrays.asList(id, id2);

        when(accountRepository.findById(id))
                .thenReturn(Optional.of(TestFactoryServices.createSuspiciousAccountTransferEntity()));
        when(accountRepository.findById(id2)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(anyString())).thenReturn(new EntityNotFoundException(MESSAGE));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            accountService.findAllById(ids);
        });

        assertEquals(MESSAGE, exception.getMessage());

        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).findById(id2);
        verify(accountMapper, times(0)).toListDto(anyList());
    }
}
