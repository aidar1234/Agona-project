package com.technokratos.dto.request;

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
public class AccountSignInRequest {

    @ApiModelProperty(required = true, value = "max length = 255")
    private String email;

    @ApiModelProperty(required = true, value = "min length = 8, max length = 64")
    private String password;
}
