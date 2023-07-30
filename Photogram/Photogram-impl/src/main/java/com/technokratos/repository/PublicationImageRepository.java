package com.technokratos.repository;

import com.technokratos.model.jooq.tables.pojos.PublicationImageEntity;

import java.util.List;
import java.util.UUID;

public interface PublicationImageRepository extends CrudRepository<PublicationImageEntity, UUID> {

    List<PublicationImageEntity> findPublicationImageListByPublicationId(UUID id);

    void deleteByPublicationId(UUID id);
}
