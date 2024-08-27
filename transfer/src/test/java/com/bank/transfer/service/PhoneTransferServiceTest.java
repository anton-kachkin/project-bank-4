package com.bank.transfer.service;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.Impl.PhoneTransferServiceImpl;
import com.bank.transfer.service.common.EntityNotFoundReturner;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PhoneTransferServiceTest {

    private final static String MESSAGE = "Не найден перевод по номеру телефона с ID ";

    @Mock
    PhoneTransferRepository repository;

    @Mock
    PhoneTransferMapper mapper;

    @Mock
    EntityNotFoundReturner notFoundReturner;

    @InjectMocks
    PhoneTransferServiceImpl service;

    @DisplayName("поиск по списку id, позитивный сценарий")
    @Test
    void findAllById_ReturnsDtoList() {
        List<PhoneTransferDto> dtoList = DataGenerator.getPhoneTransferDtoList(List.of(1l, 2l, 3l));

        List<PhoneTransferEntity> entityList = DataGenerator.getPhoneTransferEntityList(3);

        doReturn(Optional.of(entityList.get(0))).when(repository).findById(1L);
        doReturn(Optional.of(entityList.get(1))).when(repository).findById(2L);
        doReturn(Optional.of(entityList.get(2))).when(repository).findById(3L);

        doReturn(dtoList.get(0)).when(mapper).toDto(entityList.get(0));
        doReturn(dtoList.get(1)).when(mapper).toDto(entityList.get(1));
        doReturn(dtoList.get(2)).when(mapper).toDto(entityList.get(2));

        var allDtoList = service.findAllById(List.of(1L, 2L, 3L));

        assertNotNull(allDtoList);
        assertEquals(dtoList, allDtoList, "lists are not equal");

    }

    @DisplayName("поиск по списку, содержащему несуществующий id, негативный сценарий")
    @Test
    void findAllById_ThrowsEntityNotFoundException() {
        doReturn(new EntityNotFoundException(MESSAGE + 1L)).when(notFoundReturner).
                getEntityNotFoundException(1L, MESSAGE);

        assertThrows(EntityNotFoundException.class, () -> {
            service.findAllById(List.of(1L, 2L, 3L));
        });
    }

    @DisplayName("поиск по id, позитивный сценарий")
    @Test
    void findById_ReturnsDto() {
        PhoneTransferDto dto = DataGenerator.getRandomPhoneDtoWithId(1l);
        PhoneTransferEntity entity = DataGenerator.getRandomPhoneEntity();

        doReturn(Optional.of(entity)).when(repository).findById(1L);
        doReturn(dto).when(mapper).toDto(entity);

        var dtoResult = service.findById(1L);

        assertNotNull(dtoResult);
        assertEquals(dto, dtoResult, "Dto are not equal");
    }

    @DisplayName("поиск по несуществующему id, негативный сценарий")
    @Test
    void findById_ThrowsEntityNotFoundException() {
        doReturn(new EntityNotFoundException(MESSAGE + 1L)).when(notFoundReturner).
                getEntityNotFoundException(1L, MESSAGE);

        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });
        assertEquals(MESSAGE + 1L, thrown.getMessage());
    }

    @DisplayName("сохранение, позитивный сценарий")
    @Test
    void save_ReturnsDto() {
        PhoneTransferDto dto = DataGenerator.getRandomPhoneDto();
        PhoneTransferEntity entity = DataGenerator.getRandomPhoneEntity();

        doReturn(entity).when(mapper).toEntity(dto);
        doReturn(entity).when(repository).save(entity);
        doReturn(dto).when(mapper).toDto(entity);

        var dtoResult = service.save(dto);

        assertNotNull(dtoResult);
        assertEquals(dto, dtoResult, "Dto are not equal");
    }

    @DisplayName("сохранение, негативный сценарий")
    @Test
    void save_ThrowsException() {
        PhoneTransferDto dto = DataGenerator.getRandomPhoneDto();
        PhoneTransferEntity entity = DataGenerator.getRandomPhoneEntity();

        doReturn(entity).when(mapper).toEntity(dto);
        doReturn(new Exception()).when(repository).save(entity);

        assertThrows(Exception.class, () -> {
            service.save(dto);
        });
    }

    @DisplayName("обновление, позитивный сценарий")
    @Test
    void update_ReturnsDto() {
        Long id = 1L;
        PhoneTransferDto dto = DataGenerator.getRandomPhoneDtoWithId(id);
        PhoneTransferEntity entity = DataGenerator.getRandomPhoneEntity();
        PhoneTransferEntity entityResult = DataGenerator.getRandomPhoneEntity();

        doReturn(Optional.of(entity)).when(repository).findById(id);
        doReturn(entityResult).when(mapper).mergeToEntity(dto, entity);
        doReturn(entityResult).when(repository).save(entityResult);
        doReturn(dto).when(mapper).toDto(entityResult);

        var dtoResult = service.update(id, dto);

        assertNotNull(dtoResult);
        assertEquals(dtoResult, dto, "Dto are not equal");
    }

    @DisplayName("обновление, негативный сценарий")
    @Test
    void update_ThrowsEntityNotFoundException() {
        Long id = 1L;
        doReturn(new EntityNotFoundException(MESSAGE + id)).when(notFoundReturner).
                getEntityNotFoundException(id, MESSAGE);

        assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
    }
}