package com.bank.transfer.mapper;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuditMapperTest {

    private final AuditMapper auditMapper = new AuditMapperImpl();

    @DisplayName("маппинг в dto")
    @Test
    void toDto() {
        AuditEntity auditEntity = DataGenerator.getRandomAuditEntity();

        AuditDto auditDto = auditMapper.toDto(auditEntity);

        assertNotNull(auditDto, "auditDto is null");
        assertEquals(auditEntity.getId(), auditDto.getId(), "ids not equal");
        assertEquals(auditEntity.getEntityType(), auditDto.getEntityType(), "entityType not equal");
        assertEquals(auditEntity.getOperationType(), auditDto.getOperationType(), "operationType not equal");
        assertEquals(auditEntity.getCreatedBy(), auditDto.getCreatedBy(), "createdBy not equal");
        assertEquals(auditEntity.getModifiedBy(), auditDto.getModifiedBy(), "modifiedBy not equal");
        assertEquals(auditEntity.getCreatedAt(), auditDto.getCreatedAt(), "createdAt not equal");
        assertEquals(auditEntity.getModifiedAt(), auditDto.getModifiedAt(), "modifiedAt not equal");
        assertEquals(auditEntity.getEntityJson(), auditDto.getEntityJson(), "entityJson not equal");
        assertEquals(auditEntity.getNewEntityJson(), auditDto.getNewEntityJson(), "newEntityJson not equal");
    }

    @DisplayName("маппинг в dto, на вход подан null")
    @Test
    void toDto_NullCheck() {
        AuditDto auditDto = auditMapper.toDto(null);

        assertNull(auditDto, "should be null after mapping from null");
    }
}