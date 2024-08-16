package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.utils.TestFactoryMappers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditMapperTests {

    AuditEntity auditEntity;

    AuditMapper auditMapper;

    static AuditDto auditDto;

    @BeforeEach
    public void setUp() {
        auditMapper = Mappers.getMapper(AuditMapper.class);
        auditEntity = TestFactoryMappers.createAuditEntity();
    }

    @Test
    @DisplayName("Тест маппинга AuditEntity на AuditDto")
    public void shouldMapAuditEntityToAuditDtoCorrectly() {
        auditDto = auditMapper.toDto(auditEntity);

        assertEquals(auditEntity.getId(), auditDto.getId());
        assertEquals(auditEntity.getEntityType(), auditDto.getEntityType());
        assertEquals(auditEntity.getOperationType(), auditDto.getOperationType());
        assertEquals(auditEntity.getCreatedBy(), auditDto.getCreatedBy());
        assertEquals(auditEntity.getModifiedBy(), auditDto.getModifiedBy());
        assertEquals(auditEntity.getCreatedAt(), auditDto.getCreatedAt());
        assertEquals(auditEntity.getModifiedAt(), auditDto.getModifiedAt());
        assertEquals(auditEntity.getNewEntityJson(), auditDto.getNewEntityJson());
        assertEquals(auditEntity.getEntityJson(), auditDto.getEntityJson());
    }

    @Test
    @DisplayName("Тест что маппинг возвращает null, когда входное значение равно null")
    public void shouldReturnNullWhenMappingNullAuditEntity() {
        assertNull(auditMapper.toDto(null));
    }
}
