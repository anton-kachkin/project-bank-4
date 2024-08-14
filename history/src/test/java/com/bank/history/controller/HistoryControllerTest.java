package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HistoryControllerTest {

    @Mock
    private HistoryServiceImpl historyService;

    @InjectMocks
    private HistoryController historyController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Получение истории по ID")
    void read() throws Exception {
        HistoryDto historyDto =
                new HistoryDto(1L, 1L, 1L, 1L, 1L,
                        1L, 1L
                );
        when(historyService.readById(1L)).thenReturn(historyDto);
        mockMvc.perform(get("/api/history/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.transferAuditId").value(1L))
                .andExpect(jsonPath("$.profileAuditId").value(1L))
                .andExpect(jsonPath("$.accountAuditId").value(1L))
                .andExpect(jsonPath("$.antiFraudAuditId").value(1L))
                .andExpect(jsonPath("$.publicBankInfoAuditId").value(1L))
                .andExpect(jsonPath("$.authorizationAuditId").value(1L));
        verify(historyService, times(1)).readById(1L);
    }

    @Test
    @DisplayName("Получение истории по ID - не найдено")
    void readNotFound() {
        when(historyService.readById(1L)).thenThrow(new EntityNotFoundException("Entity not found"));
        assertThrows(EntityNotFoundException.class, () -> historyController.read(1L));
        verify(historyService, times(1)).readById(1L);
    }

    @Test
    @DisplayName("Получение всех историй")
    void readAll() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<HistoryDto> historyDtos = Arrays.asList(new HistoryDto(), new HistoryDto());
        when(historyService.readAllById(ids)).thenReturn(historyDtos);

        ResponseEntity<List<HistoryDto>> responseEntity = historyController.readAll(ids);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(historyDtos, responseEntity.getBody());

        verify(historyService, times(1)).readAllById(ids);
    }

    @Test
    @DisplayName("Неудачное получение всех историй - некорректные идентификаторы")
    void readAllInvalidIds() throws Exception {
        List<Long> invalidIds = Arrays.asList(-1L, -2L);
        String invalidIdsJson = objectMapper.writeValueAsString(invalidIds);

        mockMvc.perform(get("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidIdsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Создание новой истории")
    void create() throws Exception {
        HistoryDto historyDto =
                new HistoryDto(1L, 1L, 1L, 1L, 1L,
                        1L, 1L
                );
        String historyJson = objectMapper.writeValueAsString(historyDto);
        mockMvc.perform(post("/api/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(historyJson))
                .andExpect(status().isOk());
        ArgumentCaptor<HistoryDto> captor = ArgumentCaptor.forClass(HistoryDto.class);
        verify(historyService, times(1)).create(captor.capture());
    }

    @Test
    @DisplayName("Неудачное создание новой истории - отсутствуют обязательные поля")
    void createMissingFields() throws Exception {
        HistoryDto invalidHistoryDto = new HistoryDto();
        String invalidHistoryJson = objectMapper.writeValueAsString(invalidHistoryDto);

        mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidHistoryJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление существующей истории")
    void update() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        when(historyService.update(id, historyDto)).thenReturn(historyDto);
        ResponseEntity<HistoryDto> responseEntity = historyController.update(id, historyDto);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(historyDto, responseEntity.getBody());
        verify(historyService, times(1)).update(id, historyDto);
    }

    @Test
    @DisplayName("Обновление существующей истории - не найдено")
    void updateNotFound() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        when(historyService.update(id, historyDto)).thenThrow(new EntityNotFoundException("History not found"));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            historyController.update(id, historyDto);
        });

        assertEquals("History not found", exception.getMessage());
        verify(historyService, times(1)).update(id, historyDto);
    }
}
