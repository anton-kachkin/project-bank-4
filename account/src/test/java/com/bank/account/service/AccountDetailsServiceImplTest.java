package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.common.ExceptionReturner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceImplTest {

    @Mock
    private AccountDetailsMapper mapper;
    @Mock
    private AccountDetailsRepository repository;
    @Mock
    private ExceptionReturner exceptionReturner;
    @InjectMocks
    private AccountDetailsServiceImpl service;

    private AccountDetailsEntity accountDetailsEntity;
    private AccountDetailsDto accountDetailsDto;

    @BeforeEach
    void setUp() {
        accountDetailsEntity = new AccountDetailsEntity();
        accountDetailsEntity.setId(1L);
        accountDetailsEntity.setAccountNumber(1234L);
        accountDetailsEntity.setBankDetailsId(100L);
        accountDetailsEntity.setMoney(new BigDecimal(1000));
        accountDetailsEntity.setPassportId(200L);
        accountDetailsEntity.setNegativeBalance(false);
        accountDetailsEntity.setProfileId(300L);

        accountDetailsDto = new AccountDetailsDto();
        accountDetailsDto.setId(1L);
        accountDetailsDto.setAccountNumber(1234L);
        accountDetailsDto.setBankDetailsId(100L);
        accountDetailsDto.setMoney(new BigDecimal(1000));
        accountDetailsDto.setPassportId(200L);
        accountDetailsDto.setNegativeBalance(false);
        accountDetailsDto.setProfileId(300L);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findById_IdIsValid_ReturnsAccountDetailsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(accountDetailsEntity));
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = service.findById(1L);

        assertEquals(accountDetailsDto, result);
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toDto(accountDetailsEntity);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findById_IdIsInvalid_ThrowsEntityNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenThrow(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1))
                .getEntityNotFoundException("Не существующий id = 1");
    }

    @Test
    @DisplayName("Поиск по ids, позитивный сценарий")
    void findAllById_IdsAreValid_ReturnsAccountDetailsDtoList() {
        AccountDetailsEntity accountDetailsEntity1 = new AccountDetailsEntity();
        accountDetailsEntity1.setId(2L);
        accountDetailsEntity1.setAccountNumber(2234L);
        accountDetailsEntity1.setBankDetailsId(200L);
        accountDetailsEntity1.setMoney(new BigDecimal(2000));
        accountDetailsEntity1.setPassportId(300L);
        accountDetailsEntity1.setNegativeBalance(false);
        accountDetailsEntity1.setProfileId(2300L);

        AccountDetailsDto accountDetailsDto1 = new AccountDetailsDto();
        accountDetailsDto1.setId(2L);
        accountDetailsDto1.setAccountNumber(2234L);
        accountDetailsDto1.setBankDetailsId(200L);
        accountDetailsDto1.setMoney(new BigDecimal(2000));
        accountDetailsDto1.setPassportId(300L);
        accountDetailsDto1.setNegativeBalance(false);
        accountDetailsDto1.setProfileId(2300L);

        List<Long> ids = List.of(1L, 2L);

        when(repository.findById(1L)).thenReturn(Optional.of(accountDetailsEntity));
        when(repository.findById(2L)).thenReturn(Optional.of(accountDetailsEntity1));
        when(mapper.toDtoList(List.of(accountDetailsEntity, accountDetailsEntity1)))
                .thenReturn(List.of(accountDetailsDto, accountDetailsDto1));

        List<AccountDetailsDto> result = service.findAllById(ids);

        assertEquals(List.of(accountDetailsDto, accountDetailsDto1), result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
        verify(mapper, times(1)).toDtoList(List.of(accountDetailsEntity, accountDetailsEntity1));
    }

    @Test
    @DisplayName("Поиск по несуществующим ids, негативный сценарий")
    void findAllById_IdsAreInvalid_ThrowsEntityNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenThrow(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.findAllById(List.of(1L)));
        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1))
                .getEntityNotFoundException("Не существующий id = 1");
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void save_Positive_ReturnsAccountDetailsDto() {
        when(mapper.toEntity(accountDetailsDto)).thenReturn(accountDetailsEntity);
        when(repository.save(accountDetailsEntity)).thenReturn(accountDetailsEntity);
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = service.save(accountDetailsDto);

        assertEquals(accountDetailsDto, result);
        verify(mapper, times(1)).toEntity(accountDetailsDto);
        verify(repository, times(1)).save(accountDetailsEntity);
        verify(mapper, times(1)).toDto(accountDetailsEntity);
    }

    @Test
    @DisplayName("Сохранение, передан null в passport_id, негативный сценарий")
    void save_Negative_ThrowsDataIntegrityViolationException() {
        accountDetailsDto.setPassportId(null);
        when(mapper.toEntity(accountDetailsDto)).thenReturn(accountDetailsEntity);
        when(repository.save(accountDetailsEntity)).
                thenThrow(new DataIntegrityViolationException("could not execute statement"));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(accountDetailsDto));
        verify(mapper, times(1)).toEntity(accountDetailsDto);
        verify(repository, times(1)).save(accountDetailsEntity);
        verify(mapper, times(0)).toDto(accountDetailsEntity);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void update_IdIsValid_ReturnsAccountDetailsDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(accountDetailsEntity));
        when(mapper.mergeToEntity(accountDetailsEntity, accountDetailsDto))
                .thenReturn(accountDetailsEntity);
        when(repository.save(accountDetailsEntity)).thenReturn(accountDetailsEntity);
        when(mapper.toDto(accountDetailsEntity)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = service.update(1L, accountDetailsDto);

        assertEquals(accountDetailsDto, result);
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).mergeToEntity(accountDetailsEntity, accountDetailsDto);
        verify(repository, times(1)).save(accountDetailsEntity);
        verify(mapper, times(1)).toDto(accountDetailsEntity);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void update_IdIsInvalid_ThrowsEntityNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, accountDetailsDto));
        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1)).getEntityNotFoundException("Не существующий id = 1");
        verify(mapper, times(0)).mergeToEntity(accountDetailsEntity, accountDetailsDto);
        verify(repository, times(0)).save(accountDetailsEntity);
        verify(mapper, times(0)).toDto(accountDetailsEntity);
    }
}