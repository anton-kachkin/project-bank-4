package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.service.AccountDetailsIdService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

/**
 * Контроллер для {@link AccountDetailsIdEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account/details")
@Tag(name = "Банковский счет", description = "API для взаимодействия с данными банковских счетов")
public class AccountDetailsIdController {

    private final AccountDetailsIdService service;

    /**
     * @param id технический идентификатор {@link AccountDetailsIdEntity}
     * @return {@link ResponseEntity<AccountDetailsIdDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(
            summary = "Получение данных",
            description = "Возвращает запись с id",
            method = "GET",
            tags = {"Account Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsIdDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "accountDetailsId с данным id не найден!")))
    }
    )
    public ResponseEntity<AccountDetailsIdDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param accountDetailsId {@link AccountDetailsIdDto}
     * @return {@link ResponseEntity<AccountDetailsIdDto>}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание данных",
            description = "Создание новой записи",
            method = "POST",
            tags = {"Account Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsIdDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Конфликт данных",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте актуальность обновляемых данных.")))
    }
    )
    public ResponseEntity<AccountDetailsIdDto> create(@RequestBody AccountDetailsIdDto accountDetailsId) {
        return ResponseEntity.ok(service.save(accountDetailsId));
    }

    /**
     * @param accountDetailsId {@link AccountDetailsIdDto}
     * @param id               технический идентификатор {@link AccountDetailsIdEntity}
     * @return {@link ResponseEntity<AccountDetailsIdDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновление данных",
            description = "Обновление существующей записи с id",
            method = "PUT",
            tags = {"Account Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsIdDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Обновление невозможно, accountDetailsId не найден!")))
    }
    )
    public ResponseEntity<AccountDetailsIdDto> update(@PathVariable Long id,
                                                      @RequestBody AccountDetailsIdDto accountDetailsId) {
        return ResponseEntity.ok(service.update(id, accountDetailsId));
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountDetailsIdEntity}
     * @return {@link ResponseEntity} с листом {@link List<AccountDetailsIdDto>}
     */
    @GetMapping("read/all")
    @Operation(
            summary = "Получение списка данных",
            description = "Возвращение записей по списку ids",
            method = "GET",
            tags = {"Account Details"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsIdDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "accountDetailsId с данным id не найден!")))
    }
    )
    public ResponseEntity<List<AccountDetailsIdDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
