package com.bank.transfer.controller;

import com.bank.transfer.service.AuditService;
import com.bank.transfer.util.DataGenerator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

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
class AuditControllerTest {

    @Mock
    AuditService service;

    @InjectMocks
    AuditController controller;

    final Long id = 1L;

    @Test
    @DisplayName("GET /id возвращает dto")
    void read_ValidId_ReturnsDto() {
        var dto = DataGenerator.getRandomAuditDtoWithId(id);

        doReturn(dto).when(service).findById(id);

        var dtoResult = controller.read(id);

        assertNotNull(dtoResult);
        assertEquals(dtoResult, dto, "dto are not equal");
        verify(service, times(1)).findById(any());
    }

    @Test
    @DisplayName("GET /id EntityNotFoundException")
    void read_InvalidId_ThrowsEntityNotFoundException() {
        doThrow(new EntityNotFoundException("entity not found by id")).when(service).findById(id);

        assertThrows(EntityNotFoundException.class, () -> {
            controller.read(id);
        });
        verify(service, times(1)).findById(any());
    }
}