package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Контроллер для {@link SuspiciousCardTransferDto}
 */

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Подозрительные переводы по номеру карты",
        description = "API для обработки подозрительных переводов по номеру карты")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/suspicious/card/transfer")
public class SuspiciousCardTransferController {

    SuspiciousCardTransferService service;

    /**
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousCardTransferDto}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение подозрительного перевода по номеру карты по идентификатору",
            description = """
                    Возвращает информацию о подозрительном переводе по номеру карты, используя указанный идентификатор
                    """,
            tags = {"SuspiciousCardTransfer"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousCardTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректная обработка запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность переданных данных"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запрашиваемая запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Запрос с несуществующим идентификатором"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка на стороне сервера",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Ошибка сервера. Пожалуйста, попробуйте позже")
                    )
            )
    })
    public ResponseEntity<SuspiciousCardTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity } c листом {@link SuspiciousCardTransferDto}
     */
    @GetMapping
    @Operation(
            summary = "Получение идентификаторов всех подозрительных переводов по номеру карты",
            description = """
                    Возвращает список всех подозрительных переводов по номеру карты, зарегистрированных в системе
                    """,
            tags = {"SuspiciousCardTransfer"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousCardTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректная обработка запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность переданных данных"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запрашиваемая запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Запрос с несуществующими идентификаторами"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка на стороне сервера",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Ошибка сервера. Пожалуйста, попробуйте позже")
                    )
            )
    })
    public ResponseEntity<List<SuspiciousCardTransferDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousCardTransferDto}
     * @return {@link ResponseEntity} {@link SuspiciousCardTransferDto}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание нового подозрительного перевода по номеру карты",
            description = "Создает новый подозрительный перевод по номеру карты с предоставленными данными",
            tags = {"SuspiciousCardTransfer"},
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousCardTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректная обработка запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность переданных данных"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запрашиваемая запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Запрос с несуществующим идентификатором"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка на стороне сервера",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Ошибка сервера. Пожалуйста, попробуйте позже")
                    )
            )
    })
    public ResponseEntity<SuspiciousCardTransferDto> create(
            @RequestBody SuspiciousCardTransferDto suspiciousTransfer) {
        return ResponseEntity.ok(service.save(suspiciousTransfer));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousCardTransferDto}
     * @param id                 технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousCardTransferDto}
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных подозрительного перевода по номеру карты",
            description = "Обновляет данные подозрительного перевода по номеру карты",
            tags = {"SuspiciousCardTransfer"},
            method = "PUT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousCardTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректная обработка запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность переданных данных"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запрашиваемая запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Запрос с несуществующим идентификатором"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка на стороне сервера",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Ошибка сервера. Пожалуйста, попробуйте позже")
                    )
            )
    })
    public ResponseEntity<SuspiciousCardTransferDto> update(
            @RequestBody SuspiciousCardTransferDto suspiciousTransfer,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(id, suspiciousTransfer));
    }
}
