package com.technokratos.repository;

import com.technokratos.model.jooq.tables.pojos.PublicationVideoEntity;

import java.util.List;
import java.util.UUID;

public interface PublicationVideoRepository extends CrudRepository<PublicationVideoEntity, UUID> {

    List<PublicationVideoEntity> findPublicationVideoListByPublicationId(UUID id);

    void deleteByPublicationId(UUID id);
}
