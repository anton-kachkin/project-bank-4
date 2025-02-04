package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.AuditService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Реализация {@link AuditService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuditServiceImpl implements AuditService {

    AuditRepository repository;

    AuditMapper mapper;

    /**
     * @param id технический идентификатор {@link AuditEntity}
     * @return {@link AuditDto}
     */
    @Override
    public AuditDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(
                        () -> getException(id)
                )
        );
    }

    private EntityNotFoundException getException(Long id) {
        return new EntityNotFoundException("Не найден аудит с ID  " + id);
    }
}
