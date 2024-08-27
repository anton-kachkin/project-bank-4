package com.bank.transfer.mapper;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardTransferMapperTest {

    private final CardTransferMapper cardTransferMapper = new CardTransferMapperImpl();

    @DisplayName("маппинг в entity")
    @Test
    void toEntity() {
        CardTransferDto transferDto = DataGenerator.getRandomCardDto();

        CardTransferEntity transferEntity = cardTransferMapper.toEntity(transferDto);

        assertNotNull(transferEntity, "transferEntity is null");
        assertEquals(transferDto.getCardNumber(), transferEntity.getCardNumber(),
                "cardNumbers not equal");
        assertEquals(transferDto.getAmount(), transferEntity.getAmount(), "amount not equal");
        assertEquals(transferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferDto.getPurpose(), transferEntity.getPurpose(), "purposes not equal");
    }

    @DisplayName("маппинг в entity, на вход подан null")
    @Test
    void toEntity_NullCheck() {
        CardTransferEntity transferEntity = cardTransferMapper.toEntity(null);

        assertNull(transferEntity, "entity should be null after mapping from null");
    }

    @DisplayName("маппинг в dto")
    @Test
    void toDto() {
        CardTransferEntity transferEntity = DataGenerator.getRandomCardEntity();

        CardTransferDto transferDto = cardTransferMapper.toDto(transferEntity);

        assertNotNull(transferDto, "transferDto is null");
        assertEquals(transferEntity.getId(), transferDto.getId(), "ids not equal");
        assertEquals(transferEntity.getCardNumber(), transferDto.getCardNumber(),
                "cardNumbers not equal");
        assertEquals(transferEntity.getAmount(), transferDto.getAmount(), "amount not equal");
        assertEquals(transferEntity.getAccountDetailsId(), transferDto.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferEntity.getPurpose(), transferDto.getPurpose(), "purposes not equal");
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDto_NullCheck() {
        CardTransferDto transferDto = cardTransferMapper.toDto(null);

        assertNull(transferDto, "dto should be null after mapping from null");
    }

    @DisplayName("слияние в entity")
    @Test
    void mergeToEntity() {
        CardTransferDto transferDto = DataGenerator.getRandomCardDto();

        CardTransferEntity transferEntity = DataGenerator.getRandomCardEntity();

        transferEntity = cardTransferMapper.mergeToEntity(transferDto, transferEntity);

        assertNotNull(transferEntity, "transferEntity is null");
        assertEquals(transferDto.getCardNumber(), transferEntity.getCardNumber(),
                "accountNumbers not equal");
        assertEquals(transferDto.getAmount(), transferEntity.getAmount(), "amount not equal");
        assertEquals(transferDto.getAccountDetailsId(), transferEntity.getAccountDetailsId(),
                "accountDetailsIDs not equal");
        assertEquals(transferDto.getPurpose(), transferEntity.getPurpose(), "purposes not equal");
    }

    @DisplayName("слияние в entity, на вход подан null")
    @Test
    void mergeToEntity_NullCheck() {
        CardTransferEntity transferEntity = cardTransferMapper.mergeToEntity(null, null);

        assertNull(transferEntity, "entity should be null after merging with null");
    }

    @DisplayName("маппинг в list dto")
    @Test
    void toDtoList() {
        List<CardTransferEntity> transferEntityList = DataGenerator.getCardTransferEntityList(3);

        List<CardTransferDto> transferDtoList = cardTransferMapper.toDtoList(transferEntityList);

        assertNotNull(transferDtoList, "transferDtoList is null");
        assertEquals(transferEntityList.size(), transferDtoList.size());
        for (CardTransferEntity transferEntity : transferEntityList) {
            assertEquals(transferEntity.getId(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getId(),
                    "ids not equal at position #" + transferEntityList.indexOf(transferEntity));
            assertEquals(transferEntity.getCardNumber(),
                    transferDtoList.get(transferEntityList.indexOf(transferEntity)).getCardNumber(),
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

        List<CardTransferDto> transferDtoList = cardTransferMapper.toDtoList(null);

        assertNull(transferDtoList, "dto list should be null after mapping from null");
    }
}