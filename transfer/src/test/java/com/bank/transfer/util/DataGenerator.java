package com.bank.transfer.util;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.entity.PhoneTransferEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитный класс генерации объектов для целей тестирования
 */
public class DataGenerator {
    
    private final static String TRANSFER_PURPOSE = "transfer purpose #";

    public static List<AccountTransferDto> getAccountTransferDtoList(List<Long> ids) {
        final List<AccountTransferDto> transferDtoList = new ArrayList<>();
        for (Long id : ids) {
            transferDtoList.add(getRandomAccountDtoWithId(id));
        }
        return transferDtoList;
    }

    public static List<AccountTransferEntity> getAccountTransferEntityList(int listSize) {
        final List<AccountTransferEntity> transferEntityList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            transferEntityList.add(getRandomAccountEntity());
        }
        return transferEntityList;
    }

    public static AccountTransferEntity getRandomAccountEntity() {
        final AccountTransferEntity transferEntity = new AccountTransferEntity();
        transferEntity.setAccountNumber((long) Math.random());
        transferEntity.setAmount(BigDecimal.valueOf(Math.random()));
        transferEntity.setAccountDetailsId((long) Math.random());
        transferEntity.setPurpose(TRANSFER_PURPOSE + Math.random());

        return transferEntity;
    }

    public static AccountTransferDto getRandomAccountDto() {
        final AccountTransferDto transferDto = new AccountTransferDto();
        transferDto.setId((long) Math.random());
        transferDto.setAccountNumber((long) Math.random());
        transferDto.setAmount(BigDecimal.valueOf(Math.random()));
        transferDto.setAccountDetailsId((long) Math.random());
        transferDto.setPurpose(TRANSFER_PURPOSE + Math.random());

        return transferDto;
    }

    public static AccountTransferDto getRandomAccountDtoWithId(long id) {
        final AccountTransferDto transferDto = new AccountTransferDto();
        transferDto.setId(id);
        transferDto.setAccountNumber((long) Math.random());
        transferDto.setAmount(BigDecimal.valueOf(Math.random()));
        transferDto.setAccountDetailsId((long) Math.random());
        transferDto.setPurpose(TRANSFER_PURPOSE + Math.random());

        return transferDto;
    }

    public static AuditEntity getRandomAuditEntity() {
        final AuditEntity entity = new AuditEntity();

        entity.setEntityType("entityType #" + Math.random());
        entity.setOperationType("operationType #" + Math.random());
        entity.setCreatedBy("createdBy #" + Math.random());
        entity.setModifiedBy("modifiedBy #" + Math.random());
        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entity.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        entity.setEntityJson("entityJson #" + Math.random());
        entity.setNewEntityJson("newEntityJson #" + Math.random());

        return entity;
    }

    public static AuditDto getRandomAuditDto() {
        final AuditDto dto = new AuditDto();

        return dto;
    }

    public static AuditDto getRandomAuditDtoWithId(Long id) {
        final AuditDto dto = new AuditDto();
        dto.setId(id);

        return dto;
    }

    public static CardTransferEntity getRandomCardEntity() {
        final CardTransferEntity entity = new CardTransferEntity();
        entity.setCardNumber((long) Math.random());
        entity.setAmount(BigDecimal.valueOf(Math.random()));
        entity.setAccountDetailsId((long) Math.random());
        entity.setPurpose(TRANSFER_PURPOSE + Math.random());
        return entity;
    }

    public static CardTransferDto getRandomCardDto() {
        final CardTransferDto dto = new CardTransferDto();
        dto.setId((long) Math.random());
        dto.setCardNumber((long) Math.random());
        dto.setAmount(BigDecimal.valueOf(Math.random()));
        dto.setAccountDetailsId((long) Math.random());
        dto.setPurpose(TRANSFER_PURPOSE + String.valueOf(Math.random()));
        return dto;
    }

    public static CardTransferDto getRandomCardDtoWithId(long id) {
        final CardTransferDto dto = new CardTransferDto();
        dto.setId(id);
        dto.setCardNumber((long) Math.random());
        dto.setAmount(BigDecimal.valueOf(Math.random()));
        dto.setAccountDetailsId((long) Math.random());
        dto.setPurpose(TRANSFER_PURPOSE + Math.random());
        return dto;
    }

    public static List<CardTransferDto> getCardTransferDtoList(List<Long> ids) {
        final List<CardTransferDto> transferDtoList = new ArrayList<>();
        for (Long id : ids) {
            transferDtoList.add(getRandomCardDtoWithId(id));
        }
        return transferDtoList;
    }

    public static List<CardTransferEntity> getCardTransferEntityList(int listSize) {
        final List<CardTransferEntity> transferEntityList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            transferEntityList.add(getRandomCardEntity());
        }
        return transferEntityList;
    }

    public static PhoneTransferEntity getRandomPhoneEntity() {
        final PhoneTransferEntity entity = new PhoneTransferEntity();
        entity.setPhoneNumber((long) Math.random());
        entity.setAmount(BigDecimal.valueOf(Math.random()));
        entity.setAccountDetailsId((long) Math.random());
        entity.setPurpose(TRANSFER_PURPOSE + Math.random());
        return entity;
    }

    public static PhoneTransferDto getRandomPhoneDto() {
        final PhoneTransferDto dto = new PhoneTransferDto();
        dto.setId((long) Math.random());
        dto.setPhoneNumber((long) Math.random());
        dto.setAmount(BigDecimal.valueOf(Math.random()));
        dto.setAccountDetailsId((long) Math.random());
        dto.setPurpose(TRANSFER_PURPOSE + Math.random());
        return dto;
    }

    public static PhoneTransferDto getRandomPhoneDtoWithId(long id) {
        final PhoneTransferDto dto = new PhoneTransferDto();
        dto.setId(id);
        dto.setPhoneNumber((long) Math.random());
        dto.setAmount(BigDecimal.valueOf(Math.random()));
        dto.setAccountDetailsId((long) Math.random());
        dto.setPurpose(TRANSFER_PURPOSE + Math.random());
        return dto;
    }

    public static List<PhoneTransferDto> getPhoneTransferDtoList(List<Long> ids) {
        final List<PhoneTransferDto> transferDtoList = new ArrayList<>();
        for (Long id : ids) {
            transferDtoList.add(getRandomPhoneDtoWithId(id));
        }
        return transferDtoList;
    }

    public static List<PhoneTransferEntity> getPhoneTransferEntityList(int listSize) {
        final List<PhoneTransferEntity> transferEntityList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            transferEntityList.add(getRandomPhoneEntity());
        }
        return transferEntityList;
    }
}
