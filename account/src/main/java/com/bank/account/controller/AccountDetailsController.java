package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.service.AccountDetailsService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для {@link AccountDetailsEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/details")
@Tag(name = "Контроллер для банковских счетов", description = "API для взаимодействия с банковскими счетами")
public class AccountDetailsController {

    private final AccountDetailsService service;

    /**
     * @param id технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить данные",
            description = "Возвращает данные банковского счета по указанному id",
            tags = {"account details"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsDto.class))
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
            )
    })
    public AccountDetailsDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param accountDetails - сущность для создания в виде {@link AccountDetailsDto}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Отправить данные",
            description = "Создает новый банковский счет",
            tags = {"account details"},
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса."))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Конфликт данных",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте актуальность обновляемых данных."))
            )
    })
    public ResponseEntity<AccountDetailsDto> create(@RequestBody AccountDetailsDto accountDetails) {
        return ResponseEntity.ok(service.save(accountDetails));
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновить данные",
            description = "Обновляет существующий банковский счет по указанному id",
            tags = {"account details"},
            method = "PUT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса."))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Конфликт данных",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте актуальность обновляемых данных."))
            )
    })
    public ResponseEntity<AccountDetailsDto> update(@PathVariable Long id,
                                                    @RequestBody AccountDetailsDto accountDetails) {
        return ResponseEntity.ok(service.update(id, accountDetails));
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link List<AccountDetailsDto>}.
     */
    @GetMapping("read/all")
    @Operation(
            summary = "Получить данные",
            description = "Возвращает данные банковских счетов по указанным ids",
            tags = {"account details"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDetailsDto.class))
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
            )
    })
    public ResponseEntity<List<AccountDetailsDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
