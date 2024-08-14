package com.bank.profile.service.impl;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.util.DtoFactory;
import com.bank.profile.util.DtoFactoryImpl;
import com.bank.profile.util.EntityFactory;
import com.bank.profile.util.EntityFactoryImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * В тестах используются предустановленные тестовые значения, создаваемые с помощью фабрик EntityFactory и DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование сервиса RegistrationServiceImp")
class RegistrationServiceImpTest {

    @Mock
    private EntityFactory entityFactory;

    @Mock
    private DtoFactory dtoFactory;

    @Mock
    private RegistrationRepository repository;

    @Mock
    private RegistrationMapper mapper;

    @InjectMocks
    private RegistrationServiceImp service;

    private RegistrationEntity entity;
    private List<RegistrationEntity> entityList;

    private RegistrationDto dto;
    private List<RegistrationDto> dtoList;

    private final Long id = 1L;
    private final List<Long> idList = List.of(1L, 2L);

    @BeforeEach
    void setUp() {
        entityFactory = new EntityFactoryImpl(entityFactory);
        dtoFactory = new DtoFactoryImpl(dtoFactory);

        entity = entityFactory.createRegistrationEntity();
        entityList = List.of(entityFactory.createRegistrationEntity(),
                entityFactory.createRegistrationEntity());

        dto = dtoFactory.createRegistrationDto();
        dtoList = List.of(dtoFactory.createRegistrationDto(),
                dtoFactory.createRegistrationDto());
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void testFindById() {
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        RegistrationDto result = service.findById(id);

        assertThat(result).isEqualTo(dto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    public void testFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void testSave() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        RegistrationDto result = service.save(dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий: поле profile DTO равно null")
    void testNotSave() {
        dto.setCountry(null);
        when(mapper.toEntity(dto))
                .thenThrow(new IllegalArgumentException("Профиль не может быть null"));

        assertThrows(IllegalArgumentException.class,
                () -> service.save(dto), "Сохранение DTO с null в поле profile");
        verify(repository, never()).save(entity);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void testUpdate() {
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        RegistrationDto result = service.update(id, dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper, times(1)).mergeToEntity(dto, entity);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    public void testUpdateIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Поиск по idList, позитивный сценарий")
    void testFindAllById() {

        when(repository.findAllById(idList)).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<RegistrationDto> resultList = service.findAllById(idList);

        assertEquals(resultList.size(), dtoList.size());
        assertTrue(resultList.containsAll(dtoList));

        verify(repository, times(1)).findAllById(idList);
        verify(mapper, times(1)).toDtoList(entityList);
    }

    @Test
    @DisplayName("Поиск по несуществующему idList, негативный сценарий")
    public void testFindAllByIdNotFound() {
        List<Long> idList = Collections.emptyList();
        when(repository.findAllById(idList)).thenReturn(Collections.emptyList());

        List<RegistrationDto> resultList = service.findAllById(idList);

        assertTrue(resultList.isEmpty(), "Список id пустой");
        verify(repository, times(1)).findAllById(idList);
    }
}