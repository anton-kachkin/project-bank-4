package com.bank.profile.service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
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
 * В тестах используются предустановленные тестовые значения,
 * создаваемые с помощью фабрик EntityFactory и DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование сервиса ActualRegistrationServiceImp")
class ActualRegistrationServiceImpTest {

    @Mock
    private EntityFactory entityFactory;

    @Mock
    private DtoFactory dtoFactory;

    @Mock
    private ActualRegistrationRepository repository;

    @Mock
    private ActualRegistrationMapper mapper;

    @InjectMocks
    private ActualRegistrationServiceImp service;

    private ActualRegistrationEntity entity;
    private List<ActualRegistrationEntity> entityList;

    private ActualRegistrationDto dto;
    private List<ActualRegistrationDto> dtoList;

    private final Long id = 1L;
    private final List<Long> idList = List.of(1L, 2L);

    @BeforeEach
    void setUp() {
        entityFactory = new EntityFactoryImpl(entityFactory);
        dtoFactory = new DtoFactoryImpl(dtoFactory);

        entity = entityFactory.createActualRegistrationEntity();
        entityList = List.of(entityFactory.createActualRegistrationEntity(),
                entityFactory.createActualRegistrationEntity());

        dto = dtoFactory.createActualRegistrationDto();
        dtoList = List.of(dtoFactory.createActualRegistrationDto(),
                dtoFactory.createActualRegistrationDto());
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void testFindById() {
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ActualRegistrationDto result = service.findById(id);

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

        ActualRegistrationDto result = service.save(dto);

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

        ActualRegistrationDto result = service.update(id, dto);

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

        List<ActualRegistrationDto> resultDtoList = service.findAllById(idList);

        assertEquals(resultDtoList.size(), dtoList.size());
        assertTrue(resultDtoList.containsAll(dtoList));

        verify(repository, times(1)).findAllById(idList);
        verify(mapper, times(1)).toDtoList(entityList);
    }

    @Test
    @DisplayName("Поиск по несуществующему idList, негативный сценарий")
    public void testFindAllByIdNotFound() {
        List<Long> idList = Collections.emptyList();
        when(repository.findAllById(idList)).thenReturn(Collections.emptyList());

        List<ActualRegistrationDto> resultList = service.findAllById(idList);

        assertTrue(resultList.isEmpty(), "Список id пустой");
        verify(repository, times(1)).findAllById(idList);
    }
}