package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PhoneTransferMapperTest {

    private final PhoneTransferMapper phoneTransferMapper = new PhoneTransferMapperImpl();

    @DisplayName("маппинг в entity")
    @Test
    void toEntity() {
        PhoneTransferDto transferDto = DataGenerator.getRandomPhoneDto();

        PhoneTransferEntity transferEntity = phoneTransferMapper.toEntity(transferDto);

        assertNotNull(transferEntity, "transferEntity is null");
        assertEquals(transferDto.getPhoneNumber(), transferEntity.getPhoneNumber(),
                "phoneNumbers not equal");
        assertEquals(transferDto.getAmount(), transferEntity.getAmount(), "amount not equal");
        assertEquals(transferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferDto.getPurpose(), transferEntity.getPurpose(), "purposes not equal");
    }

    @DisplayName("маппинг в entity, на вход подан null")
    @Test
    void toEntity_NullCheck() {
        PhoneTransferEntity transferEntity = phoneTransferMapper.toEntity(null);

        assertNull(transferEntity, "should be null after mapping from null");
    }

    @DisplayName("маппинг в dto")
    @Test
    void toDto() {
        PhoneTransferEntity transferEntity = DataGenerator.getRandomPhoneEntity();

        PhoneTransferDto transferDto = phoneTransferMapper.toDto(transferEntity);

        assertNotNull(transferDto, "transferDto is null");
        assertEquals(transferEntity.getId(), transferDto.getId(), "ids not equal");
        assertEquals(transferEntity.getPhoneNumber(), transferDto.getPhoneNumber(), "phoneNumbers not equal");
        assertEquals(transferEntity.getAmount(), transferDto.getAmount(), "amount not equal");
        assertEquals(transferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferEntity.getPurpose(), transferDto.getPurpose(), "purposes not equal");
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDto_NullCheck() {
        PhoneTransferDto transferDto = phoneTransferMapper.toDto(null);

        assertNull(transferDto, "should be null after mapping from null");
    }

    @DisplayName("слияние в entity")
    @Test
    void mergeToEntity() {
        PhoneTransferDto transferDto = DataGenerator.getRandomPhoneDto();

        PhoneTransferEntity transferEntity = DataGenerator.getRandomPhoneEntity();

        transferEntity = phoneTransferMapper.mergeToEntity(transferDto, transferEntity);

        assertNotNull(transferEntity, "transferEntity is null");
        assertEquals(transferDto.getPhoneNumber(), transferEntity.getPhoneNumber(), "accountNumbers not equal");
        assertEquals(transferDto.getAmount(), transferEntity.getAmount(), "amount not equal");
        assertEquals(transferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferDto.getPurpose(), transferEntity.getPurpose(), "purposes not equal");
    }

    @DisplayName("слияние в entity, на вход подан null")
    @Test
    void mergeToEntity_NullCheck() {
        PhoneTransferEntity transferEntity = phoneTransferMapper.mergeToEntity(null, null);

        assertNull(transferEntity, "should be null after merging with null");
    }

    @DisplayName("маппинг в dto list")
    @Test
    void toDtoList() {
        List<PhoneTransferEntity> transferEntityList = DataGenerator.getPhoneTransferEntityList(3);

        List<PhoneTransferDto> transferDtoList = phoneTransferMapper.toDtoList(transferEntityList);

        assertNotNull(transferDtoList, "transferDtoList is null");
        assertEquals(transferEntityList.size(), transferDtoList.size());
        for (PhoneTransferEntity transferEntity : transferEntityList) {
            assertEquals(transferEntity.getId(), transferDtoList.get(transferEntityList.indexOf(transferEntity)).getId(),
                    "ids not equal at position #" + transferEntityList.indexOf(transferEntity));
            assertEquals(transferEntity.getPhoneNumber(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getPhoneNumber(),
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

    @DisplayName("маппинг в dto list, на вход подан null")
    @Test
    void toDtoList_NullCheck() {
        List<PhoneTransferDto> transferDtoList = phoneTransferMapper.toDtoList(null);

        assertNull(transferDtoList, "should be null after mapping from null");
    }
}