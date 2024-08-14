package com.bank.profile.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
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
@DisplayName("Тестирование маппера ProfileMapper")
class ProfileMapperTest {

    private final ProfileMapper mapper = new ProfileMapperImpl();

    @Mock
    private EntityFactory entityFactory;

    @Mock
    private DtoFactory dtoFactory;

    private ProfileEntity actualEntity;
    private ProfileEntity expectedEntity;
    private List<ProfileEntity> entityList;

    private ProfileDto actualDto;
    private ProfileDto expectedDto;
    private List<ProfileDto> expectedDtoList;

    @BeforeEach
    void setUp() {
        dtoFactory = new DtoFactoryImpl(dtoFactory);
        entityFactory = new EntityFactoryImpl(entityFactory);

        actualEntity = entityFactory.createProfileEntity();
        expectedEntity = entityFactory.createProfileEntity();
        entityList = List.of(entityFactory.createProfileEntity(),
                entityFactory.createProfileEntity());

        actualDto = dtoFactory.createProfileDto();
        expectedDto = dtoFactory.createProfileDto();
        expectedDtoList = List.of(dtoFactory.createProfileDto(),
                dtoFactory.createProfileDto());
    }

    @Test
    @DisplayName("Преобразование в DTO в Entity, проверка всех полей кроме 'id'")
    void testToEntity() {
        ProfileEntity resultEntity = mapper.toEntity(actualDto);

        assertThat(resultEntity).isEqualToIgnoringGivenFields(expectedEntity, "id");
    }

    @Test
    @DisplayName("Преобразование в Entity при при входящем Dto null")
    void testToEntityByNull() {
        ProfileEntity resultEntity = mapper.toEntity(null);

        assertNull(resultEntity);
    }

    @Test
    @DisplayName("Преобразование Entity в DTO, проверка всех полей")
    void testToDto() {
        ProfileDto resultDto = mapper.toDto(actualEntity);

        assertThat(resultDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Преобразование Entity в DTO при входящем Entity null")
    void testToDtoByNull() {
        ProfileDto resultDto = mapper.toDto(null);

        assertNull(resultDto);
    }

    @Test
    @DisplayName("Слияние с Entity")
    void testMergeToEntity() {
        ProfileEntity resultEntity = mapper.mergeToEntity(actualDto, actualEntity);

        assertThat(resultEntity).usingRecursiveComparison().isEqualTo(expectedEntity);
    }

    @Test
    @DisplayName("Объединение с Entity при входящих DTO null и Entity null")
    void testMergeToEntityByNullAndNull() {
        ProfileEntity resultEntity = mapper.mergeToEntity(null,null);

        assertNull(resultEntity);
    }


    @Test
    @DisplayName("toDtoList преобразует список Entity в список DTO, проверка соответствия всех элементов списка")
    void testToDtoList() {
        List<ProfileDto> resultDtoList = mapper.toDtoList(entityList);

        assertThat(resultDtoList).usingRecursiveComparison().isEqualTo(expectedDtoList);
    }

    @Test
    @DisplayName("Преобразование списка Entity в список DTO при входящем списке Entity null")
    void testToDtoListByNull() {
        List<ProfileDto> resultDtoList = mapper.toDtoList(null);

        assertNull(resultDtoList);
    }
}