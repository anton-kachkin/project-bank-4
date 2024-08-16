package com.bank.utils;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TestFactoryControllers {


    public static AuditDto createAuditDto(Long id) {
        AuditDto auditDto = new AuditDto();
        auditDto.setId(id);
        return auditDto;
    }

    public static void mockAuditService(AuditService auditService, Long id, AuditDto auditDto) {
        when(auditService.findById(id)).thenReturn(auditDto);
    }

    public static SuspiciousAccountTransferDto createAccountDto(Long id) {
        SuspiciousAccountTransferDto accountDto = new SuspiciousAccountTransferDto();
        accountDto.setId(id);
        return accountDto;
    }

    public static void mockAccountService(SuspiciousAccountTransferService accountService, Long id,
                                          SuspiciousAccountTransferDto accountDto) {
        when(accountService.findById(id)).thenReturn(accountDto);
        when(accountService.update(eq(id), any(SuspiciousAccountTransferDto.class))).thenReturn(accountDto);
        when(accountService.save(any(SuspiciousAccountTransferDto.class))).thenReturn(accountDto);
    }

    public static void mockAccountServiceForReadAll(SuspiciousAccountTransferService accountService, List<Long> ids,
                                                    List<SuspiciousAccountTransferDto> accountDtos) {
        when(accountService.findAllById(ids)).thenReturn(accountDtos);
    }

    public static SuspiciousCardTransferDto createCardDto(Long id) {
        SuspiciousCardTransferDto cardDto = new SuspiciousCardTransferDto();
        cardDto.setId(id);
        return cardDto;
    }

    public static void mockCardService(SuspiciousCardTransferService cardService, Long id,
                                       SuspiciousCardTransferDto cardDto) {
        when(cardService.findById(id)).thenReturn(cardDto);
        when(cardService.update(eq(id), any(SuspiciousCardTransferDto.class))).thenReturn(cardDto);
        when(cardService.save(any(SuspiciousCardTransferDto.class))).thenReturn(cardDto);
    }

    public static void mockCardServiceForReadAll(SuspiciousCardTransferService cardService, List<Long> ids,
                                                 List<SuspiciousCardTransferDto> cardDtos) {
        when(cardService.findAllById(ids)).thenReturn(cardDtos);
    }

    public static SuspiciousPhoneTransferDto createPhoneDto(Long id) {
        SuspiciousPhoneTransferDto phoneDto = new SuspiciousPhoneTransferDto();
        phoneDto.setId(id);
        return phoneDto;
    }

    public static void mockPhoneService(SuspiciousPhoneTransferService phoneService, Long id,
                                        SuspiciousPhoneTransferDto phoneDto) {
        when(phoneService.findById(id)).thenReturn(phoneDto);
        when(phoneService.update(eq(id), any(SuspiciousPhoneTransferDto.class))).thenReturn(phoneDto);
        when(phoneService.save(any(SuspiciousPhoneTransferDto.class))).thenReturn(phoneDto);
    }

    public static void mockPhoneServiceForReadAll(SuspiciousPhoneTransferService phoneService, List<Long> ids,
                                                  List<SuspiciousPhoneTransferDto> phoneDtos) {
        when(phoneService.findAllById(ids)).thenReturn(phoneDtos);
    }
}
