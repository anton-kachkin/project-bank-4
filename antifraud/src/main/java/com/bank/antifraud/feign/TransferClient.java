package com.bank.antifraud.feign;

import com.bank.antifraud.dto.external.AccountClientTransferDto;
import com.bank.antifraud.dto.external.CardClientTransferDto;
import com.bank.antifraud.dto.external.PhoneClientTransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "transfer", url = "http://localhost:8092/api/transfer")
public interface TransferClient {

    @GetMapping("/account/read/{id}")
    AccountClientTransferDto getAccountClientTransfer(@PathVariable("id") Long accountTransferId);

    @GetMapping("/card/read/{id}")
    CardClientTransferDto getCardClientTransfer(@PathVariable("id") Long cardTransferId);

    @GetMapping("/phone/read/{id}")
    PhoneClientTransferDto getPhoneClientTransfer(@PathVariable("id") Long phoneTransferId);
}
