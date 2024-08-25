package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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
 * Контроллер для {@link SuspiciousPhoneTransferDto}
 */
@RestController
@RequiredArgsConstructor
@Tag(
        name = "Подозрительные переводы по номеру телефона",
        description = "API для обработки подозрительных переводов по номерам телефонов")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/suspicious/phone/transfer")
public class SuspiciousPhoneTransferController {

    SuspiciousPhoneTransferService service;

    /**
     * @param id технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousPhoneTransferDto}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение подозрительного перевода по номеру телефона по идентификатору",
            description = """
                    Возвращает информацию о подозрительном переводе по номеру телефона,
                    используя указанный идентификатор
                    """,
            tags = {"SuspiciousPhoneTransfer"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousPhoneTransferDto.class))
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
    public ResponseEntity<SuspiciousPhoneTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} c листом {@link SuspiciousPhoneTransferDto}
     */
    @GetMapping
    @Operation(
            summary = "Получение идентификаторов всех подозрительных переводов по телефонным номерам",
            description = """
                    Возвращает список всех подозрительных переводов по телефонным номерам, зарегистрированных в системе
                    """,
            tags = {"SuspiciousPhoneTransfer"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousPhoneTransferDto.class))
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
    public ResponseEntity<List<SuspiciousPhoneTransferDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousPhoneTransferDto}
     * @return {@link ResponseEntity} {@link SuspiciousPhoneTransferDto}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание нового подозрительного перевода по номеру телефона",
            description = "Создает новый подозрительный перевод по номеру телефона с предоставленными данными",
            tags = {"SuspiciousPhoneTransfer"},
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousPhoneTransferDto.class))
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
    public ResponseEntity<SuspiciousPhoneTransferDto> create(
            @Valid @RequestBody SuspiciousPhoneTransferDto suspiciousTransfer) {
        return ResponseEntity.ok(service.save(suspiciousTransfer));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousPhoneTransferDto}
     * @param id                 технический идентификатор {@link SuspiciousPhoneTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousPhoneTransferDto}
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных подозрительного перевода по номеру телефона",
            description = "Обновляет данные подозрительного перевода по номеру телефона",
            tags = {"SuspiciousPhoneTransfer"},
            method = "PUT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousPhoneTransferDto.class))
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
    public ResponseEntity<SuspiciousPhoneTransferDto> update(
            @Valid @RequestBody SuspiciousPhoneTransferDto suspiciousTransfer,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(id, suspiciousTransfer));
    }
}
