package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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
public class SuspiciousPhoneTransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SuspiciousPhoneTransferService phoneService;

    static SuspiciousPhoneTransferDto phoneDto, newPhoneDto, updatePhoneDto;

    @BeforeEach
    public void setUp() {
        phoneDto = TestFactoryControllers.createPhoneDto(1L);
        TestFactoryControllers.mockPhoneService(phoneService, 1L, phoneDto);

        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<SuspiciousPhoneTransferDto> phoneDtos = ids.stream()
                .map(TestFactoryControllers::createPhoneDto)
                .collect(Collectors.toList());
        TestFactoryControllers.mockPhoneServiceForReadAll(phoneService, ids, phoneDtos);
    }

    @Test
    @DisplayName("Тест на успешное получение SuspiciousPhoneTransfer с ID 1")
    public void testPositiveGetPhoneById() throws Exception {
        mockMvc.perform(get("/suspicious/phone/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное получение SuspiciousPhoneTransfer с ID 1")
    public void testNegativeGetPhoneById() throws Exception {
        when(phoneService.findById(1L)).thenThrow(new EntityNotFoundException("Не существующий id = 1"));

        mockMvc.perform(get("/suspicious/phone/transfer/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }

    @Test
    @DisplayName("Тест на успешное получение всех SuspiciousPhoneTransfer по ID")
    public void testPositiveGetPhoneByIdAll() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        mockMvc.perform(get("/suspicious/phone/transfer")
                        .param("ids", ids.stream().map(String::valueOf).toArray(String[]::new))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(ids.size()));
    }

    @Test
    @DisplayName("Тест на неуспешное получение всех SuspiciousPhoneTransfer по ID")
    public void testNegativeGetPhoneByIdAll() throws Exception {
        when(phoneService.findAllById(Arrays.asList(1L, 2L, 3L)))
                .thenThrow(new EntityNotFoundException("Не существующий id = 1, 2, 3"));

        mockMvc.perform(get("/suspicious/phone/transfer")
                        .param("ids", "1", "2", "3"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1, 2, 3",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }

    @Test
    @DisplayName("Тест на успешное создание SuspiciousPhoneTransfer и возврат DTO")
    public void testPositivePhoneCreate() throws Exception {
        newPhoneDto = TestFactoryControllers.createPhoneDto(1L);
        mockMvc.perform(post("/suspicious/phone/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newPhoneDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное создание SuspiciousPhoneTransfer и возврат DTO")
    public void testNegativePhoneCreate() throws Exception {
        mockMvc.perform(post("/suspicious/phone/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertFalse(exception.getMessage().contains("Отсутствует необходимое тело запроса"));
                });
    }

    @Test
    @DisplayName("Teст на успешное обновление SuspiciousPhoneTransfer и возврат DTO")
    public void testPositivePhoneUpdate() throws Exception {
        updatePhoneDto = TestFactoryControllers.createPhoneDto(1L);
        mockMvc.perform(put("/suspicious/phone/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatePhoneDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Teст на неуспешное обновление SuspiciousPhoneTransfer и возврат DTO")
    public void testNegativePhoneUpdate() throws Exception {
        mockMvc.perform(put("/suspicious/phone/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertFalse(exception.getMessage().contains("Отсутствует необходимое тело запроса"));
                });
    }
}
