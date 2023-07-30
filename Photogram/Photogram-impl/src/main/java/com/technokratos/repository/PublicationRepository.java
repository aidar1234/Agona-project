package com.technokratos.repository;

import com.technokratos.model.jooq.tables.pojos.PublicationEntity;

import java.util.List;
import java.util.UUID;

public interface PublicationRepository extends CrudRepository<PublicationEntity, UUID> {

    List<PublicationEntity> findListByAccountId(UUID id);
}
