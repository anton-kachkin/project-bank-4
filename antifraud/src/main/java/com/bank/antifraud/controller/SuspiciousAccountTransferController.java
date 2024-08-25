package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
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

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для {@link SuspiciousAccountTransferDto}
 */
@RestController
@RequiredArgsConstructor
@Tag(
        name = "Подозрительные переводы по номеру счета",
        description = "API для обработки подозрительных переводов по номеру счета")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/suspicious/account/transfer")
public class SuspiciousAccountTransferController {

    SuspiciousAccountTransferService service;

    /**
     * @param id технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousAccountTransferDto}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение подозрительного перевода по номеру счета по идентификатору",
            description = """
                    Возвращает информацию о подозрительном переводе по номеру счета, используя указанный идентификатор
                    """,
            tags = {"SuspiciousAccountTransfer"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousAccountTransferDto.class))
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
    public ResponseEntity<SuspiciousAccountTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} c листом {@link SuspiciousAccountTransferDto}
     */
    @GetMapping
    @Operation(
            summary = "Получение идентификаторов всех подозрительных переводов по номерам счетов",
            description = """
                    Возвращает список всех подозрительных переводов по номерам счетов, зарегистрированных в системе
                    """,
            tags = {"SuspiciousAccountTransfer"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousAccountTransferDto.class))
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
    public ResponseEntity<List<SuspiciousAccountTransferDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousAccountTransferDto}
     * @return {@link ResponseEntity} {@link SuspiciousAccountTransferDto}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание нового подозрительного перевода по номеру счета",
            description = "Создает новый подозрительный перевод по номеру счета с предоставленными данными",
            tags = {"SuspiciousAccountTransfer"},
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousAccountTransferDto.class))
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
    public ResponseEntity<SuspiciousAccountTransferDto> create(
            @Valid @RequestBody SuspiciousAccountTransferDto suspiciousTransfer) {
        return ResponseEntity.ok(service.save(suspiciousTransfer));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousAccountTransferDto}
     * @param id                 технический идентификатор {@link SuspiciousAccountTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousAccountTransferDto}
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных подозрительного перевода по номеру счета",
            description = "Обновляет данные подозрительного перевода по номеру счета",
            tags = {"SuspiciousAccountTransfer"},
            method = "PUT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousAccountTransferDto.class))
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
    public ResponseEntity<SuspiciousAccountTransferDto> update(
            @RequestBody SuspiciousAccountTransferDto suspiciousTransfer,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(id, suspiciousTransfer));
    }
}
