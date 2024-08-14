package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HistoryServiceImplTest {

    @Mock
    private HistoryMapper historyMapper;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryServiceImpl historyServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Успешное получение истории по id")
    void readByIdSuccess() {
        Long id = 1L;
        HistoryEntity historyEntity = new HistoryEntity();
        HistoryDto historyDto = new HistoryDto();

        when(historyRepository.findById(id)).thenReturn(Optional.of(historyEntity));
        when(historyMapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto result = historyServiceImpl.readById(id);

        assertNotNull(result);
        assertEquals(historyDto, result);
        verify(historyRepository, times(1)).findById(id);
        verify(historyMapper, times(1)).toDto(historyEntity);
    }

    @Test
    @DisplayName("Кейс, когда история по id не найдена")
    void readByIdNotFound() {
        Long id = 1L;
        when(historyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> historyServiceImpl.readById(id));
        verify(historyRepository, times(1)).findById(id);
        verify(historyMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Успешное получение всех историй по id")
    void readAllByIdSuccess() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<HistoryEntity> historyEntities = Arrays.asList(new HistoryEntity(), new HistoryEntity());
        List<HistoryDto> historyDtos = Arrays.asList(new HistoryDto(), new HistoryDto());

        when(historyRepository.findAllById(ids)).thenReturn(historyEntities);
        when(historyMapper.toListDto(historyEntities)).thenReturn(historyDtos);

        List<HistoryDto> result = historyServiceImpl.readAllById(ids);

        assertNotNull(result);
        assertEquals(historyDtos, result);
        verify(historyRepository, times(1)).findAllById(ids);
        verify(historyMapper, times(1)).toListDto(historyEntities);
    }

    @Test
    @DisplayName("Кейс, когда истории по id не найдены")
    void readAllByIdNotFound() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<HistoryEntity> historyEntities = List.of(new HistoryEntity());

        when(historyRepository.findAllById(ids)).thenReturn(historyEntities);

        assertThrows(EntityNotFoundException.class, () -> historyServiceImpl.readAllById(ids));
        verify(historyRepository, times(1)).findAllById(ids);
        verify(historyMapper, times(0)).toListDto(any());
    }

    @Test
    @DisplayName("Успешное создание истории")
    void createSuccess() {
        HistoryDto historyDto = new HistoryDto();
        HistoryEntity historyEntity = new HistoryEntity();

        when(historyMapper.toEntity(historyDto)).thenReturn(historyEntity);
        when(historyRepository.save(historyEntity)).thenReturn(historyEntity);
        when(historyMapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto result = historyServiceImpl.create(historyDto);
        assertNotNull(result);
        assertEquals(historyDto, result);
        verify(historyMapper, times(1)).toEntity(historyDto);
        verify(historyRepository, times(1)).save(historyEntity);
        verify(historyMapper, times(1)).toDto(historyEntity);
    }

    @Test
    @DisplayName("Неудачное создание истории - ошибка преобразования")
    void createFailureOnMapping() {
        HistoryDto historyDto = new HistoryDto();
        when(historyMapper.toEntity(historyDto)).thenThrow(new RuntimeException("Mapping error"));
        assertThrows(RuntimeException.class, () -> historyServiceImpl.create(historyDto));
        verify(historyMapper, times(1)).toEntity(historyDto);
        verify(historyRepository, times(0)).save(any());
        verify(historyMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Успешное обновление истории")
    void updateSuccess() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        HistoryEntity historyEntity = new HistoryEntity();

        when(historyRepository.findById(id)).thenReturn(Optional.of(historyEntity));
        when(historyMapper.mergeToEntity(historyDto, historyEntity)).thenReturn(historyEntity);
        when(historyRepository.save(historyEntity)).thenReturn(historyEntity);
        when(historyMapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto result = historyServiceImpl.update(id, historyDto);

        assertNotNull(result);
        assertEquals(historyDto, result);
        verify(historyRepository, times(1)).findById(id);
        verify(historyMapper, times(1)).mergeToEntity(historyDto, historyEntity);
        verify(historyRepository, times(1)).save(historyEntity);
        verify(historyMapper, times(1)).toDto(historyEntity);
    }

    @Test
    @DisplayName("Неудачное обновление истории")
    void updateNotFound() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();

        when(historyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> historyServiceImpl.update(id, historyDto));
        verify(historyRepository, times(1)).findById(id);
        verify(historyMapper, times(0)).mergeToEntity(any(), any());
        verify(historyRepository, times(0)).save(any());
        verify(historyMapper, times(0)).toDto(any());
    }
}
