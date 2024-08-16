package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.service.ActualRegistrationService;
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
 * Контроллер для {@link ActualRegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/actual/registration")
@Tag(name = "Регистрация", description = "API для управления регистрационными данными")
public class ActualRegistrationController {

    private final ActualRegistrationService service;

    /**
     * @param id технический идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(
            summary = "Получение данных",
            description = "Возвращает запись с id",
            method = "GET",
            tags = {"Actual Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActualRegistrationDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "actualRegistration с данным id не найден!")))
    }
    )
    public ResponseEntity<ActualRegistrationDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание данных",
            description = "Создание новой записи",
            method = "POST",
            tags = {"Actual Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActualRegistrationDto.class))),
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
    public ResponseEntity<ActualRegistrationDto> create(@RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.save(actualRegistration));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @param id                 технический идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновление данных",
            description = "Обновление существующей записи с id",
            method = "PUT",
            tags = {"Actual Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActualRegistrationDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Обновление невозможно, ActualRegistration не найден!")))
    }
    )
    public ResponseEntity<ActualRegistrationDto> update(@PathVariable Long id,
                                                        @RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.update(id, actualRegistration));
    }

    /**
     * @param ids лист технических идентификаторов {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity} {@link List<ActualRegistrationDto>}
     */
    @GetMapping("read/all")
    @Operation(
            summary = "Получение списка данных",
            description = "Возвращение записей по списку ids",
            method = "GET",
            tags = {"Actual Registration"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActualRegistrationDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "actualRegistration с данным id не найден!")))
    }
    )
    public ResponseEntity<List<ActualRegistrationDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
