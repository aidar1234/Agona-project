package com.technokratos.api.v1;

import com.technokratos.dto.request.AccountEditRequest;
import com.technokratos.dto.request.AccountPasswordChangeRequest;
import com.technokratos.dto.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/account")
@Api(tags = "Account")
public interface AccountApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение своего аккаунта")
    ResponseEntity<AccountResponse> getAccount();

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение подробной информации о свём аккаунте")
    ResponseEntity<AccountInfoResponse> getAccountInfo();

    @GetMapping("/subscribers")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение подписчиков")
    ResponseEntity<SubscribersResponse> getSubscribers();

    @GetMapping("/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получение подписок")
    ResponseEntity<SubscriptionsResponse> getSubscriptions();

    @PutMapping("/subscribe/{username}")
    @ApiOperation(value = "Подписаться на другого пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    ResponseEntity<AccountResponse> subscribe(@PathVariable String username);

    @PutMapping("/unsubscribe/{username}")
    @ApiOperation(value = "Отписаться от другого пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    ResponseEntity<AccountResponse> unsubscribe(@PathVariable String username);

    @PutMapping
    @ApiOperation(value = "Редактирование свего аккаунта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountEditRequest request);

    @PutMapping("/change-password")
    @ApiOperation(value = "Изменение пароля")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    void changePassword(@RequestBody AccountPasswordChangeRequest request);

    @PutMapping("/image")
    @ApiOperation(value = "Изменение аватарки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    ResponseEntity<AccountResponse> changeAccountImage(@RequestParam MultipartFile file);

    @GetMapping("/publications")
    @ApiOperation(value = "Получение публикаций")
    ResponseEntity<List<PublicationResponse>> getPublications();

    // TODO DELETE all publications
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Удаление своего аккаунта")
    void deleteAccount();
}
