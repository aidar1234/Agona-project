package com.technokratos.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountInfoResponse {

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String gender;

    private String aboutMe;

    private LocalDateTime birthDate;
}
