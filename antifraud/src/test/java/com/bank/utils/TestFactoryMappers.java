package com.bank.utils;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestFactoryMappers {

    static Long id = 1L;
    static String entityType = "Type";
    static String operationType = "Create";
    static String createdBy = "User1";
    static String modifiedBy = "User2";
    static Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    static Timestamp modifiedAt = new Timestamp(System.currentTimeMillis());
    static String newEntityJson = "{\"key\":\"newValue\"}";
    static String entityJson = "{\"key\":\"oldValue\"}";
    static Long accountTransferId = 1L;
    static Long cardTransferId = 1L;
    static Long phoneTransferId = 1L;
    static Boolean isBlocked = true;
    static Boolean isSuspicious = true;
    static String blockedReason = "Reason";
    static String suspiciousReason = "Reason";

    public static AuditEntity createAuditEntity() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(id);
        auditEntity.setEntityType(entityType);
        auditEntity.setOperationType(operationType);
        auditEntity.setCreatedBy(createdBy);
        auditEntity.setModifiedBy(modifiedBy);
        auditEntity.setCreatedAt(createdAt);
        auditEntity.setModifiedAt(modifiedAt);
        auditEntity.setNewEntityJson(entityJson);
        auditEntity.setEntityJson(newEntityJson);
        return auditEntity;
    }

    public static SuspiciousAccountTransferEntity createAccountEntity() {
        SuspiciousAccountTransferEntity accountEntity = new SuspiciousAccountTransferEntity();
        accountEntity.setId(id);
        accountEntity.setAccountTransferId(accountTransferId);
        accountEntity.setIsBlocked(isBlocked);
        accountEntity.setIsSuspicious(isSuspicious);
        accountEntity.setBlockedReason(blockedReason);
        accountEntity.setSuspiciousReason(suspiciousReason);
        return accountEntity;
    }

    public static SuspiciousAccountTransferDto createAccountDto() {
        SuspiciousAccountTransferDto accountDto = new SuspiciousAccountTransferDto();
        accountDto.setId(id);
        accountDto.setAccountTransferId(accountTransferId);
        accountDto.setIsBlocked(isBlocked);
        accountDto.setIsSuspicious(isSuspicious);
        accountDto.setBlockedReason(blockedReason);
        accountDto.setSuspiciousReason(suspiciousReason);
        return accountDto;
    }

    public static SuspiciousCardTransferDto createCardDto() {
        SuspiciousCardTransferDto cardDto = new SuspiciousCardTransferDto();
        cardDto.setId(id);
        cardDto.setCardTransferId(cardTransferId);
        cardDto.setIsBlocked(isBlocked);
        cardDto.setIsSuspicious(isSuspicious);
        cardDto.setBlockedReason(blockedReason);
        cardDto.setSuspiciousReason(suspiciousReason);
        return cardDto;
    }

    public static SuspiciousPhoneTransferDto createPhoneDto() {
        SuspiciousPhoneTransferDto phoneDto = new SuspiciousPhoneTransferDto();
        phoneDto.setId(id);
        phoneDto.setPhoneTransferId(phoneTransferId);
        phoneDto.setIsBlocked(isBlocked);
        phoneDto.setIsSuspicious(isSuspicious);
        phoneDto.setBlockedReason(blockedReason);
        phoneDto.setSuspiciousReason(suspiciousReason);
        return phoneDto;
    }

    public static SuspiciousCardTransferEntity createCardEntity() {
        SuspiciousCardTransferEntity cardEntity = new SuspiciousCardTransferEntity();
        cardEntity.setId(id);
        cardEntity.setCardTransferId(cardTransferId);
        cardEntity.setIsBlocked(isBlocked);
        cardEntity.setIsSuspicious(isSuspicious);
        cardEntity.setBlockedReason(blockedReason);
        cardEntity.setSuspiciousReason(suspiciousReason);
        return cardEntity;
    }

    public static SuspiciousPhoneTransferEntity createPhoneEntity() {
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
