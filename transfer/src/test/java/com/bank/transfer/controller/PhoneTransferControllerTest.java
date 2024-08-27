package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.service.PhoneTransferService;
import com.bank.transfer.util.DataGenerator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class PhoneTransferControllerTest {

    @Mock
    PhoneTransferService service;

    @InjectMocks
    PhoneTransferController controller;

    final Long id = 1L;
    final PhoneTransferDto randomDto = DataGenerator.getRandomPhoneDto();
    final PhoneTransferDto dtoById = DataGenerator.getRandomPhoneDtoWithId(id);

    @Test
    @DisplayName("GET /read/all возвращает HTTP-ответ со статусом 200 ОК и списком dto")
    void readAll_ReturnsValidResponseEntity() {

        var ids = List.of(1l, 2l, 3l);
        var listDto = DataGenerator.getPhoneTransferDtoList(ids);

        doReturn(listDto).when(service).findAllById(ids);

        var responseEntity = controller.readAll(ids);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listDto, responseEntity.getBody());
        verify(service, times(1)).findAllById(any());
    }

    @Test
    @DisplayName("GET /read/{id} возвращает dto")
    void read_ReturnsDto() {
        doReturn(dtoById).when(service).findById(id);

        var dtoResult = controller.read(id);

        assertNotNull(dtoResult);
        assertEquals(dtoResult, dtoById, "dto are not equal");
        verify(service, times(1)).findById(any());
    }

    @Test
    @DisplayName("POST /create возвращает HTTP-ответ со статусом 200 и dto")
    void create_ReturnsValidResponseEntity() {
        doReturn(randomDto).when(service).save(randomDto);

        var responseEntity = controller.create(randomDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(randomDto, responseEntity.getBody());
        verify(service, times(1)).save(any());
    }

    @Test
    @DisplayName("POST /create исключение при сохранении в репозиторий")
    void create_ThrowsDataIntegrityViolationException() {
        doThrow(new DataIntegrityViolationException("unable to save data")).when(service).save(any());

        assertThrows(DataIntegrityViolationException.class, () -> {
            controller.create(randomDto);
        });
        verify(service, times(1)).save(any());
    }

    @Test
    @DisplayName("PUT /update/{id} возвращает HTTP-ответ со статусом 200 и dto")
    void update_ReturnsValidResponseEntity() {
        doReturn(dtoById).when(service).update(id, dtoById);

        var responseEntity = controller.update(id, dtoById);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dtoById, responseEntity.getBody());
        verify(service, times(1)).update(any(), any());
    }

    @Test
    @DisplayName("PUT /update/{id} EntityNotFoundException")
    void update_ThrowsEntityNotFoundException() {
        doThrow(new EntityNotFoundException("entity not found by id")).when(service).update(id, dtoById);

        assertThrows(EntityNotFoundException.class, () -> {
            controller.update(id, dtoById);
        });
        verify(service, times(1)).update(any(), any());
    }
}