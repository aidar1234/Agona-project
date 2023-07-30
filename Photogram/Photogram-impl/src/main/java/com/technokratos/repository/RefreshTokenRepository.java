package com.technokratos.repository;

import com.technokratos.model.jooq.tables.pojos.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByName(UUID name);

    void deleteByAccountId(UUID id);

    Optional<RefreshTokenEntity> findByAccountId(UUID id);
}
