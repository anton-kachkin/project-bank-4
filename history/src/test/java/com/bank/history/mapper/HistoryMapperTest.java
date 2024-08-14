package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HistoryMapperTest {

    private HistoryMapper historyMapper;

    @BeforeEach
    void setUp() {
        historyMapper = Mappers.getMapper(HistoryMapper.class);
    }

    @Test
    @DisplayName("Слияние в сущность")
    void toEntity() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(1L);
        historyDto.setTransferAuditId(1L);
        historyDto.setProfileAuditId(1L);
        historyDto.setAccountAuditId(1L);
        historyDto.setAntiFraudAuditId(1L);
        historyDto.setPublicBankInfoAuditId(1L);
        historyDto.setAuthorizationAuditId(1L);
        HistoryEntity historyEntity = historyMapper.toEntity(historyDto);
        assertNotNull(historyEntity);
    }

    @Test
    @DisplayName("Слияние в сущность - отсутствуют обязательные поля")
    void toEntityMissingFields() {
        HistoryDto historyDto = new HistoryDto();
        HistoryEntity historyEntity = historyMapper.toEntity(historyDto);
        assertNotNull(historyEntity);
    }

    @Test
    @DisplayName("Слияние в ДТО")
    void toDto() {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setTransferAuditId(1L);
        historyEntity.setProfileAuditId(1L);
        historyEntity.setAccountAuditId(1L);
        historyEntity.setAntiFraudAuditId(1L);
        historyEntity.setPublicBankInfoAuditId(1L);
        historyEntity.setAuthorizationAuditId(1L);
        HistoryDto historyDto = historyMapper.toDto(historyEntity);
        assertNotNull(historyDto);
    }

    @Test
    @DisplayName("Слияние в ДТО - отсутствуют обязательные поля")
    void toDtoMissingFields() {
        HistoryEntity historyEntity = new HistoryEntity();
        HistoryDto historyDto = historyMapper.toDto(historyEntity);
        assertNotNull(historyDto);
    }

    @Test
    @DisplayName("Слияние ДТО в сущность")
    void mergeToEntity() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(1L);
        historyDto.setTransferAuditId(1L);
        historyDto.setProfileAuditId(1L);
        historyDto.setAccountAuditId(1L);
        historyDto.setAntiFraudAuditId(1L);
        historyDto.setPublicBankInfoAuditId(1L);
        historyDto.setAuthorizationAuditId(1L);

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setTransferAuditId(1L);
        historyEntity.setProfileAuditId(1L);
        historyEntity.setAccountAuditId(1L);
        historyEntity.setAntiFraudAuditId(1L);
        historyEntity.setPublicBankInfoAuditId(1L);
        historyEntity.setAuthorizationAuditId(1L);

        HistoryEntity mergedHistoryEntity = historyMapper.mergeToEntity(historyDto, historyEntity);
        assertNotNull(mergedHistoryEntity);
    }

    @Test
    @DisplayName("Слияние ДТО в сущность - неверные данные")
    void mergeToEntityInvalidData() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(1L);
        HistoryEntity existingEntity = new HistoryEntity();
        existingEntity.setId(2L);
        HistoryEntity mergedHistoryEntity = historyMapper.mergeToEntity(historyDto, existingEntity);
        assertEquals(2L, mergedHistoryEntity.getId());
    }

    @Test
    @DisplayName("Слияние сущностей в список ДТО")
    void toListDto() {
        HistoryEntity entity1 = new HistoryEntity();
        HistoryEntity entity2 = new HistoryEntity();
        List<HistoryEntity> entityList = Arrays.asList(entity1, entity2);

        List<HistoryDto> dtoList = historyMapper.toListDto(entityList);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
    }

    @Test
    @DisplayName("Слияние сущностей в список ДТО - пустой список")
    void toListDtoEmptyList() {
        List<HistoryEntity> emptyList = Collections.emptyList();
        List<HistoryDto> dtoList = historyMapper.toListDto(emptyList);
        assertNotNull(dtoList);
        assertTrue(dtoList.isEmpty());
    }
}
