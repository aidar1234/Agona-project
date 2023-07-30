package com.technokratos.dto.response;

import com.technokratos.dto.PublicationType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PublicationResponse {

    private UUID id;

    private String description;

    private PublicationType type;

    private List<String> fileNames;
}
