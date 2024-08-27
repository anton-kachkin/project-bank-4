package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.Impl.AccountTransferServiceImpl;
import com.bank.transfer.service.common.EntityNotFoundReturner;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AccountTransferServiceTest {

    private final static String MESSAGE = "Не найден перевод по номеру счета с ID ";

    @Mock
    AccountTransferRepository repository;
    @Mock
    AccountTransferMapper mapper;
    @Mock
    EntityNotFoundReturner notFoundReturner;

    @InjectMocks
    AccountTransferServiceImpl service;

    @DisplayName("поиск по списку id, позитивный сценарий")
    @Test
    void findAllById_ReturnsDtoList() {
        List<AccountTransferDto> transferDtoList = DataGenerator.getAccountTransferDtoList(List.of(1l, 2l, 3l));

        List<AccountTransferEntity> transferEntityList = new ArrayList<>();
        transferEntityList.add(DataGenerator.getRandomAccountEntity());
        transferEntityList.add(DataGenerator.getRandomAccountEntity());
        transferEntityList.add(DataGenerator.getRandomAccountEntity());

        doReturn(Optional.of(transferEntityList.get(0))).when(this.repository).findById(1L);
        doReturn(Optional.of(transferEntityList.get(1))).when(this.repository).findById(2L);
        doReturn(Optional.of(transferEntityList.get(2))).when(this.repository).findById(3L);

        doReturn(transferDtoList.get(0)).when(this.mapper).toDto(transferEntityList.get(0));
        doReturn(transferDtoList.get(1)).when(this.mapper).toDto(transferEntityList.get(1));
        doReturn(transferDtoList.get(2)).when(this.mapper).toDto(transferEntityList.get(2));

        var transferAllDtoList = service.findAllById(List.of(1L, 2L, 3L));

        assertNotNull(transferAllDtoList);
        assertEquals(transferDtoList, transferAllDtoList, "lists are not equal");

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
    void findById_ReturnsDto(){
        AccountTransferDto transferDto = DataGenerator.getRandomAccountDtoWithId(1l);
        AccountTransferEntity transferEntity = DataGenerator.getRandomAccountEntity();

        doReturn(Optional.of(transferEntity)).when(this.repository).findById(1L);
        doReturn(transferDto).when(this.mapper).toDto(transferEntity);

        var transferDtoResult = service.findById(1L);

        assertNotNull(transferDtoResult);
        assertEquals(transferDto, transferDtoResult, "Dto are not equal");
    }

    @DisplayName("поиск по несуществующему id, негативный сценарий")
    @Test
    void findById_ThrowsEntityNotFoundException(){
        doReturn(new EntityNotFoundException(MESSAGE + 1L)).when(notFoundReturner).getEntityNotFoundException(1L, MESSAGE);

        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });
        assertEquals(MESSAGE + 1L, thrown.getMessage());
    }

    @DisplayName("сохранение, позитивный сценарий")
    @Test
    void save_ReturnsDto() {
        AccountTransferDto transferDto = DataGenerator.getRandomAccountDto();
        AccountTransferEntity transferEntity = DataGenerator.getRandomAccountEntity();

        doReturn(transferEntity).when(this.mapper).toEntity(transferDto);
        doReturn(transferEntity).when(this.repository).save(transferEntity);
        doReturn(transferDto).when(this.mapper).toDto(transferEntity);

        var transferDtoResult = service.save(transferDto);

        assertNotNull(transferDtoResult);
        assertEquals(transferDto, transferDtoResult, "Dto are not equal");
    }

    @DisplayName("сохранение, негативный сценарий")
    @Test
    void save_ThrowsException() {
        AccountTransferDto transferDto = DataGenerator.getRandomAccountDto();
        AccountTransferEntity transferEntity = DataGenerator.getRandomAccountEntity();

        doReturn(transferEntity).when(this.mapper).toEntity(transferDto);
        doReturn(new Exception()).when(repository).save(transferEntity);


        assertThrows(Exception.class, () -> {
            service.save(transferDto);
        });
    }

    @DisplayName("обновление, позитивный сценарий")
    @Test
    void update_ReturnsDto() {
        Long id = 1L;
        AccountTransferDto dto = DataGenerator.getRandomAccountDtoWithId(id);
        AccountTransferEntity entity = DataGenerator.getRandomAccountEntity();
        AccountTransferEntity entityResult = DataGenerator.getRandomAccountEntity();

        doReturn(Optional.of(entity)).when(this.repository).findById(id);
        doReturn(entityResult).when(this.mapper).mergeToEntity(dto, entity);
        doReturn(entityResult).when(this.repository).save(entityResult);
        doReturn(dto).when(this.mapper).toDto(entityResult);

        var dtoResult = this.service.update(id, dto);

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