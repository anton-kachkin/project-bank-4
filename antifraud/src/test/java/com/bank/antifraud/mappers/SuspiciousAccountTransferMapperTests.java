package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
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
public class SuspiciousAccountTransferMapperTests {

    static SuspiciousAccountTransferEntity accountEntity, accountEntity1, accountMergeEntity;

    SuspiciousAccountTransferMapper accountMapper;

    static SuspiciousAccountTransferDto accountDto;

    @BeforeEach
    public void setUp() {
        accountMapper = Mappers.getMapper(SuspiciousAccountTransferMapper.class);
        accountEntity = TestFactoryMappers.createAccountEntity();
        accountEntity1 = TestFactoryMappers.createAccountEntity();
        accountDto = TestFactoryMappers.createAccountDto();
    }

    @Test
    @DisplayName("Тест маппинга AccountEntity на AccountDto")
    public void testShouldMapAccountEntityToAccountDtoCorrectly() {
        accountDto = accountMapper.toDto(accountEntity);

        assertEquals(accountEntity.getId(), accountDto.getId());
        assertEquals(accountEntity.getAccountTransferId(), accountDto.getAccountTransferId());
        assertEquals(accountEntity.getIsBlocked(), accountDto.getIsBlocked());
        assertEquals(accountEntity.getIsSuspicious(), accountDto.getIsSuspicious());
        assertEquals(accountEntity.getBlockedReason(), accountDto.getBlockedReason());
        assertEquals(accountEntity.getSuspiciousReason(), accountDto.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг AccountDto возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullAccountDto() {
        assertNull(accountMapper.toDto(null));
    }

    @Test
    @DisplayName("Тест маппинга AccountDto на AccountEntity")
    public void testShouldMapAccountDtoToAccountEntityCorrectly() {
        accountEntity = accountMapper.toEntity(accountDto);

        assertNull(accountEntity.getId());
        assertEquals(accountDto.getAccountTransferId(), accountEntity.getAccountTransferId());
        assertEquals(accountDto.getIsBlocked(), accountEntity.getIsBlocked());
        assertEquals(accountDto.getIsSuspicious(), accountEntity.getIsSuspicious());
        assertEquals(accountDto.getBlockedReason(), accountEntity.getBlockedReason());
        assertEquals(accountDto.getSuspiciousReason(), accountEntity.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг AccountEntity возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullAccountEntity() {
        assertNull(accountMapper.toEntity(null));
    }

    @Test
    @DisplayName("Тест маппинга AccountEntityList на AccountDtoList")
    public void testShouldMapAccountEntityListToAccountDtoListCorrectly() {
        List<SuspiciousAccountTransferEntity> accountEntityList = List.of(accountEntity, accountEntity1);
        List<SuspiciousAccountTransferDto> accountDtoList = accountMapper.toListDto(accountEntityList);

        assertEquals(accountEntityList.size(), accountDtoList.size());

        assertEquals(accountEntity.getId(), accountDtoList.get(0).getId());
        assertEquals(accountEntity.getAccountTransferId(), accountDtoList.get(0).getAccountTransferId());
        assertEquals(accountEntity.getIsBlocked(), accountDtoList.get(0).getIsBlocked());
        assertEquals(accountEntity.getIsSuspicious(), accountDtoList.get(0).getIsSuspicious());
        assertEquals(accountEntity.getBlockedReason(), accountDtoList.get(0).getBlockedReason());
        assertEquals(accountEntity.getSuspiciousReason(), accountDtoList.get(0).getSuspiciousReason());

        assertEquals(accountEntity1.getId(), accountDtoList.get(1).getId());
        assertEquals(accountEntity1.getAccountTransferId(), accountDtoList.get(1).getAccountTransferId());
        assertEquals(accountEntity1.getIsBlocked(), accountDtoList.get(1).getIsBlocked());
        assertEquals(accountEntity1.getIsSuspicious(), accountDtoList.get(1).getIsSuspicious());
        assertEquals(accountEntity1.getBlockedReason(), accountDtoList.get(1).getBlockedReason());
        assertEquals(accountEntity1.getSuspiciousReason(), accountDtoList.get(1).getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг AccountDtoList возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMappingNullAccountDtoList() {
        assertNull(accountMapper.toListDto(null));
    }

    @Test
    @DisplayName("Тест маппинга слияния данных AccountDto в AccountEntity")
    public void testShouldMergeAccountDtoIntoAccountEntityCorrectly() {
        accountEntity = TestFactoryMappers.createAccountEntity();
        accountDto = TestFactoryMappers.createAccountDto();

        accountMergeEntity = accountMapper.mergeToEntity(accountDto, accountEntity);

        assertEquals(accountEntity.getId(), accountMergeEntity.getId());
        assertEquals(accountEntity.getAccountTransferId(), accountMergeEntity.getAccountTransferId());
        assertEquals(accountEntity.getIsBlocked(), accountMergeEntity.getIsBlocked());
        assertEquals(accountEntity.getIsSuspicious(), accountMergeEntity.getIsSuspicious());
        assertEquals(accountEntity.getBlockedReason(), accountMergeEntity.getBlockedReason());
        assertEquals(accountEntity.getSuspiciousReason(), accountMergeEntity.getSuspiciousReason());
    }

    @Test
    @DisplayName("Тест что маппинг AccountEntity возвращает null, когда входное значение равно null")
    public void testShouldReturnNullWhenMergingNullDtoAndEntity() {
        assertNull(accountMapper.mergeToEntity(null, null));
    }
}
