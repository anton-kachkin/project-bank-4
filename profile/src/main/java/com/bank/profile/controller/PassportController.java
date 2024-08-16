package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.service.PassportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
 * Контроллер для {@link PassportEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/passport")
@Tag(name = "Паспорт", description = "API для управления данными паспортов")
public class PassportController {

    private final PassportService service;

    /**
     * @param id технический идентификатор {@link PassportEntity}
     * @return {@link ResponseEntity<PassportDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(
            summary = "Получение данных",
            description = "Возвращает запись с id",
            method = "GET",
            tags = {"Passport"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "passport с данным id не найден!")))
    }
    )
    public ResponseEntity<PassportDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param passport {@link PassportDto}
     * @return {@link ResponseEntity<PassportDto>}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание данных",
            description = "Создание новой записи",
            method = "POST",
            tags = {"Passport"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportDto.class))),
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
    public ResponseEntity<PassportDto> create(@Validated @RequestBody PassportDto passport) {
        return ResponseEntity.ok(service.save(passport));
    }

    /**
     * @param passport {@link PassportDto}
     * @param id       технический идентификатор {@link PassportEntity}
     * @return {@link ResponseEntity<PassportDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновление данных",
            description = "Обновление существующей записи с id",
            method = "PUT",
            tags = {"Passport"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Обновление невозможно, passport не найден!")))
    }
    )
    public ResponseEntity<PassportDto> update(@PathVariable Long id, @Validated @RequestBody PassportDto passport) {
        return ResponseEntity.ok(service.update(id, passport));
    }

    /**
     * @param ids лист технических идентификаторов {@link PassportDto}
     * @return {@link ResponseEntity} {@link List<PassportDto>}
     */
    @GetMapping("read/all")
    @Operation(
            summary = "Получение списка данных",
            description = "Возвращение записей по списку ids",
            method = "GET",
            tags = {"Passport"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PassportDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "passport с данным id не найден!")))
    }
    )
    public ResponseEntity<List<PassportDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
