package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsControllerTest {

    @Mock
    private AccountDetailsService service;
    @InjectMocks
    private AccountDetailsController controller;

    private AccountDetailsDto accountDetailsDto;

    @BeforeEach
    void setUp() {
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
    @DisplayName("Чтение по id, позитивный сценарий")
    void read_IdIsValid_ReturnsAccountDetailsDto() {
        when(service.findById(1L)).thenReturn(accountDetailsDto);

        AccountDetailsDto result = controller.read(1L);

        assertEquals(accountDetailsDto, result);
        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void read_IdIsInvalid_ThrowsEntityNotFoundException() {
        when(service.findById(1L)).thenThrow(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> controller.read(1L));
        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void create_Positive_ReturnsResponseEntity() {
        when(service.save(accountDetailsDto)).thenReturn(accountDetailsDto);

        ResponseEntity<AccountDetailsDto> result = controller.create(accountDetailsDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(accountDetailsDto, result.getBody());
        verify(service, times(1)).save(accountDetailsDto);
    }

    @Test
    @DisplayName("Создание, дубликат id, негативный сценарий")
    void create_Negative_ThrowsDataIntegrityViolationException() {
        when(service.save(accountDetailsDto))
                .thenThrow(new DataIntegrityViolationException("could not execute statement"));

        assertThrows(DataIntegrityViolationException.class, () -> controller.create(accountDetailsDto));
        verify(service, times(1)).save(accountDetailsDto);
    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void update_Positive_ReturnsResponseEntity() {
        when(service.update(1L, accountDetailsDto)).thenReturn(accountDetailsDto);

        ResponseEntity<AccountDetailsDto> result = controller.update(1L, accountDetailsDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(accountDetailsDto, result.getBody());
        verify(service, times(1)).update(1L, accountDetailsDto);
    }

    @Test
    @DisplayName("Обновление, несуществующий id, негативный сценарий")
    void update_Negative_ThrowsEntityNotFoundException() {
        when(service.update(1L, accountDetailsDto))
                .thenThrow(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> controller.update(1L, accountDetailsDto));
        verify(service, times(1)).update(1L, accountDetailsDto);
    }

    @Test
    @DisplayName("Чтение всех, позитивный сценарий")
    void readAll_Positive_ReturnsResponseEntity() {
        when(service.findAllById(List.of(1L))).thenReturn(List.of(accountDetailsDto));

        ResponseEntity<List<AccountDetailsDto>> result = controller.readAll(List.of(1L));

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(accountDetailsDto), result.getBody());
        verify(service, times(1)).findAllById(List.of(1L));
    }

    @Test
    @DisplayName("Чтение всех, несуществующие ids, негативный сценарий")
    void readAll_Negative_ThrowsEntityNotFoundException() {
        when(service.findAllById(List.of(1L)))
                .thenThrow(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> controller.readAll(List.of(1L)));
        verify(service, times(1)).findAllById(List.of(1L));
    }
}
