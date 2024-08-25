package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.service.AuditService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для {@link AuditEntity}
 */
@RestController
@RequiredArgsConstructor
@Tag(
        name = "Аудит",
        description =
                "API для управления данными аудита, предоставления доступа к информации об аудитах по идентификатору")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/audit")
public class AuditController {

    AuditService service;

    /**
     * @param id технический идентификатор {@link AuditEntity}
     * @return {@link ResponseEntity<AuditDto>}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение аудит по идентификатору",
            description = "Возвращает аудит по указанному идентификатору",
            tags = {"Audit"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная обработка запроса",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditDto.class))
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
    public AuditDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
