package com.technokratos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {

    @ApiModelProperty("JSON Web token")
    private String accessToken;

    @ApiModelProperty("Refresh token")
    private String refreshToken;
}
