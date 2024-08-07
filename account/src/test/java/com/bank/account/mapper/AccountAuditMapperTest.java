package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountAuditMapperTest {

    private AccountAuditMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AccountAuditMapper.class);
    }

    @Test
    @DisplayName("Маппинг в dto")
    public void toDto_Positive_ReturnsAuditDto() {
        AuditEntity entity = createAuditEntity();
        AuditDto dto = mapper.toDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEntityType(), dto.getEntityType());
        assertEquals(entity.getOperationType(), dto.getOperationType());
        assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
        assertEquals(entity.getModifiedBy(), dto.getModifiedBy());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.getModifiedAt(), dto.getModifiedAt());
        assertEquals(entity.getNewEntityJson(), dto.getNewEntityJson());
        assertEquals(entity.getEntityJson(), dto.getEntityJson());
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDto_Negative_NullCheck() {
        AuditDto dto = mapper.toDto(null);

        assertNull(dto);
    }

    private AuditEntity createAuditEntity() {
        return new AuditEntity(
                1L,
                "TestEntityType",
                "CREATE",
                "creator",
                "modifier",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "{\"key\":\"newValue\"}",
                "{\"key\":\"oldValue\"}"
        );
    }
}
