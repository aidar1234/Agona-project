package com.technokratos.api.v1;

import com.technokratos.dto.request.AccountSignInRequest;
import com.technokratos.dto.request.AccountSignUpRequest;
import com.technokratos.dto.response.AuthResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/auth")
@Api(tags = "Auth")
public interface AuthApi {

    @GetMapping("/token/{refresh-token}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Обновление access и refresh токенов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 404, message = "Аккаунт не найден")
    })
    ResponseEntity<AuthResponse> authenticate(@PathVariable("refresh-token") UUID refreshToken);

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Вход в аккаунт")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации"),
            @ApiResponse(code = 404, message = "Аккаунт не найден")
    })
    ResponseEntity<AuthResponse> signIn(@RequestBody AccountSignInRequest request);

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Создание аккаунта")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Аккаунт создан"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    void signUp(@RequestBody AccountSignUpRequest request);

    @PutMapping("/logout")
    @ApiOperation(value = "Выход из аккаунта")
    @ResponseStatus(HttpStatus.OK)
    void logout();

    @GetMapping("/verify/{token}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Подтверждение электронной почты")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 400, message = "Ошибка валидации")
    })
    void verifyEmail(@PathVariable String token);

}
