package com.bank.antifraud.controllers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.utils.TestFactoryControllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class SuspiciousAccountTransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SuspiciousAccountTransferService accountService;

    static SuspiciousAccountTransferDto accountDto, newAccountDto, updateAccountDto;

    @BeforeEach
    public void setUp() {
        accountDto = TestFactoryControllers.createAccountDto(1L);
        TestFactoryControllers.mockAccountService(accountService, 1L, accountDto);

        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<SuspiciousAccountTransferDto> accountDtos = ids.stream()
                .map(TestFactoryControllers::createAccountDto)
                .collect(Collectors.toList());
        TestFactoryControllers.mockAccountServiceForReadAll(accountService, ids, accountDtos);
    }

    @Test
    @DisplayName("Тест на успешное получение SuspiciousAccountTransfer с ID 1")
    public void testPositiveGetAccountById() throws Exception {
        mockMvc.perform(get("/suspicious/account/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное получение SuspiciousAccountTransfer с ID 1")
    public void testNegativeGetAccountById() throws Exception {
        when(accountService.findById(1L)).thenThrow(new EntityNotFoundException("Не существующий id = 1"));

        mockMvc.perform(get("/suspicious/account/transfer/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }

    @Test
    @DisplayName("Тест на успешное получение всех SuspiciousAccountTransfer по ID")
    public void testPositiveGetAccountByIdAll() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        mockMvc.perform(get("/suspicious/account/transfer")
                        .param("ids", ids.stream().map(String::valueOf).toArray(String[]::new))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(ids.size()));
    }

    @Test
    @DisplayName("Тест на неуспешное получение всех SuspiciousAccountTransfer по ID")
    public void testNegativeGetAccountByIdAll() throws Exception {
        when(accountService.findAllById(Arrays.asList(1L, 2L, 3L)))
                .thenThrow(new EntityNotFoundException("Не существующий id = 1, 2, 3"));

        mockMvc.perform(get("/suspicious/account/transfer")
                        .param("ids", "1", "2", "3"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Не существующий id = 1, 2, 3",
                        Objects.requireNonNull(result.getResolvedException()).getMessage())
                );
    }

    @Test
    @DisplayName("Тест на успешное создание SuspiciousAccountTransfer и возврат DTO")
    public void testPositiveAccountCreate() throws Exception {
        newAccountDto = TestFactoryControllers.createAccountDto(1L);
        mockMvc.perform(post("/suspicious/account/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAccountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Тест на неуспешное создание SuspiciousAccountTransfer и возврат DTO")
    public void testNegativeAccountCreate() throws Exception {
        mockMvc.perform(post("/suspicious/account/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertFalse(exception.getMessage().contains("Отсутствует необходимое тело запроса"));
                });
    }

    @Test
    @DisplayName("Тест на успешное обновление SuspiciousAccountTransfer и возврат DTO")
    public void testPositiveAccountUpdate() throws Exception {
        updateAccountDto = TestFactoryControllers.createAccountDto(1L);
        mockMvc.perform(put("/suspicious/account/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateAccountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Teст на неуспешное обновление SuspiciousAccountTransfer и возврат DTO")
    public void testNegativeAccountUpdate() throws Exception {
        mockMvc.perform(put("/suspicious/account/transfer/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertFalse(exception.getMessage().contains("Отсутствует необходимое тело запроса"));
                });
    }
}
