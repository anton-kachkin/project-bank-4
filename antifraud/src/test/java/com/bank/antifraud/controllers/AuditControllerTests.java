package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import com.bank.utils.TestFactoryControllers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuditService auditService;

    @BeforeEach
    public void setUp() {
        AuditDto auditDto = TestFactoryControllers.createAuditDto(1L);
        TestFactoryControllers.mockAuditService(auditService, 1L, auditDto);
    }

    @Test
    @DisplayName("Тест на успешное получение Audit с ID 1")
    public void testPositiveGetAuditById() throws Exception {
        mockMvc.perform(get("/audit/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное получение Audit с ID 1")
    public void testNegativeGetAuditById() throws Exception {
        when(auditService.findById(1L)).thenThrow(new EntityNotFoundException("Не существующий id = 1"));

        mockMvc.perform(get("/audit/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }
}
