package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.service.ProfileService;
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
 * Контроллер для {@link ProfileEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Банковский профиль", description = "API для управления данными банковских профилей")
public class ProfileController {

    private final ProfileService service;

    /**
     * @param id технический идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(
            summary = "Получение данных",
            description = "Возвращает запись с id",
            method = "GET",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "profile с данным id не найден!")))
    }
    )
    public ResponseEntity<ProfileDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param profile {@link ProfileDto}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание данных",
            description = "Создание новой записи",
            method = "POST",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDto.class))),
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
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profile) {
        return ResponseEntity.ok(service.save(profile));
    }

    /**
     * @param profile {@link ProfileDto}
     * @param id      технический идентификатор {@link ProfileEntity}
     * @return {@link ResponseEntity<ProfileDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновление данных",
            description = "Обновление существующей записи с id",
            method = "PUT",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Обновление невозможно, profile не найден!")))
    }
    )
    public ResponseEntity<ProfileDto> update(@PathVariable Long id, @RequestBody ProfileDto profile) {
        return ResponseEntity.ok(service.update(id, profile));
    }

    /**
     * @param ids лист технических идентификаторов {@link ProfileEntity}
     * @return {@link ResponseEntity} {@link List<ProfileDto>}
     */
    @GetMapping("read/all")
    @Operation(
            summary = "Получение списка данных",
            description = "Возвращение записей по списку ids",
            method = "GET",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfileDto.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность применяемого запроса"))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "profile с данным id не найден!")))
    }
    )
    public ResponseEntity<List<ProfileDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
