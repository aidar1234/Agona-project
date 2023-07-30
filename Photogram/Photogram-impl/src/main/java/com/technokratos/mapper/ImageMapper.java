package com.technokratos.mapper;

import com.technokratos.model.jooq.tables.pojos.PublicationImageEntity;
import com.technokratos.model.jooq.tables.records.PublicationImageRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    PublicationImageEntity getImageEntity(PublicationImageRecord record);
}
