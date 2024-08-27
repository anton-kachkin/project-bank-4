package com.bank.antifraud.services.external;

import com.bank.antifraud.dto.external.CardClientTransferDto;
import com.bank.antifraud.feign.TransferClient;
import com.bank.antifraud.service.external.CardClientTransferServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static com.bank.utils.external.TestFactoryClientTransfer.createCardClientTransferDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardClientTransferServiceImplTests {

    @Mock
    TransferClient transferClient;

    @InjectMocks
    CardClientTransferServiceImpl cardClientTransferService;

    static CardClientTransferDto cardClientTransferDto, cardClientTransferResult;
    static EntityNotFoundException exception;

    @Test
    @DisplayName("Тест на успешное получение данных о переводе по карте по ID 1")
    public void testPositiveGetCardClientTransferSuccess() {
        cardClientTransferDto = createCardClientTransferDto();
        when(transferClient.getCardClientTransfer(1L)).thenReturn(cardClientTransferDto);

        cardClientTransferResult = cardClientTransferService.getCardClientTransfer(1L);

        assertNotNull(cardClientTransferResult);
        assertEquals(cardClientTransferDto.getId(), cardClientTransferResult.getId());
        assertEquals(cardClientTransferDto.getCardNumber(), cardClientTransferResult.getCardNumber());
        assertEquals(cardClientTransferDto.getAmount(), cardClientTransferResult.getAmount());
        assertEquals(cardClientTransferDto.getPurpose(), cardClientTransferResult.getPurpose());
        assertEquals(cardClientTransferDto.getAccountDetailsId(), cardClientTransferResult.getAccountDetailsId());
    }

    @Test
    @DisplayName("Тест на неуспешное получение данных о переводе по карте по несуществующему ID")
    public void testNegativeGetCardClientTransferNotFound() {
        when(transferClient.getCardClientTransfer(1L))
                .thenThrow(new EntityNotFoundException("CardClientTransfer по данному id не существует"));

        exception = assertThrows(EntityNotFoundException.class, () -> {
            cardClientTransferService.getCardClientTransfer(1L);
        });

        assertEquals("CardClientTransfer по данному id не существует", exception.getMessage());
    }
}
