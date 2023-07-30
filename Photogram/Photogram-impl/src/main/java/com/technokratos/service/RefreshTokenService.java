package com.technokratos.service;

import com.technokratos.model.jooq.tables.pojos.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    Long createToken(RefreshTokenEntity refreshToken);

    Optional<RefreshTokenEntity> findTokenByName(UUID name);

    void updateToken(RefreshTokenEntity refreshToken);

    Optional<RefreshTokenEntity> findTokenByAccountId(UUID id);

    void deleteTokenByAccountId(UUID id);
}
