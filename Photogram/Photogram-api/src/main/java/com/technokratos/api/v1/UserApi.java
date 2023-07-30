package com.technokratos.api.v1;

import com.technokratos.dto.response.PublicationResponse;
import com.technokratos.dto.response.SubscribersResponse;
import com.technokratos.dto.response.SubscriptionsResponse;
import com.technokratos.dto.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/users")
@Api(tags = "Users")
public interface UserApi {

    @GetMapping("/{username}")
    @ApiOperation(value = "Получение пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    ResponseEntity<UserResponse> getUser(@PathVariable String username);

    @GetMapping("{username}/subscribers")
    @ApiOperation(value = "Получение подписчиков по username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    ResponseEntity<SubscribersResponse> getSubscribers(@PathVariable String username);

    @GetMapping("/{username}/subscriptions")
    @ApiOperation(value = "Получение подписок по username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    ResponseEntity<SubscriptionsResponse> getSubscriptions(@PathVariable String username);

    @GetMapping("/{username}/publications")
    @ApiOperation(value = "Получение публикаций")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно"),
            @ApiResponse(code = 404, message = "Пользователь не найден")
    })
    ResponseEntity<List<PublicationResponse>> getPublications(@PathVariable String username);
}
