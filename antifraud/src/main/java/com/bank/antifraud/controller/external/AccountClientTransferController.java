package com.bank.antifraud.controller.external;

import com.bank.antifraud.dto.external.AccountClientTransferDto;
import com.bank.antifraud.service.external.AccountClientTransferService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/account")
public class AccountClientTransferController {

    AccountClientTransferService accountClientService;

    @GetMapping("/read/{id}")
    public ResponseEntity<AccountClientTransferDto> getAccountClientTransfer(
            @PathVariable("id") Long accountClientTransferId) {

        final AccountClientTransferDto accountClientTransfer = accountClientService
                .getAccountClientTransfer(accountClientTransferId);
        return ResponseEntity.ok(accountClientTransfer);
    }
}
