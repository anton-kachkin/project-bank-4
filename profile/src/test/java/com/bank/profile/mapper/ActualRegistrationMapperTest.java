package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
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

import java.util.List;

/**
 * В тестах используются предустановленные тестовые значения, создаваемые с помощью фабрик EntityFactory и DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование маппера ActualRegistrationMapper")
class ActualRegistrationMapperTest {

    private final ActualRegistrationMapper mapper = new ActualRegistrationMapperImpl();

    @Mock
    private EntityFactory entityFactory;

    @Mock
    private DtoFactory dtoFactory;

    private ActualRegistrationEntity actualEntity;
    private ActualRegistrationEntity expectedEntity;
    private List<ActualRegistrationEntity> entityList;

    private ActualRegistrationDto actualDto;
    private ActualRegistrationDto expectedDto;
    private List<ActualRegistrationDto> expectedDtoList;

    @BeforeEach
    void setUp() {
        dtoFactory = new DtoFactoryImpl(dtoFactory);
        entityFactory = new EntityFactoryImpl(entityFactory);

        actualEntity = entityFactory.createActualRegistrationEntity();
        expectedEntity = entityFactory.createActualRegistrationEntity();
        entityList = List.of(entityFactory.createActualRegistrationEntity(),
                entityFactory.createActualRegistrationEntity());


        actualDto = dtoFactory.createActualRegistrationDto();
        expectedDto = dtoFactory.createActualRegistrationDto();
        expectedDtoList = List.of(dtoFactory.createActualRegistrationDto(),
                dtoFactory.createActualRegistrationDto());
    }

    @Test
    @DisplayName("Преобразование в DTO в Entity, проверка всех полей кроме 'id'")
    void testToEntity() {
        ActualRegistrationEntity resultEntity = mapper.toEntity(actualDto);

        assertThat(resultEntity).isEqualToIgnoringGivenFields(expectedEntity, "id");
    }

    @Test
    @DisplayName("Преобразование в Entity при при входящем Dto null")
    void testToEntityByNull() {
        ActualRegistrationEntity resultEntity = mapper.toEntity(null);

        assertNull(resultEntity);
    }

    @Test
    @DisplayName("Преобразование Entity в DTO, проверка всех полей")
    void testToDto() {
        ActualRegistrationDto resultDto = mapper.toDto(actualEntity);

        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Преобразование Entity в DTO при входящем Entity null")
    void testToDtoByNull() {
        ActualRegistrationDto resultDto = mapper.toDto(null);

        assertNull(resultDto);
    }

    @Test
    @DisplayName("Слияние с Entity")
    void testMergeToEntity() {
        ActualRegistrationEntity resultEntity = mapper.mergeToEntity(actualDto, actualEntity);

        assertThat(resultEntity).usingRecursiveComparison().isEqualTo(expectedEntity);
    }

    @Test
    @DisplayName("Объединение с Entity при входящих DTO null и Entity null")
    void testMergeToEntityByNullAndNull() {
        ActualRegistrationEntity resultEntity = mapper.mergeToEntity(null,null);

        assertNull(resultEntity);
    }


    @Test
    @DisplayName("toDtoList преобразует список Entity в список DTO, проверка соответствия всех элементов списка")
    void testToDtoList() {
        List<ActualRegistrationDto> resultDtoList = mapper.toDtoList(entityList);

        assertThat(resultDtoList).usingRecursiveComparison().isEqualTo(expectedDtoList);
    }

    @Test
    @DisplayName("Преобразование списка Entity в список DTO при входящем списке Entity null")
    void testToDtoListByNull() {
        List<ActualRegistrationDto> resultDtoList = mapper.toDtoList(null);

        assertNull(resultDtoList);
    }
}