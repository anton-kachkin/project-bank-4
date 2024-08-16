package com.bank.antifraud.services;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.impl.AuditServiceImpl;
import com.bank.utils.TestFactoryServices;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditServiceImplTest {

    static final Long id = 1L;

    @Mock
    AuditRepository repository;

    @Mock
    AuditMapper mapper;

    @InjectMocks
    AuditServiceImpl auditService;

    static AuditDto auditDto, auditResult;

    AuditEntity auditEntity;

    static EntityNotFoundException exception;

    @BeforeEach
    public void setUp() {
        auditDto = TestFactoryServices.createAuditDto();
        auditEntity = TestFactoryServices.createAuditEntity();
    }

    @Test
    @DisplayName("Тест на успешное нахождение Audit по ID и возврат DTO")
    public void testPositiveFindByIdSuccess() {
        when(repository.findById(id)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        auditResult = auditService.findById(id);

        assertNotNull(auditResult);
        assertEquals(id, auditResult.getId());

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(auditEntity);
    }

    @Test
    @DisplayName("Тест на неуспешное нахождение Audit по ID и возврат DTO")
    public void testNegativeFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.findById(id)).thenThrow(new EntityNotFoundException("Сущность с таким ID " + id + " не найден"));

        exception = assertThrows(EntityNotFoundException.class, () -> auditService.findById(id));

        String expectedMessage = "Сущность с таким ID " + id + " не найден";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Ожидалось, что сообщение об исключении будет содержать: " + expectedMessage +
                        ", но получили: " + actualMessage);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(0)).toDto(any());
    }
}
