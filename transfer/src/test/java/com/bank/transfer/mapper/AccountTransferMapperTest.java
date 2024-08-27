package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountTransferMapperTest {

    private final AccountTransferMapper accountTransferMapper = new AccountTransferMapperImpl();

    @DisplayName("маппинг в entity")
    @Test
    void toEntity() {
        AccountTransferDto transferDto = DataGenerator.getRandomAccountDto();

        AccountTransferEntity transferEntity = accountTransferMapper.toEntity(transferDto);

        assertNotNull(transferEntity, "transferEntity is null");
        assertEquals(transferDto.getAccountNumber(), transferEntity.getAccountNumber(),
                "accountNumbers not equal");
        assertEquals(transferDto.getAmount(), transferEntity.getAmount(), "amount not equal");
        assertEquals(transferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferDto.getPurpose(), transferEntity.getPurpose(), "purposes not equal");
    }

    @DisplayName("маппинг в entity, на вход подан null")
    @Test
    void toEntity_NullCheck() {
        AccountTransferEntity transferEntity = accountTransferMapper.toEntity(null);

        assertNull(transferEntity, "should be null after mapping from null");
    }

    @DisplayName("маппинг в dto")
    @Test
    void toDto() {
        AccountTransferEntity transferEntity = DataGenerator.getRandomAccountEntity();

        AccountTransferDto transferDto = accountTransferMapper.toDto(transferEntity);

        assertNotNull(transferDto, "transferDto is null");
        assertEquals(transferEntity.getId(), transferDto.getId(), "ids not equal");
        assertEquals(transferEntity.getAccountNumber(), transferDto.getAccountNumber(),
                "accountNumbers not equal");
        assertEquals(transferEntity.getAmount(), transferDto.getAmount(), "amount not equal");
        assertEquals(transferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferEntity.getPurpose(), transferDto.getPurpose(), "purposes not equal");
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDto_NullCheck() {
        AccountTransferDto transferDto = accountTransferMapper.toDto(null);

        assertNull(transferDto, "should be null after mapping from null");
    }

    @DisplayName("слияние в entity")
    @Test
    void mergeToEntity() {
        AccountTransferDto transferDto = DataGenerator.getRandomAccountDto();

        AccountTransferEntity transferEntity = DataGenerator.getRandomAccountEntity();

        transferEntity = accountTransferMapper.mergeToEntity(transferDto, transferEntity);

        assertNotNull(transferEntity, "transferEntity is null");
        assertEquals(transferDto.getAccountNumber(), transferEntity.getAccountNumber(),
                "accountNumbers not equal");
        assertEquals(transferDto.getAmount(), transferEntity.getAmount(), "amount not equal");
        assertEquals(transferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferDto.getPurpose(), transferEntity.getPurpose(), "purposes not equal");
    }

    @DisplayName("слияние в entity, на вход подан null")
    @Test
    void mergeToEntity_NullCheck() {
        AccountTransferEntity transferEntity = accountTransferMapper.mergeToEntity(null, null);

        assertNull(transferEntity, "should be null after merging with null");
    }

    @DisplayName("маппинг в list dto")
    @Test
    void toDtoList() {
        List<AccountTransferEntity> transferEntityList = DataGenerator.getAccountTransferEntityList(3);

        List<AccountTransferDto> transferDtoList = accountTransferMapper.toDtoList(transferEntityList);

        assertNotNull(transferDtoList, "transferDtoList is null");
        assertEquals(transferEntityList.size(), transferDtoList.size());
        for (AccountTransferEntity transferEntity : transferEntityList) {
            assertEquals(transferEntity.getId(), transferDtoList.get(transferEntityList.indexOf(transferEntity)).getId(),
                    "ids not equal at position #" + transferEntityList.indexOf(transferEntity));
            assertEquals(transferEntity.getAccountNumber(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getAccountNumber(),
                    "accountNumbers not equal at position #" + transferEntityList.indexOf(transferEntity));
            assertEquals(transferEntity.getAmount(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getAmount(),
                    "amount not equal at position #" + transferEntityList.indexOf(transferEntity));
            assertEquals(transferEntity.getAccountDetailsId(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getAccountDetailsId(),
                    "accountDetailsIDs not equal at position #" + transferEntityList.indexOf(transferEntity));
            assertEquals(transferEntity.getPurpose(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getPurpose(),
                    "purposes not equal at position #" + transferEntityList.indexOf(transferEntity));
        }
    }

    @DisplayName("маппинг в list dto, на вход подан null")
    @Test
    void toDtoList_NullCheck() {
        List<AccountTransferDto> transferDtoList = accountTransferMapper.toDtoList(null);

        assertNull(transferDtoList, "should be null after mapping from null");
    }
}