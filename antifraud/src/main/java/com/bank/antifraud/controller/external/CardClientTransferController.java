package com.bank.antifraud.controller.external;

import com.bank.antifraud.dto.external.CardClientTransferDto;
import com.bank.antifraud.service.external.CardClientTransferService;
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
@RequestMapping("/card")
public class CardClientTransferController {

    CardClientTransferService cardClientService;

    @GetMapping("/read/{id}")
    public ResponseEntity<CardClientTransferDto> getCardClientTransfer(@PathVariable("id") Long cardClientTransferId) {
        final CardClientTransferDto cardClientTransfer = cardClientService.getCardClientTransfer(cardClientTransferId);
        return ResponseEntity.ok(cardClientTransfer);
    }
}
