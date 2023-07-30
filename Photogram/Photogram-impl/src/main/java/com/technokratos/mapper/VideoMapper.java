package com.technokratos.mapper;

import com.technokratos.model.jooq.tables.pojos.PublicationVideoEntity;
import com.technokratos.model.jooq.tables.records.PublicationVideoRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    PublicationVideoEntity getVideoEntity(PublicationVideoRecord record);
}
