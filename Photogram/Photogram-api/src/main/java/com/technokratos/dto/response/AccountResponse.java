package com.technokratos.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponse {

    private String username;

    private String firstName;

    private String lastName;

    private String aboutMe;

    private String imageName;
}
