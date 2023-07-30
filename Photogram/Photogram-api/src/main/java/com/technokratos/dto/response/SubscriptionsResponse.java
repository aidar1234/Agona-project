package com.technokratos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubscriptionsResponse {

    @ApiModelProperty("List of usernames")
    private List<String> subscriptions;

    private int count;
}
