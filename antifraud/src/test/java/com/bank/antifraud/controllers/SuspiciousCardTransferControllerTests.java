package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.utils.TestFactoryControllers;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuspiciousCardTransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SuspiciousCardTransferService cardService;

    static SuspiciousCardTransferDto cardDto, newCardDto, updateCardDto;

    @BeforeEach
    public void setUp() {
        cardDto = TestFactoryControllers.createCardDto(1L);
        TestFactoryControllers.mockCardService(cardService, 1L, cardDto);

        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<SuspiciousCardTransferDto> cardDtos = ids.stream()
                .map(TestFactoryControllers::createCardDto)
                .collect(Collectors.toList());
        TestFactoryControllers.mockCardServiceForReadAll(cardService, ids, cardDtos);
    }

    @Test
    @DisplayName("Тест на успешное получение SuspiciousCardTransfer с ID 1")
    public void testPositiveGetCardById() throws Exception {
        mockMvc.perform(get("/suspicious/card/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное получение SuspiciousCardTransfer с ID 1")
    public void testNegativeGetCardById() throws Exception {
        when(cardService.findById(1L)).thenThrow(new EntityNotFoundException("Не существующий id = 1"));

        mockMvc.perform(get("/suspicious/card/transfer/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }

    @Test
    @DisplayName("Тест на успешное получение всех SuspiciousAccountTransfer по ID")
    public void testPositiveGetCardByIdAll() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        mockMvc.perform(get("/suspicious/card/transfer")
                        .param("ids", ids.stream().map(String::valueOf).toArray(String[]::new))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(ids.size()));
    }

    @Test
    @DisplayName("Тест на неуспешное получение всех SuspiciousCardTransfer по ID")
    public void testNegativeGetCardByIdAll() throws Exception {
        when(cardService.findAllById(Arrays.asList(1L, 2L, 3L)))
                .thenThrow(new EntityNotFoundException("Не существующий id = 1, 2, 3"));

        mockMvc.perform(get("/suspicious/card/transfer")
                        .param("ids", "1", "2", "3"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1, 2, 3",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }

    @Test
    @DisplayName("Тест на успешное создание SuspiciousCardTransfer и возврат DTO")
    public void testPositiveCardCreate() throws Exception {
        newCardDto = TestFactoryControllers.createCardDto(1L);
        mockMvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное создание SuspiciousCardTransfer и возврат DTO")
    public void testNegativeCardCreate() throws Exception {
        mockMvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertFalse(exception.getMessage().contains("Отсутствует необходимое тело запроса"));
                });
    }

    @Test
    @DisplayName("Тест на успешное обновление SuspiciousCardTransfer и возврат DTO")
    public void testPositiveCardUpdate() throws Exception {
        updateCardDto = TestFactoryControllers.createCardDto(1L);
        mockMvc.perform(put("/suspicious/card/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateCardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Teст на неуспешное обновление SuspiciousCardTransfer и возврат DTO")
    public void testNegativeCardUpdate() throws Exception {
        mockMvc.perform(put("/suspicious/card/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertFalse(exception.getMessage().contains("Отсутствует необходимое тело запроса"));
                });
    }
}
