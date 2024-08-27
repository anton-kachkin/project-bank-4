package com.bank.antifraud.service.external;

import com.bank.antifraud.dto.external.AccountClientTransferDto;

public interface AccountClientTransferService {

    AccountClientTransferDto getAccountClientTransfer(Long accountClientTransferId);
}
