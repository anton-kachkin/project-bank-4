package com.bank.account.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapper;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.common.ExceptionReturner;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountAuditServiceImplTest {
    @Mock
    private AccountAuditRepository repository;
    @Mock
    private AccountAuditMapper mapper;
    @Mock
    private ExceptionReturner exceptionReturner;
    @InjectMocks
    private AccountAuditServiceImpl service;

    private AuditEntity auditEntity;
    private AuditDto auditDto;

    @BeforeEach
    void setUp() {
        auditEntity = new AuditEntity();
        auditEntity.setId(1L);

        auditDto = new AuditDto();
        auditDto.setId(1L);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findById_IdIsValid_ReturnsAuditDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(auditDto);

        AuditDto result = service.findById(1L);

        assertEquals(auditDto, result);
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toDto(auditEntity);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findById_IdIsInvalid_ThrowsEntityNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(exceptionReturner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException("Не существующий id = " + 1L));

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
        verify(exceptionReturner, times(1)).getEntityNotFoundException(anyString());
    }
}