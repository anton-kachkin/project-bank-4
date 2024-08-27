package com.bank.antifraud.services.external;

import com.bank.antifraud.dto.external.AccountClientTransferDto;
import com.bank.antifraud.feign.TransferClient;
import com.bank.antifraud.service.external.AccountClientTransferServiceImpl;
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

import static com.bank.utils.external.TestFactoryClientTransfer.createAccountClientTransferDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountClientTransferServiceImplTests {

    @Mock
    TransferClient transferClient;

    @InjectMocks
    AccountClientTransferServiceImpl accountClientTransferService;

    static AccountClientTransferDto accountClientTransferDto, accountClientTransferResult;
    static EntityNotFoundException exception;

    @BeforeEach
    public void setUp() {
        accountClientTransferDto = createAccountClientTransferDto();
    }

    @Test
    @DisplayName("Тест на успешное получение данных о переводе на счёт по ID 1")
    public void testPositiveGetAccountClientTransferSuccess() {
        accountClientTransferDto = createAccountClientTransferDto();
        when(transferClient.getAccountClientTransfer(1L)).thenReturn(accountClientTransferDto);

        accountClientTransferResult = accountClientTransferService.getAccountClientTransfer(1L);

        assertNotNull(accountClientTransferResult);
        assertEquals(accountClientTransferDto.getId(), accountClientTransferResult.getId());
        assertEquals(accountClientTransferDto.getAccountNumber(), accountClientTransferResult.getAccountNumber());
        assertEquals(accountClientTransferDto.getAmount(), accountClientTransferResult.getAmount());
        assertEquals(accountClientTransferDto.getPurpose(), accountClientTransferResult.getPurpose());
        assertEquals(accountClientTransferDto.getAccountDetailsId(), accountClientTransferResult.getAccountDetailsId());
    }

    @Test
    @DisplayName("Тест на неуспешное получение данных о переводе на счёт по несуществующему ID")
    public void testNegativeGetAccountClientTransferNotFound() {
        when(transferClient.getAccountClientTransfer(1L))
                .thenThrow(new EntityNotFoundException("AccountClientTransfer по данному id не существует"));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            accountClientTransferService.getAccountClientTransfer(1L);
        });

        assertEquals("AccountClientTransfer по данному id не существует", exception.getMessage());
    }
}
