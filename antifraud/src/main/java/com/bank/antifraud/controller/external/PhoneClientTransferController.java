package com.bank.antifraud.controller.external;

import com.bank.antifraud.dto.external.PhoneClientTransferDto;
import com.bank.antifraud.service.external.PhoneClientTransferService;
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
@RequestMapping("/phone")
public class PhoneClientTransferController {

    PhoneClientTransferService phoneClientService;

    @GetMapping("/read/{id}")
    public ResponseEntity<PhoneClientTransferDto> getPhoneClientTransfer(
            @PathVariable("id") Long phoneClientTransferId) {

        final PhoneClientTransferDto phoneClientTransfer = phoneClientService.
                getPhoneClientTransfer(phoneClientTransferId);
        return ResponseEntity.ok(phoneClientTransfer);
    }
}
