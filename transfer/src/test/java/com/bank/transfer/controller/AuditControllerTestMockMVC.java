package com.bank.transfer.controller;

import com.bank.transfer.service.AuditService;
import com.bank.transfer.util.DataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(AuditController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AuditControllerTestMockMVC {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditService service;

    final Long id = 1L;

    @Test
    @DisplayName("GET /read/{id} entity сущестует, возвращает dto")
    void read_ExistingEntity_ReturnsValidResponseEntity() throws Exception {
        var dto = DataGenerator.getRandomAuditDtoWithId(id);

        doReturn(dto).when(service).findById(id);

        mockMvc.perform(
                        get("/audit/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto))
                );
    }

    @Test
    @DisplayName("GET /read/{id} entity не существует, выбрасывает исключение EntityNotFoundException," +
            " статус ответа 404")
    void read_NotExistingEntity_ThrowsEntityNotFoundExceptionAndStatusNotFound() throws Exception {
        doThrow(new EntityNotFoundException("entity not found by id " + id)).when(service).findById(id);

        mockMvc.perform(
                        get("/audit/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result ->
                        assertEquals("entity not found by id " + id,
                                result.getResolvedException().getMessage()))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }
}