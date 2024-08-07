package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountAuditController.class)
class AccountAuditControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountAuditService service;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void read_IdIsValid_ReturnsAuditDto() throws Exception {
        AuditDto auditDto = new AuditDto(
                1L,
                "TestEntityType",
                "READ",
                "creator",
                "modifier",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "{\"key\":\"newValue\"}",
                "{\"key\":\"oldValue\"}"
        );

        when(service.findById(1L)).thenReturn(auditDto);

        mockMvc.perform(get("/audit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.entityType").value("TestEntityType"))
                .andExpect(jsonPath("$.operationType").value("READ"))
                .andExpect(jsonPath("$.createdBy").value("creator"))
                .andExpect(jsonPath("$.modifiedBy").value("modifier"))
                .andExpect(jsonPath("$.newEntityJson").value("{\"key\":\"newValue\"}"))
                .andExpect(jsonPath("$.entityJson").value("{\"key\":\"oldValue\"}"));
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void read_IdIsInvalid_ReturnNotFound() throws Exception {
        when(service.findById(1L)).thenThrow(new EntityNotFoundException("Не существующий id = 1"));

        mockMvc.perform(get("/audit/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }
}
