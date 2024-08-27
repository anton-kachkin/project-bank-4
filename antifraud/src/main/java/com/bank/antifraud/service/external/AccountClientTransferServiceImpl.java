package com.bank.antifraud.service.external;

import com.bank.antifraud.dto.external.AccountClientTransferDto;
import com.bank.antifraud.feign.TransferClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountClientTransferServiceImpl implements AccountClientTransferService {

    TransferClient transferClient;

    @Override
    public AccountClientTransferDto getAccountClientTransfer(Long accountClientTransferId) {
        return transferClient.getAccountClientTransfer(accountClientTransferId);
    }
}
