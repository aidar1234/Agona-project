package com.technokratos.mapper;

import com.technokratos.dto.response.PublicationResponse;
import com.technokratos.model.jooq.enums.PublicationType;
import com.technokratos.model.jooq.tables.pojos.PublicationEntity;
import com.technokratos.model.jooq.tables.records.PublicationRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PublicationMapper {

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    PublicationEntity getPublicationEntity(UUID accountId, PublicationType type, String description);

    PublicationResponse getPublicationResponse(PublicationEntity publication, List<String> fileNames);

    PublicationEntity getPublicationEntity(PublicationRecord publicationRecord);
}
