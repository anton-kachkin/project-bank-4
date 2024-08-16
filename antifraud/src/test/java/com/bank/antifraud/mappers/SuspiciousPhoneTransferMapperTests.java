package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
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
public class SuspiciousPhoneTransferMapperTests {

    static SuspiciousPhoneTransferEntity phoneEntity, phoneEntity1, phoneMergeEntity;

    SuspiciousPhoneTransferMapper phoneMapper;

    static SuspiciousPhoneTransferDto phoneDto;

    @BeforeEach
    public void setUp() {
        phoneMapper = Mappers.getMapper(SuspiciousPhoneTransferMapper.class);
        phoneEntity = TestFactoryMappers.createPhoneEntity();
        phoneEntity1 = TestFactoryMappers.createPhoneEntity();
        phoneDto = TestFactoryMappers.createPhoneDto();
    }

    @Test
    @DisplayName("Тест маппинга PhoneEntity на PhoneDto")
    public void testShouldMapPhoneEntityToPhoneDtoCorrectly() {
        phoneDto = phoneMapper.toDto(phoneEntity);

        assertEquals(phoneEntity.getId(), phoneDto.getId());
        assertEquals(phoneEntity.getPhoneTransferId(), phoneDto.getPhoneTransferId());
        assertEquals(phoneEntity.getIsBlocked(), phoneDto.getIsBlocked());
        assertEquals(phoneEntity.getIsSuspicious(), phoneDto.getIsSuspicious());
        assertEquals(phoneEntity.getBlockedReason(), phoneDto.getBlockedReason());
        assertEquals(phoneEntity.getSuspiciousReason(), phoneDto.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг PhoneDto возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullPhoneDto() {
        assertNull(phoneMapper.toDto(null));
    }

    @Test
    @DisplayName("Тест маппинга PhoneDto на PhoneEntity")
    public void testShouldMapPhoneDtoToPhoneEntityCorrectly() {
        phoneEntity = phoneMapper.toEntity(phoneDto);

        assertNull(phoneEntity.getId());
        assertEquals(phoneDto.getPhoneTransferId(), phoneEntity.getPhoneTransferId());
        assertEquals(phoneDto.getIsBlocked(), phoneEntity.getIsBlocked());
        assertEquals(phoneDto.getIsSuspicious(), phoneEntity.getIsSuspicious());
        assertEquals(phoneDto.getBlockedReason(), phoneEntity.getBlockedReason());
        assertEquals(phoneDto.getSuspiciousReason(), phoneEntity.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг PhoneEntity возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullPhoneEntity() {
        assertNull(phoneMapper.toEntity(null));
    }

    @Test
    @DisplayName("Тест маппинга PhoneEntityList на PhoneDtoList")
    public void testShouldMapPhoneEntityListToPhoneDtoListCorrectly() {
        List<SuspiciousPhoneTransferEntity> phoneEntityList = List.of(phoneEntity, phoneEntity1);
        List<SuspiciousPhoneTransferDto> phoneDtoList = phoneMapper.toListDto(phoneEntityList);

        assertEquals(phoneEntityList.size(), phoneDtoList.size());

        assertEquals(phoneEntity.getId(), phoneDtoList.get(0).getId());
        assertEquals(phoneEntity.getPhoneTransferId(), phoneDtoList.get(0).getPhoneTransferId());
        assertEquals(phoneEntity.getIsBlocked(), phoneDtoList.get(0).getIsBlocked());
        assertEquals(phoneEntity.getIsSuspicious(), phoneDtoList.get(0).getIsSuspicious());
        assertEquals(phoneEntity.getBlockedReason(), phoneDtoList.get(0).getBlockedReason());
        assertEquals(phoneEntity.getSuspiciousReason(), phoneDtoList.get(0).getSuspiciousReason());

        assertEquals(phoneEntity1.getId(), phoneDtoList.get(1).getId());
        assertEquals(phoneEntity1.getPhoneTransferId(), phoneDtoList.get(1).getPhoneTransferId());
        assertEquals(phoneEntity1.getIsBlocked(), phoneDtoList.get(1).getIsBlocked());
        assertEquals(phoneEntity1.getIsSuspicious(), phoneDtoList.get(1).getIsSuspicious());
        assertEquals(phoneEntity1.getBlockedReason(), phoneDtoList.get(1).getBlockedReason());
        assertEquals(phoneEntity1.getSuspiciousReason(), phoneDtoList.get(1).getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг PhoneDtoList возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullPhoneDtoList() {
        assertNull(phoneMapper.toListDto(null));
    }

    @Test
    @DisplayName("Тест маппинга слияния данных PhoneDto в PhoneEntity")
    public void testShouldMergePhoneDtoIntoPhoneEntityCorrectly() {
        phoneEntity = TestFactoryMappers.createPhoneEntity();
        phoneDto = TestFactoryMappers.createPhoneDto();

        phoneMergeEntity = phoneMapper.mergeToEntity(phoneDto, phoneEntity);

        assertEquals(phoneEntity.getId(), phoneMergeEntity.getId());
        assertEquals(phoneEntity.getPhoneTransferId(), phoneMergeEntity.getPhoneTransferId());
        assertEquals(phoneEntity.getIsBlocked(), phoneMergeEntity.getIsBlocked());
        assertEquals(phoneEntity.getIsSuspicious(), phoneMergeEntity.getIsSuspicious());
        assertEquals(phoneEntity.getBlockedReason(), phoneMergeEntity.getBlockedReason());
        assertEquals(phoneEntity.getSuspiciousReason(), phoneMergeEntity.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг PhoneMergeEntity возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMergingNullDtoAndEntity() {
        assertNull(phoneMapper.mergeToEntity(null, null));
    }
}
