package com.technokratos.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountEditRequest {

    @ApiModelProperty(required = true, value = "max length = 255")
    private String email;

    @ApiModelProperty(required = true, value = "min length = 1, max length = 16")
    private String username;

    @ApiModelProperty(required = true, value = "min length = 1, max length = 32")
    private String firstName;

    @ApiModelProperty(required = true, value = "min length = 1, max length = 32")
    private String lastName;

    @ApiModelProperty(required = true, value = "MALE or FEMALE")
    private String gender;

    @ApiModelProperty(value = "max length = 128")
    private String aboutMe;

    @ApiModelProperty(required = true)
    private LocalDateTime birthDate;

    @ApiModelProperty(required = true, value = "list of changed fields")
    private List<String> fields;

}
