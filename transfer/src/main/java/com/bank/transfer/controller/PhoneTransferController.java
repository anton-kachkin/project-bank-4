package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.service.PhoneTransferService;
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
 * Контроллер для {@link PhoneTransferDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/phone")
@Tag(name = "Контроллер для переводов по номеру телефона", description = "API для взаимодействия с переводами по" +
        " номеру телефона")
public class PhoneTransferController {

    private final PhoneTransferService service;

    /**
     * @param ids список технических идентификаторов {@link PhoneTransferEntity}
     * @return {@link ResponseEntity} c листом {@link PhoneTransferDto}
     */
    @GetMapping("/read/all")
    @Operation(
            summary = "Получить все переводы",
            description = "Возвращает данные переводов по указанным идентификаторам",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность отправляемого запроса"))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность передаваемых идентификаторов"))
            )
    })
    public ResponseEntity<List<PhoneTransferDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    /**
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return {@link ResponseEntity} {@link PhoneTransferDto}
     */
    @GetMapping("/read/{id}")
    @Operation(
            summary = "Получить данные перевода",
            description = "Возвращает данные перевода по его идентификатору",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность отправляемого запроса"))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Запись не найдена",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Запись с переданным id не существует"))
            )
    })
    public PhoneTransferDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param phoneTransfer {@link PhoneTransferDto}
     * @return {@link ResponseEntity } {@link PhoneTransferDto}
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создать перевод",
            description = "Создает новый перевод",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность отправляемого запроса"))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Конфликт данных",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Возможно, запись с таким id уже существует"))
            )
    })
    public ResponseEntity<PhoneTransferDto> create(@RequestBody PhoneTransferDto phoneTransfer) {
        return ResponseEntity.ok(service.save(phoneTransfer));
    }

    /**
     * @param phoneTransfer {@link PhoneTransferDto}
     * @param id            технический идентификатор {@link PhoneTransferEntity}
     * @return {@link ResponseEntity} {@link PhoneTransferDto}
     */
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Обновить перевод",
            description = "Обновляет существующий перевод",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountTransferDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Проверьте корректность отправляемого запроса"))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Запись не существует",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Запись с переданным id не существует"))
            ),
            @ApiResponse(
                    responseCode = "409", description = "Конфликт данных",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Возможно, запись занята другим процессом"))
            )
    })
    public ResponseEntity<PhoneTransferDto> update(@PathVariable("id") Long id,
                                                   @RequestBody PhoneTransferDto phoneTransfer) {
        return ResponseEntity.ok(service.update(id, phoneTransfer));
    }
}
