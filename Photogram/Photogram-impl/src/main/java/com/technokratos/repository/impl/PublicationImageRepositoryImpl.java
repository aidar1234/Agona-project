package com.technokratos.repository.impl;

import com.technokratos.mapper.ImageMapper;
import com.technokratos.model.jooq.tables.pojos.PublicationImageEntity;
import com.technokratos.repository.PublicationImageRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.technokratos.model.jooq.Tables.PUBLICATION_IMAGE_ENTITY;

@Repository
@RequiredArgsConstructor
public class PublicationImageRepositoryImpl implements PublicationImageRepository {

    private final DSLContext dsl;
    private final ImageMapper mapper;

    @Override
    public UUID save(PublicationImageEntity publicationImage) {
        return dsl.insertInto(PUBLICATION_IMAGE_ENTITY)
                .set(dsl.newRecord(PUBLICATION_IMAGE_ENTITY, publicationImage))
                .returning()
                .fetchOne(PUBLICATION_IMAGE_ENTITY.ID);
    }

    @Override
    public Optional<PublicationImageEntity> findById(UUID id) {
        return dsl.selectFrom(PUBLICATION_IMAGE_ENTITY)
                .where(PUBLICATION_IMAGE_ENTITY.ID.eq(id))
                .fetchOptionalInto(PublicationImageEntity.class);
    }

    @Override
    public void update(PublicationImageEntity publicationImageEntity) {
        dsl.update(PUBLICATION_IMAGE_ENTITY)
                .set(dsl.newRecord(PUBLICATION_IMAGE_ENTITY, publicationImageEntity))
                .where(PUBLICATION_IMAGE_ENTITY.ID.eq(publicationImageEntity.getId()))
                .execute();
    }

    @Override
    public void deleteById(UUID id) {
        dsl.delete(PUBLICATION_IMAGE_ENTITY)
                .where(PUBLICATION_IMAGE_ENTITY.ID.eq(id))
                .execute();
    }

    @Override
    public List<PublicationImageEntity> findPublicationImageListByPublicationId(UUID id) {
        return dsl.selectFrom(PUBLICATION_IMAGE_ENTITY)
                .where(PUBLICATION_IMAGE_ENTITY.PUBLICATION_ID.eq(id))
                .fetch(mapper::getImageEntity);
    }

    @Override
    public void deleteByPublicationId(UUID id) {
        dsl.delete(PUBLICATION_IMAGE_ENTITY)
                .where(PUBLICATION_IMAGE_ENTITY.PUBLICATION_ID.eq(id))
                .execute();
    }
}
