package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
import com.bank.profile.util.DtoFactory;
import com.bank.profile.util.DtoFactoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * В тестах используются предустановленные тестовые значения,
 * создаваемые с помощью фабрики DtoFactory.
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(RegistrationController.class)
@DisplayName("Тестирование контроллера RegistrationController")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService service;

    @MockBean
    private DtoFactory dtoFactory;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private RegistrationDto dto;
    private List<RegistrationDto> dtoList;

    private final Long id = 1L;
    private final List<Long> idList = List.of(1L, 2L);

    @BeforeEach
    public void setUp() {
        dtoFactory = new DtoFactoryImpl(dtoFactory);

        dto = dtoFactory.createRegistrationDto();
        dtoList = List.of(dtoFactory.createRegistrationDto(),
                dtoFactory.createRegistrationDto());
    }

    @Test
    @DisplayName("Чтение по ID: успешно")
    public void testRead() throws Exception {
        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/registration/read/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Чтение по ID: не найдено")
    public void testRead_Id_NotFound() throws Exception {
        when(service.findById(id))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        mockMvc.perform(get("/registration/read/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity не найден")));
    }

    @Test
    @DisplayName("Создание: успешно")
    void testCreate() throws Exception {
        when(service.save(dto)).thenReturn(dto);

        mockMvc.perform(post("/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Создание: ошибка уникальности ID")
    void testCreate_DuplicateId() throws Exception {
        when(service.save(dto))
                .thenThrow(new DataIntegrityViolationException("Ошибка: нарушение уникальности ID"));

        mockMvc.perform(post("/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Обновление: успешно")
    void testUpdate() throws Exception {
        when(service.update(eq(id), any(RegistrationDto.class))).thenReturn(dto);

        mockMvc.perform(put("/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Обновление: ID не найден")
    void testUpdate_Id_NotFound() throws Exception {
        when(service.update(eq(id), any(RegistrationDto.class)))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        mockMvc.perform(put("/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity не найден")));
    }

    @Test
    @DisplayName("Чтение всех по ID: успешно")
    void testReadAllById() throws Exception {
        when(service.findAllById(anyList())).thenReturn(dtoList);

        mockMvc.perform(get("/registration/read/all")
                        .param("ids", idList.stream()
                                .map(String::valueOf)
                                .toArray(String[]::new)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)));
    }

    @Test
    @DisplayName("Чтение всех по ID: не найдено")
    void testReadAllById_NotFound() throws Exception {

        when(service.findAllById(anyList()))
                .thenThrow(new EntityNotFoundException("Entity не найден"));

        mockMvc.perform(get("/registration/read/all")
                        .param("ids", idList.stream()
                                .map(String::valueOf)
                                .toArray(String[]::new)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Entity не найден")));
    }
}