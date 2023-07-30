package com.technokratos.api.v1;

import com.technokratos.dto.response.PublicationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/publications")
@Api(tags = "Publications")
public interface PublicationApi {

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение публикации по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Публикация не найдена"),
            @ApiResponse(code = 401, message = "Не авторизован")
    })
    ResponseEntity<PublicationResponse> getPublication(@PathVariable UUID id);

    @PostMapping("/image")
    @ApiOperation(value = "Создание публикации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 401, message = "Не авторизован")
    })
    void createImagePublication(@RequestParam String description, @RequestParam List<MultipartFile> files);

    @PostMapping("/video")
    @ApiOperation(value = "Создание публикации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 401, message = "Не авторизован")
    })
    void createVideoPublication(@RequestParam String description, @RequestParam MultipartFile file);

    @PutMapping("/{id}")
    @ApiOperation(value = "Обновление публикации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 401, message = "Не авторизован")
    })
    ResponseEntity<PublicationResponse> updatePublication(@PathVariable UUID id, @RequestParam String description);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление публикации по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Публикация не найдена"),
            @ApiResponse(code = 401, message = "Не авторизован")
    })
    void deletePublication(@PathVariable UUID id);
}
