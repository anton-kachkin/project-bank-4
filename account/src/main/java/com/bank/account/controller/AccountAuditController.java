package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.service.AccountAuditService;
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
@Tag(name = "Контроллер для аудита", description = "API для взаимодействия с таблицей аудита")
public class AccountAuditController {

    private final AccountAuditService service;

    /**
     * @param id технический идентификатор {@link AuditEntity}
     * @return {@link ResponseEntity<AuditDto>}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить данные",
            description = "Возвращает запись аудита по указанному id",
            tags = {"audit"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса."))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Не существующий id"))
            ),
    })
    public AuditDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
