package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.util.DtoFactory;
import com.bank.profile.util.DtoFactoryImpl;
import com.bank.profile.service.AccountDetailsIdService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * В тестах используются предустановленные тестовые значения,
 * создаваемые с помощью фабрики DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование контроллера AccountDetailsIdController")
public class AccountDetailsIdControllerTest {

    @Mock
    private DtoFactory dtoFactory;

    @Mock
    private AccountDetailsIdService service;

    @InjectMocks
    private AccountDetailsIdController controller;

    private AccountDetailsIdDto dto;
    private List<AccountDetailsIdDto> dtoList;

    private final Long id = 1L;
    private final List<Long> idList = List.of(1L, 2L);


    @BeforeEach
    void setUp() {
        dtoFactory = new DtoFactoryImpl(dtoFactory);
        dto = dtoFactory.createAccountDetailsIdDto();
        dtoList = List.of(dtoFactory.createAccountDetailsIdDto(),
                dtoFactory.createAccountDetailsIdDto());
    }

    @Test
    @DisplayName("Чтение по ID: успешно")
    void testRead() {
        when(service.findById(id)).thenReturn(dto);

        ResponseEntity<AccountDetailsIdDto> result = controller.read(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dto, result.getBody());
        verify(service, times(1)).findById(id);
    }

    @Test
    @DisplayName("Чтение по ID: не найдено")
    void testRead_Id_NotFound() {
        when(service.findById(id))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        assertThrows(EntityNotFoundException.class, () -> controller.read(id));
        verify(service, times(1)).findById(id);
    }

    @Test
    @DisplayName("Создание: успешно")
    void testCreate() {
        when(service.save(dto)).thenReturn(dto);

        ResponseEntity<AccountDetailsIdDto> result = controller.create(dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dto, result.getBody());
        verify(service, times(1)).save(dto);
    }

    @Test
    @DisplayName("Создание: ошибка уникальности ID")
    void testCreate_DuplicateId() {
        when(service.save(dto))
                .thenThrow(new DataIntegrityViolationException("Ошибка: нарушение уникальности ID"));

        assertThrows(DataIntegrityViolationException.class, () -> controller.create(dto));

        verify(service, times(1)).save(dto);
    }

    @Test
    @DisplayName("Обновление: успешно")
    void testUpdate() {
        when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<AccountDetailsIdDto> result = controller.update(id, dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dto, result.getBody());
        verify(service, times(1)).update(id, dto);
    }

    @Test
    @DisplayName("Обновление: ID не найден")
    void testUpdate_Id_NotFound() {
        when(service.update(id, dto))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        assertThrows(EntityNotFoundException.class, () -> controller.update(id, dto));
        verify(service, times(1)).update(id, dto);
    }

    @Test
    @DisplayName("Чтение всех по ID: успешно")
    void testReadAllById() {
        when(service.findAllById(idList)).thenReturn(dtoList);

        ResponseEntity<List<AccountDetailsIdDto>> result = controller.readAllById(idList);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dtoList, result.getBody());
        verify(service, times(1)).findAllById(idList);
    }

    @Test
    @DisplayName("Чтение всех по ID: не найдено")
    void testReadAllById_NotFound() {
        when(service.findAllById(idList))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        assertThrows(EntityNotFoundException.class, () -> controller.readAllById(idList));
        verify(service, times(1)).findAllById(idList);
    }
}
