package com.bank.utils.external;

import com.bank.antifraud.dto.external.AccountClientTransferDto;
import com.bank.antifraud.dto.external.CardClientTransferDto;
import com.bank.antifraud.dto.external.PhoneClientTransferDto;

import java.math.BigDecimal;

public class TestFactoryClientTransfer {

    public static AccountClientTransferDto createAccountClientTransferDto() {
        return new AccountClientTransferDto(
                1L,
                4243242L,
                BigDecimal.valueOf(250.75),
                "Ежемесячный платеж",
                456L
        );
    }

    public static CardClientTransferDto createCardClientTransferDto() {
        return new CardClientTransferDto(
                1L,
                3254765436546785L,
                BigDecimal.valueOf(350.20),
                "Оплата за услуги",
                321L
        );
    }

    public static PhoneClientTransferDto createPhoneClientTransferDto() {
        return new PhoneClientTransferDto(
                1L,
                79992345678L,
                BigDecimal.valueOf(114.73),
                "Возврат денежных средств",
                43223L
        );
    }
}
