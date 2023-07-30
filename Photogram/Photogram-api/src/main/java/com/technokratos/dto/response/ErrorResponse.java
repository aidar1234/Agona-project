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
@Builder
@Data
public class ErrorResponse {

    private String error;

    private Integer status;

    private String path;

    private LocalDateTime timestamp;
}
