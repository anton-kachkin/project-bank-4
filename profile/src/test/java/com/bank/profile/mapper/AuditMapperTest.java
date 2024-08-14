package com.bank.profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.util.DtoFactory;
import com.bank.profile.util.DtoFactoryImpl;
import com.bank.profile.util.EntityFactory;
import com.bank.profile.util.EntityFactoryImpl;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * В тестах используются предустановленные тестовые значения, создаваемые с помощью фабрик EntityFactory и DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование маппера AuditMapper")
class AuditMapperTest {

    private final AuditMapper mapper = new AuditMapperImpl();

    @Mock
    private EntityFactory entityFactory;

    @Mock
    private DtoFactory dtoFactory;

    private AuditEntity actualEntity;

    private AuditDto expectedDto;

    @BeforeEach
    void setUp() {
        dtoFactory = new DtoFactoryImpl(dtoFactory);
        entityFactory = new EntityFactoryImpl(entityFactory);

        actualEntity = entityFactory.createAuditEntity();

        expectedDto = dtoFactory.createAuditDto();
    }

    @Test
    @DisplayName("Преобразование Entity в DTO")
    void testToDto() {
        AuditDto resultDto = mapper.toDto(actualEntity);

        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Преобразование Entity в DTO при входящем Entity null")
    void testToDtoByNull() {
        AuditDto resultDto = mapper.toDto(null);

        assertNull(resultDto);
    }
}