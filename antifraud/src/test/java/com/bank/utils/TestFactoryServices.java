package com.bank.utils;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestFactoryServices {

    static Long id = 1L;
    static Long accountTransferId = 1L;
    static Long cardTransferId = 1L;
    static Long phoneTransferId = 1L;
    static Boolean isBlocked = true;
    static Boolean isSuspicious = true;
    static String blockedReason = "Reason";
    static String suspiciousReason = "Reason";

    public static AuditDto createAuditDto() {
        AuditDto auditDto = new AuditDto();
        auditDto.setId(id);
        return auditDto;
    }

    public static AuditEntity createAuditEntity() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(id);
        return auditEntity;
    }

    public static SuspiciousAccountTransferDto createSuspiciousAccountTransferDto() {
        SuspiciousAccountTransferDto accountDto = new SuspiciousAccountTransferDto();
        accountDto.setId(id);
        accountDto.setAccountTransferId(accountTransferId);
        accountDto.setIsBlocked(isBlocked);
        accountDto.setIsSuspicious(isSuspicious);
        accountDto.setBlockedReason(blockedReason);
        accountDto.setSuspiciousReason(suspiciousReason);
        return accountDto;
    }

    public static SuspiciousAccountTransferEntity createSuspiciousAccountTransferEntity() {
        SuspiciousAccountTransferEntity accountEntity = new SuspiciousAccountTransferEntity();
        accountEntity.setId(id);
        accountEntity.setAccountTransferId(id);
        accountEntity.setIsBlocked(isBlocked);
        accountEntity.setIsSuspicious(isSuspicious);
        accountEntity.setBlockedReason(blockedReason);
        accountEntity.setSuspiciousReason(suspiciousReason);
        return accountEntity;
    }

    public static SuspiciousCardTransferDto createSuspiciousCardTransferDto() {
        SuspiciousCardTransferDto cardDto = new SuspiciousCardTransferDto();
        cardDto.setId(id);
        cardDto.setCardTransferId(cardTransferId);
        cardDto.setIsBlocked(isBlocked);
        cardDto.setIsSuspicious(isSuspicious);
        cardDto.setBlockedReason(blockedReason);
        cardDto.setSuspiciousReason(suspiciousReason);
        return cardDto;
    }

    public static SuspiciousCardTransferEntity createSuspiciousCardTransferEntity() {
        SuspiciousCardTransferEntity cardEntity = new SuspiciousCardTransferEntity();
        cardEntity.setId(id);
        cardEntity.setCardTransferId(cardTransferId);
        cardEntity.setIsBlocked(isBlocked);
        cardEntity.setIsSuspicious(isSuspicious);
        cardEntity.setBlockedReason(blockedReason);
        cardEntity.setSuspiciousReason(suspiciousReason);
        return cardEntity;
    }

    public static SuspiciousPhoneTransferDto createSuspiciousPhoneTransferDto() {
        SuspiciousPhoneTransferDto phoneDto = new SuspiciousPhoneTransferDto();
        phoneDto.setId(id);
        phoneDto.setPhoneTransferId(phoneTransferId);
        phoneDto.setIsBlocked(isBlocked);
        phoneDto.setIsSuspicious(isSuspicious);
        phoneDto.setBlockedReason(blockedReason);
        phoneDto.setSuspiciousReason(suspiciousReason);
        return phoneDto;
    }

    public static SuspiciousPhoneTransferEntity createSuspiciousPhoneTransferEntity() {
        SuspiciousPhoneTransferEntity phoneEntity = new SuspiciousPhoneTransferEntity();
        phoneEntity.setId(id);
        phoneEntity.setPhoneTransferId(phoneTransferId);
        phoneEntity.setIsBlocked(isBlocked);
        phoneEntity.setIsSuspicious(isSuspicious);
        phoneEntity.setBlockedReason(blockedReason);
        phoneEntity.setSuspiciousReason(suspiciousReason);
        return phoneEntity;
    }
}
