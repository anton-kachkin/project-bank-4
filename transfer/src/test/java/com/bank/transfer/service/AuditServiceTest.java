package com.bank.transfer.service;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.mapper.AuditMapper;
import com.bank.transfer.repository.AuditRepository;
import com.bank.transfer.service.Impl.AuditServiceImpl;
import com.bank.transfer.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    AuditRepository repository;

    @Mock
    AuditMapper mapper;

    @InjectMocks
    AuditServiceImpl service;

    @DisplayName("поиск по id, позитивный сценарий")
    @Test
    void findById_ReturnDto() {
        AuditEntity entity = DataGenerator.getRandomAuditEntity();
        AuditDto dto = DataGenerator.getRandomAuditDto();
        doReturn(Optional.of(entity)).when(repository).findById(any(Long.class));
        doReturn(dto).when(mapper).toDto(any(AuditEntity.class));

        var dtoResult = service.findById(1L);

        assertNotNull(dtoResult);
        assertEquals(dto, dtoResult, "dto are not equal");
    }

    @DisplayName("поиск по несуществующему id, негативный сценарий")
    @Test
    void findById_ThrowsEntityNotFoundException() {
        long id = 1l;

        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(id);
        });
        assertEquals("Не найден аудит с ID  " + id, thrown.getMessage());
    }
}