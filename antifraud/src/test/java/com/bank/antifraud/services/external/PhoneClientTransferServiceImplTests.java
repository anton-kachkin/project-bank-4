package com.bank.antifraud.services.external;

import com.bank.antifraud.dto.external.PhoneClientTransferDto;
import com.bank.antifraud.feign.TransferClient;
import com.bank.antifraud.service.external.PhoneClientTransferServiceImpl;
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

import static com.bank.utils.external.TestFactoryClientTransfer.createPhoneClientTransferDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneClientTransferServiceImplTests {

    @Mock
    TransferClient transferClient;

    @InjectMocks
    PhoneClientTransferServiceImpl phoneClientTransferService;

    static PhoneClientTransferDto phoneClientTransferDto, phoneClientTransferResult;
    static EntityNotFoundException exception;

    @BeforeEach
    public void setUp() {
        phoneClientTransferDto = createPhoneClientTransferDto();
    }

    @Test
    @DisplayName("Тест на успешное получение данных о телефонном переводе по ID 1")
    public void testPositiveGetPhoneClientTransferSuccess() {
        phoneClientTransferDto = createPhoneClientTransferDto();
        when(transferClient.getPhoneClientTransfer(1L)).thenReturn(phoneClientTransferDto);

        phoneClientTransferResult = phoneClientTransferService.getPhoneClientTransfer(1L);

        assertNotNull(phoneClientTransferResult);
        assertEquals(phoneClientTransferDto.getId(), phoneClientTransferResult.getId());
        assertEquals(phoneClientTransferDto.getPhoneNumber(), phoneClientTransferResult.getPhoneNumber());
        assertEquals(phoneClientTransferDto.getAmount(), phoneClientTransferResult.getAmount());
        assertEquals(phoneClientTransferDto.getPurpose(), phoneClientTransferResult.getPurpose());
        assertEquals(phoneClientTransferDto.getAccountDetailsId(), phoneClientTransferResult.getAccountDetailsId());
    }

    @Test
    @DisplayName("Тест на неуспешное получение данных о телефонном переводе по несуществующему ID")
    public void testNegativeGetPhoneClientTransferNotFound() {
        when(transferClient.getPhoneClientTransfer(1L))
                .thenThrow(new EntityNotFoundException("PhoneClientTransfer по данному id не существует"));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            phoneClientTransferService.getPhoneClientTransfer(1L);
        });

        assertEquals("PhoneClientTransfer по данному id не существует", exception.getMessage());
    }
}
