package com.technokratos.repository;

import com.technokratos.model.jooq.tables.pojos.AccountImageEntity;

import java.util.Optional;
import java.util.UUID;

public interface AccountImageRepository extends CrudRepository<AccountImageEntity, UUID> {

    Optional<AccountImageEntity> findByAccountId(UUID id);
}
