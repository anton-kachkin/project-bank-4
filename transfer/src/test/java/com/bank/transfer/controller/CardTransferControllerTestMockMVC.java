package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.service.CardTransferService;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(CardTransferController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class CardTransferControllerTestMockMVC {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardTransferService service;

    final Long id = 1L;
    final CardTransferDto randomDto = DataGenerator.getRandomCardDto();
    final CardTransferDto dtoById = DataGenerator.getRandomCardDtoWithId(id);

    @Test
    @DisplayName("GET /read/all возвращает HTTP-ответ со статусом 200 ОК и списком dto")
    void readAll_ReturnsValidResponseEntity() throws Exception {
        var ids = List.of(1l, 2l, 3l);
        var listDto = DataGenerator.getCardTransferDtoList(ids);

        doReturn(listDto).when(service).findAllById(any());

        mockMvc.perform(
                        get("/card/read/all")
                                .param("ids", ids.stream()
                                        .map(String::valueOf)
                                        .toArray(String[]::new)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listDto))
                );
    }

    @Test
    @DisplayName("GET /read/{id} entity сущестует, возвращает dto")
    void read_ExistingEntity_ReturnsValidResponseEntity() throws Exception {
        doReturn(dtoById).when(service).findById(id);

        mockMvc.perform(
                        get("/card/read/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dtoById))
                );
    }

    @Test
    @DisplayName("GET /read/{id} entity не существует, выбрасывает исключение EntityNotFoundException, статус ответа 404")
    void read_NotExistingEntity_ThrowsEntityNotFoundExceptionAndStatusNotFound() throws Exception {
        doThrow(new EntityNotFoundException("entity not found by id " + id)).when(service).findById(id);

        mockMvc.perform(
                        get("/card/read/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result ->
                        assertEquals("entity not found by id " + id,
                                result.getResolvedException().getMessage()))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
                );
    }

    @Test
    @DisplayName("POST /create возвращает HTTP-ответ со статусом 200 ОК и dto")
    void create_ReturnsResponseEntityWithDto() throws Exception {
        doReturn(randomDto).when(service).save(randomDto);

        mockMvc.perform(
                        post("/card/create")
                                .content(objectMapper.writeValueAsString(randomDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(randomDto)));
    }

    @Test
    @DisplayName("POST /create выбрасывает исключение DataIntegrityViolationException, статус ответа 409")
    void create_ThrowsDataIntegrityViolationExceptionAndStatusConflict() throws Exception {
        doThrow(new DataIntegrityViolationException("an error occurred while saving data")).when(service).save(randomDto);

        mockMvc.perform(
                        post("/card/create")
                                .content(objectMapper.writeValueAsString(randomDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(result ->
                        assertEquals("an error occurred while saving data",
                                result.getResolvedException().getMessage()))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException));
    }

    @Test
    @DisplayName("PUT /update/{id} возвращает HTTP-ответ со статусом 200 ОК и dto")
    void update_ReturnsResponseEntityWithDto() throws Exception {
        doReturn(dtoById).when(service).update(id, dtoById);

        mockMvc.perform(
                        put("/card/update/{id}", id)
                                .content(objectMapper.writeValueAsString(dtoById))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dtoById)));
    }

    @Test
    @DisplayName("PUT /update/{id} выбрасывает исключение EntityNotFoundException, статус ответа 404")
    void update_ThrowsEntityNotFoundExceptionAndStatusNotFound() throws Exception {
        doThrow(new EntityNotFoundException("entity not found by id " + id)).when(service).update(id, dtoById);

        mockMvc.perform(
                        put("/card/update/{id}", id)
                                .content(objectMapper.writeValueAsString(dtoById))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result ->
                        assertEquals("entity not found by id " + id,
                                result.getResolvedException().getMessage()))
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }
}