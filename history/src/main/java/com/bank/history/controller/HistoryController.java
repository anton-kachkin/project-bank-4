package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller для {@link HistoryEntity}.
 */
@Tag(name = "Контроллер историй", description = "API для отслеживания историй")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService service;

    /**
     * @param id технический идентификатор {@link HistoryEntity}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить историю по ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История найдена",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistoryDto.class)) }),
            @ApiResponse(responseCode = "404", description = "История с указанным id не найдена",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос") })
    public ResponseEntity<HistoryDto> read(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    /**
     * @param id список технических идентификаторов {@link HistoryEntity}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @GetMapping
    @Operation(summary = "Получить все истории", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Истории найдены",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistoryDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Истории не найдены",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос") })
    public ResponseEntity<List<HistoryDto>> readAll(@RequestParam("id") List<Long> id) {
        return new ResponseEntity<>(service.readAllById(id), HttpStatus.OK);
    }

    /**
     * @param history {@link HistoryDto}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @PostMapping
    @Operation(summary = "Создать историю", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История успешно создана",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistoryDto.class)) }),
            @ApiResponse(responseCode = "404", description = "URL запроса не найден",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос указан неверно") })
    public ResponseEntity<HistoryDto> create(@Valid @RequestBody HistoryDto history) {
        return new ResponseEntity<>(service.create(history), HttpStatus.OK);
    }

    /**
     * @param id      технический идентификатор {@link HistoryEntity}
     * @param history {@link HistoryDto}
     * @return {@link ResponseEntity} c {@link HistoryDto} и HttpStatus OK
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить историю", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История успешно обновлена",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistoryDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос указан неверно",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "URL запроса не найден") })
    public ResponseEntity<HistoryDto> update(@PathVariable Long id,
                                             @RequestBody HistoryDto history) {
        return new ResponseEntity<>(service.update(id, history), HttpStatus.OK);
    }
}
