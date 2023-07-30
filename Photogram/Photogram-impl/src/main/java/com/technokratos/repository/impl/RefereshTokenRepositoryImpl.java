package com.technokratos.repository.impl;

import com.technokratos.model.jooq.tables.pojos.RefreshTokenEntity;
import com.technokratos.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.technokratos.model.jooq.Tables.REFRESH_TOKEN_ENTITY;

@Repository
@RequiredArgsConstructor
public class RefereshTokenRepositoryImpl implements RefreshTokenRepository {

    private final DSLContext dsl;

    @Override
    public Long save(RefreshTokenEntity refreshToken) {
        return dsl.insertInto(REFRESH_TOKEN_ENTITY)
                .set(dsl.newRecord(REFRESH_TOKEN_ENTITY, refreshToken))
                .returning()
                .fetchOne(REFRESH_TOKEN_ENTITY.ID);
    }

    @Override
    public Optional<RefreshTokenEntity> findById(Long id) {
        return dsl.selectFrom(REFRESH_TOKEN_ENTITY)
                .where(REFRESH_TOKEN_ENTITY.ID.eq(id))
                .fetchOptionalInto(RefreshTokenEntity.class);
    }

    @Override
    public void update(RefreshTokenEntity refreshToken) {
        dsl.update(REFRESH_TOKEN_ENTITY)
                .set(dsl.newRecord(REFRESH_TOKEN_ENTITY, refreshToken))
                .where(REFRESH_TOKEN_ENTITY.ID.eq(refreshToken.getId()))
                .execute();
    }

    @Override
    public void deleteById(Long id) {
        dsl.delete(REFRESH_TOKEN_ENTITY)
                .where(REFRESH_TOKEN_ENTITY.ID.eq(id))
                .execute();
    }

    @Override
    public void deleteByAccountId(UUID id) {
        dsl.delete(REFRESH_TOKEN_ENTITY)
                .where(REFRESH_TOKEN_ENTITY.ACCOUNT_ID.eq(id))
                .execute();
    }

    @Override
    public Optional<RefreshTokenEntity> findByAccountId(UUID id) {
        return dsl.selectFrom(REFRESH_TOKEN_ENTITY)
                .where(REFRESH_TOKEN_ENTITY.ACCOUNT_ID.eq(id))
                .fetchOptionalInto(RefreshTokenEntity.class);
    }

    @Override
    public Optional<RefreshTokenEntity> findByName(UUID name) {
        return dsl.selectFrom(REFRESH_TOKEN_ENTITY)
                .where(REFRESH_TOKEN_ENTITY.NAME.eq(name))
                .fetchOptionalInto(RefreshTokenEntity.class);
    }
}
