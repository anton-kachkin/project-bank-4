package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.utils.TestFactoryMappers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousCardTransferMapperTests {

    static SuspiciousCardTransferEntity cardEntity, cardEntity1, cardMergeEntity;

    SuspiciousCardTransferMapper cardMapper;

    static SuspiciousCardTransferDto cardDto;

    @BeforeEach
    public void setUp() {
        cardMapper = Mappers.getMapper(SuspiciousCardTransferMapper.class);
        cardEntity = TestFactoryMappers.createCardEntity();
        cardEntity1 = TestFactoryMappers.createCardEntity();
        cardDto = TestFactoryMappers.createCardDto();
    }

    @Test
    @DisplayName("Тест маппинга CardEntity на CardDto")
    public void testShouldMapCardEntityToCardDtoCorrectly() {
        cardDto = cardMapper.toDto(cardEntity);

        assertEquals(cardEntity.getId(), cardDto.getId());
        assertEquals(cardEntity.getCardTransferId(), cardDto.getCardTransferId());
        assertEquals(cardEntity.getIsBlocked(), cardDto.getIsBlocked());
        assertEquals(cardEntity.getIsSuspicious(), cardDto.getIsSuspicious());
        assertEquals(cardEntity.getBlockedReason(), cardDto.getBlockedReason());
        assertEquals(cardEntity.getSuspiciousReason(), cardDto.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг CardDto возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullCardDto() {
        assertNull(cardMapper.toDto(null));
    }

    @Test
    @DisplayName("Тест маппинга CardDto на CardEntity")
    public void testShouldMapCardDtoToCardEntityCorrectly() {
        cardEntity = cardMapper.toEntity(cardDto);

        assertNull(cardEntity.getId());
        assertEquals(cardDto.getCardTransferId(), cardEntity.getCardTransferId());
        assertEquals(cardDto.getIsBlocked(), cardEntity.getIsBlocked());
        assertEquals(cardDto.getIsSuspicious(), cardEntity.getIsSuspicious());
        assertEquals(cardDto.getBlockedReason(), cardEntity.getBlockedReason());
        assertEquals(cardDto.getSuspiciousReason(), cardEntity.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг CardEntity возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullCardEntity() {
        assertNull(cardMapper.toEntity(null));
    }

    @Test
    @DisplayName("Тест маппинга CardEntityList на CardDtoList")
    public void testShouldMapCardEntityListToCardDtoListCorrectly() {
        List<SuspiciousCardTransferEntity> cardEntityList = List.of(cardEntity, cardEntity1);
        List<SuspiciousCardTransferDto> cardDtoList = cardMapper.toListDto(cardEntityList);

        assertEquals(cardEntityList.size(), cardDtoList.size());

        assertEquals(cardEntity.getId(), cardDtoList.get(0).getId());
        assertEquals(cardEntity.getCardTransferId(), cardDtoList.get(0).getCardTransferId());
        assertEquals(cardEntity.getIsBlocked(), cardDtoList.get(0).getIsBlocked());
        assertEquals(cardEntity.getIsSuspicious(), cardDtoList.get(0).getIsSuspicious());
        assertEquals(cardEntity.getBlockedReason(), cardDtoList.get(0).getBlockedReason());
        assertEquals(cardEntity.getSuspiciousReason(), cardDtoList.get(0).getSuspiciousReason());

        assertEquals(cardEntity1.getId(), cardDtoList.get(1).getId());
        assertEquals(cardEntity1.getCardTransferId(), cardDtoList.get(1).getCardTransferId());
        assertEquals(cardEntity1.getIsBlocked(), cardDtoList.get(1).getIsBlocked());
        assertEquals(cardEntity1.getIsSuspicious(), cardDtoList.get(1).getIsSuspicious());
        assertEquals(cardEntity1.getBlockedReason(), cardDtoList.get(1).getBlockedReason());
        assertEquals(cardEntity1.getSuspiciousReason(), cardDtoList.get(1).getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг CardDtoList возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullCardDtoList() {
        assertNull(cardMapper.toListDto(null));
    }

    @Test
    @DisplayName("Тест маппинга слияния данных CardDto в CardEntity")
    public void testShouldMergeCardDtoIntoCardEntityCorrectly() {
        cardEntity = TestFactoryMappers.createCardEntity();
        cardDto = TestFactoryMappers.createCardDto();

        cardMergeEntity = cardMapper.mergeToEntity(cardDto, cardEntity);

        assertEquals(cardEntity.getId(), cardMergeEntity.getId());
        assertEquals(cardEntity.getCardTransferId(), cardMergeEntity.getCardTransferId());
        assertEquals(cardEntity.getIsBlocked(), cardMergeEntity.getIsBlocked());
        assertEquals(cardEntity.getIsSuspicious(), cardMergeEntity.getIsSuspicious());
        assertEquals(cardEntity.getBlockedReason(), cardMergeEntity.getBlockedReason());
    }

    @Test
    @DisplayName("Тест что маппинг CardEntity возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMergingNullDtoAndEntity() {
        assertNull(cardMapper.mergeToEntity(null, null));
    }
}
