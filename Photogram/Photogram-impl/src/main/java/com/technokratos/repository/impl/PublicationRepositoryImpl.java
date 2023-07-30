package com.technokratos.repository.impl;

import com.technokratos.mapper.PublicationMapper;
import com.technokratos.model.jooq.tables.pojos.PublicationEntity;
import com.technokratos.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.technokratos.model.jooq.Tables.PUBLICATION_ENTITY;

@Repository
@RequiredArgsConstructor
public class PublicationRepositoryImpl implements PublicationRepository {

    private final PublicationMapper mapper;
    private final DSLContext dsl;

    @Override
    public UUID save(PublicationEntity publication) {
        return dsl.insertInto(PUBLICATION_ENTITY)
                .set(dsl.newRecord(PUBLICATION_ENTITY, publication))
                .returning()
                .fetchOne(PUBLICATION_ENTITY.ID);
    }

    @Override
    public Optional<PublicationEntity> findById(UUID id) {
        return dsl.selectFrom(PUBLICATION_ENTITY)
                .where(PUBLICATION_ENTITY.ID.eq(id))
                .fetchOptionalInto(PublicationEntity.class);
    }

    @Override
    public void update(PublicationEntity publication) {
        dsl.update(PUBLICATION_ENTITY)
                .set(dsl.newRecord(PUBLICATION_ENTITY, publication))
                .where(PUBLICATION_ENTITY.ID.eq(publication.getId()))
                .execute();
    }

    @Override
    public void deleteById(UUID id) {
        dsl.delete(PUBLICATION_ENTITY)
                .where(PUBLICATION_ENTITY.ID.eq(id))
                .execute();
    }

    @Override
    public List<PublicationEntity> findListByAccountId(UUID id) {
        return dsl.selectFrom(PUBLICATION_ENTITY)
                .where(PUBLICATION_ENTITY.ACCOUNT_ID.eq(id))
                .fetch(mapper::getPublicationEntity);
    }
}
