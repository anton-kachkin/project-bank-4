package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountDetailsMapperTest {

    private AccountDetailsMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AccountDetailsMapper.class);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntity_Positive_ReturnsAccountDetailsEntity() {
        AccountDetailsDto accountDetailsDto = createAccountDetailsDto(1L, 200L, 1234L,
                100L, new BigDecimal(1000), false, 300L);

        AccountDetailsEntity accountDetailsEntity = mapper.toEntity(accountDetailsDto);

        assertNull(accountDetailsEntity.getId());
        assertEquals(accountDetailsDto.getAccountNumber(), accountDetailsEntity.getAccountNumber());
        assertEquals(accountDetailsDto.getBankDetailsId(), accountDetailsEntity.getBankDetailsId());
        assertEquals(accountDetailsDto.getMoney(), accountDetailsEntity.getMoney());
        assertEquals(accountDetailsDto.getPassportId(), accountDetailsEntity.getPassportId());
        assertEquals(accountDetailsDto.getNegativeBalance(), accountDetailsEntity.getNegativeBalance());
        assertEquals(accountDetailsDto.getProfileId(), accountDetailsEntity.getProfileId());
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntity_Negative_NullCheck() {
        AccountDetailsEntity accountDetailsEntity = mapper.toEntity(null);

        assertNull(accountDetailsEntity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDto_Positive_ReturnsAccountDetailsDto() {
        AccountDetailsEntity accountDetailsEntity = createAccountDetailsEntity(1L, 200L, 1234L,
                100L, new BigDecimal(1000), false, 300L);

        AccountDetailsDto dto = mapper.toDto(accountDetailsEntity);

        assertEquals(accountDetailsEntity.getId(), dto.getId());
        assertEquals(accountDetailsEntity.getAccountNumber(), dto.getAccountNumber());
        assertEquals(accountDetailsEntity.getBankDetailsId(), dto.getBankDetailsId());
        assertEquals(accountDetailsEntity.getMoney(), dto.getMoney());
        assertEquals(accountDetailsEntity.getPassportId(), dto.getPassportId());
        assertEquals(accountDetailsEntity.getNegativeBalance(), dto.getNegativeBalance());
        assertEquals(accountDetailsEntity.getProfileId(), dto.getProfileId());
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    void toDto_Negative_NullCheck() {
        AccountDetailsDto dto = mapper.toDto(null);

        assertNull(dto);
    }

    @Test
    @DisplayName("Маппинг в dtoList")
    void toDtoList_Positive_ReturnsAccountDetailsDtoList() {
        AccountDetailsEntity accountDetailsEntity = createAccountDetailsEntity(1L, 200L, 1234L,
                100L, new BigDecimal(1000), false, 300L);
        AccountDetailsEntity accountDetailsEntity1 = createAccountDetailsEntity(2L, 300L, 2234L,
                200L, new BigDecimal(2000), false, 400L);

        List<AccountDetailsEntity> accountDetailsEntityList = List.of(accountDetailsEntity, accountDetailsEntity1);

        List<AccountDetailsDto> accountDetailsDtoList = mapper.toDtoList(accountDetailsEntityList);

        assertEquals(accountDetailsEntityList.size(), accountDetailsDtoList.size());

        assertEquals(accountDetailsEntity.getId(), accountDetailsDtoList.get(0).getId());
        assertEquals(accountDetailsEntity.getPassportId(), accountDetailsDtoList.get(0).getPassportId());
        assertEquals(accountDetailsEntity.getAccountNumber(), accountDetailsDtoList.get(0).getAccountNumber());
        assertEquals(accountDetailsEntity.getBankDetailsId(), accountDetailsDtoList.get(0).getBankDetailsId());
        assertEquals(accountDetailsEntity.getMoney(), accountDetailsDtoList.get(0).getMoney());
        assertEquals(accountDetailsEntity.getNegativeBalance(), accountDetailsDtoList.get(0).getNegativeBalance());
        assertEquals(accountDetailsEntity.getProfileId(), accountDetailsDtoList.get(0).getProfileId());

        assertEquals(accountDetailsEntity1.getId(), accountDetailsDtoList.get(1).getId());
        assertEquals(accountDetailsEntity1.getPassportId(), accountDetailsDtoList.get(1).getPassportId());
        assertEquals(accountDetailsEntity1.getAccountNumber(), accountDetailsDtoList.get(1).getAccountNumber());
        assertEquals(accountDetailsEntity1.getBankDetailsId(), accountDetailsDtoList.get(1).getBankDetailsId());
        assertEquals(accountDetailsEntity1.getMoney(), accountDetailsDtoList.get(1).getMoney());
        assertEquals(accountDetailsEntity1.getNegativeBalance(), accountDetailsDtoList.get(1).getNegativeBalance());
        assertEquals(accountDetailsEntity1.getProfileId(), accountDetailsDtoList.get(1).getProfileId());
    }

    @Test
    @DisplayName("Маппинг в dtoList, на вход подан null")
    void toDtoList_Negative_NullCheck() {
        List<AccountDetailsDto> accountDetailsDtoList = mapper.toDtoList(null);

        assertNull(accountDetailsDtoList);
    }

    @Test
    @DisplayName("Слияние с entity")
    void mergeToEntity_Positive_ReturnsAccountDetailsEntity() {
        AccountDetailsEntity accountDetailsEntity = createAccountDetailsEntity(1L, 200L, 1234L,
                100L, new BigDecimal(1000), false, 300L);
        AccountDetailsDto accountDetailsDto = createAccountDetailsDto(2L, 300L, 2234L,
                200L, new BigDecimal(2000), false, 400L);

        AccountDetailsEntity mergedEntity = mapper.mergeToEntity(accountDetailsEntity, accountDetailsDto);

        assertEquals(accountDetailsEntity.getId(), mergedEntity.getId());
        assertEquals(accountDetailsDto.getAccountNumber(), mergedEntity.getAccountNumber());
        assertEquals(accountDetailsDto.getBankDetailsId(), mergedEntity.getBankDetailsId());
        assertEquals(accountDetailsDto.getMoney(), mergedEntity.getMoney());
        assertEquals(accountDetailsDto.getPassportId(), mergedEntity.getPassportId());
        assertEquals(accountDetailsDto.getNegativeBalance(), mergedEntity.getNegativeBalance());
        assertEquals(accountDetailsDto.getProfileId(), mergedEntity.getProfileId());
    }

    @Test
    @DisplayName("Слияние с entity, на вход подан null")
    void mergeToEntity_Negative_NullCheck() {
        AccountDetailsEntity mergedEntity = mapper.mergeToEntity(null, null);

        assertNull(mergedEntity);
    }

    private AccountDetailsEntity createAccountDetailsEntity(Long id, Long passportId, Long accountNumber, Long bankDetailsId,
                                                            BigDecimal money, Boolean negativeBalance, Long profileId) {
        AccountDetailsEntity accountDetailsEntity = new AccountDetailsEntity();
        accountDetailsEntity.setId(id);
        accountDetailsEntity.setAccountNumber(accountNumber);
        accountDetailsEntity.setBankDetailsId(bankDetailsId);
        accountDetailsEntity.setMoney(money);
        accountDetailsEntity.setPassportId(passportId);
        accountDetailsEntity.setNegativeBalance(negativeBalance);
        accountDetailsEntity.setProfileId(profileId);
        return accountDetailsEntity;
    }

    private AccountDetailsDto createAccountDetailsDto(Long id, Long passportId, Long accountNumber, Long bankDetailsId,
                                                      BigDecimal money, Boolean negativeBalance, Long profileId) {
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        accountDetailsDto.setId(id);
        accountDetailsDto.setAccountNumber(accountNumber);
        accountDetailsDto.setBankDetailsId(bankDetailsId);
        accountDetailsDto.setMoney(money);
        accountDetailsDto.setPassportId(passportId);
        accountDetailsDto.setNegativeBalance(negativeBalance);
        accountDetailsDto.setProfileId(profileId);
        return accountDetailsDto;
    }
}