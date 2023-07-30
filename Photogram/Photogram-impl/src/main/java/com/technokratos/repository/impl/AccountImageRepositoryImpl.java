package com.technokratos.repository.impl;

import com.technokratos.model.jooq.tables.pojos.AccountImageEntity;
import com.technokratos.repository.AccountImageRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.technokratos.model.jooq.Tables.ACCOUNT_IMAGE_ENTITY;
import static com.technokratos.model.jooq.Tables.PUBLICATION_IMAGE_ENTITY;

@Repository
@RequiredArgsConstructor
public class AccountImageRepositoryImpl implements AccountImageRepository {

    private final DSLContext dsl;

    @Override
    public UUID save(AccountImageEntity accountImageEntity) {
        return dsl.insertInto(ACCOUNT_IMAGE_ENTITY)
                .set(dsl.newRecord(ACCOUNT_IMAGE_ENTITY, accountImageEntity))
                .returning()
                .fetchOne(ACCOUNT_IMAGE_ENTITY.ID);
    }

    @Override
    public Optional<AccountImageEntity> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void update(AccountImageEntity accountImageEntity) {
        dsl.update(ACCOUNT_IMAGE_ENTITY)
                .set(dsl.newRecord(ACCOUNT_IMAGE_ENTITY, accountImageEntity))
                .where(ACCOUNT_IMAGE_ENTITY.ID.eq(accountImageEntity.getId()))
                .execute();
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public Optional<AccountImageEntity> findByAccountId(UUID id) {
        return dsl.selectFrom(ACCOUNT_IMAGE_ENTITY)
                .where(ACCOUNT_IMAGE_ENTITY.ACCOUNT_ID.eq(id))
                .fetchOptionalInto(AccountImageEntity.class);
    }
}
