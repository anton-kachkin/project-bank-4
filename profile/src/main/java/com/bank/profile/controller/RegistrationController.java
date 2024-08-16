package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.service.RegistrationService;
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
 * Контроллер для {@link RegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
@Tag(name = "Регистрация", description = "API для управления данными регистрации")
public class RegistrationController {

    private final RegistrationService service;

    /**
     * @param id технический идентификатор {@link RegistrationEntity}
     * @return {@link ResponseEntity<RegistrationDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(
            summary = "Получение данных",
            description = "Возвращает запись с id",
            method = "GET",
            tags = {"Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "registration с данным id не найден!")))
    }
    )
    public ResponseEntity<RegistrationDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param registration {@link RegistrationDto}
     * @return {@link ResponseEntity<RegistrationDto>}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание данных",
            description = "Создание новой записи",
            method = "POST",
            tags = {"Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationDto.class))),
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
    public ResponseEntity<RegistrationDto> create(@RequestBody RegistrationDto registration) {
        return ResponseEntity.ok(service.save(registration));
    }

    /**
     * @param registration {@link RegistrationDto}
     * @param id           технический идентификатор {@link RegistrationEntity}
     * @return {@link ResponseEntity<RegistrationDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновление данных",
            description = "Обновление существующей записи с id",
            method = "PUT",
            tags = {"Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Обновление невозможно, registration не найден!")))
    }
    )
    public ResponseEntity<RegistrationDto> update(@PathVariable Long id, @RequestBody RegistrationDto registration) {
        return ResponseEntity.ok(service.update(id, registration));
    }

    /**
     * @param ids лист технических идентификаторов {@link RegistrationEntity}
     * @return {@link ResponseEntity} {@link List<RegistrationDto>}
     */
    @GetMapping("read/all")
    @Operation(
            summary = "Получение списка данных",
            description = "Возвращение записей по списку ids",
            method = "GET",
            tags = {"Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "registration с данным id не найден!")))
    }
    )
    public ResponseEntity<List<RegistrationDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
