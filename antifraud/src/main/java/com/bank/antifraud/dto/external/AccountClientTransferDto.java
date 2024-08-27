package com.bank.antifraud.dto.external;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountClientTransferDto implements Serializable {

    Long id;
    Long accountNumber;
    BigDecimal amount;
    String purpose;
    Long accountDetailsId;
}
