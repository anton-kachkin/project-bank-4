package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/audit")
@Tag(name = "Аудит", description = "API для управления данными аудита")
public class AuditController {

    private final AuditService service;

    /**
     * @param id технический идентификатор {@link AuditEntity}
     * @return {@link ResponseEntity<AuditDto>}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение данных",
            description = "Возвращает запись с id",
            method = "GET",
            tags = {"Audit"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Не найден аудит с ID")))
    }
    )
    public AuditDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
