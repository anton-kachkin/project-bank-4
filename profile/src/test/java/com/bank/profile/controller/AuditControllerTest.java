package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.AuditService;
import com.bank.profile.util.DtoFactory;
import com.bank.profile.util.DtoFactoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

/**
 * В тестах используются предустановленные тестовые значения,
 * создаваемые с помощью фабрики DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuditController.class)
@DisplayName("Тестирование контроллера AuditController")
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService service;

    @MockBean
    private DtoFactory dtoFactory;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private AuditDto dto;

    private final Long id = 1L;

    @BeforeEach
    public void setUp() {
        dtoFactory = new DtoFactoryImpl(dtoFactory);

        dto = dtoFactory.createAuditDto();
    }

    @Test
    @DisplayName("Чтение по ID: успешно")
    public void testRead() throws Exception {
        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/audit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Чтение по ID: не найдено")
    public void testRead_Id_NotFound() throws Exception {
        when(service.findById(id))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        mockMvc.perform(get("/audit/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity не найден")));
    }
}