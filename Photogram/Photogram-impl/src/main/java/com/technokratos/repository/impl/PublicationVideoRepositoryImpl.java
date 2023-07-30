package com.technokratos.repository.impl;

import com.technokratos.mapper.VideoMapper;
import com.technokratos.model.jooq.tables.pojos.PublicationVideoEntity;
import com.technokratos.repository.PublicationVideoRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.technokratos.model.jooq.Tables.PUBLICATION_VIDEO_ENTITY;

@Repository
@RequiredArgsConstructor
public class PublicationVideoRepositoryImpl implements PublicationVideoRepository {

    private final DSLContext dsl;
    private final VideoMapper mapper;

    @Override
    public UUID save(PublicationVideoEntity publicationVideo) {
        return dsl.insertInto(PUBLICATION_VIDEO_ENTITY)
                .set(dsl.newRecord(PUBLICATION_VIDEO_ENTITY, publicationVideo))
                .returning()
                .fetchOne(PUBLICATION_VIDEO_ENTITY.ID);
    }

    @Override
    public Optional<PublicationVideoEntity> findById(UUID id) {
        return dsl.selectFrom(PUBLICATION_VIDEO_ENTITY)
                .where(PUBLICATION_VIDEO_ENTITY.ID.eq(id))
                .fetchOptionalInto(PublicationVideoEntity.class);
    }

    @Override
    public void update(PublicationVideoEntity publicationVideoEntity) {
        dsl.update(PUBLICATION_VIDEO_ENTITY)
                .set(dsl.newRecord(PUBLICATION_VIDEO_ENTITY, publicationVideoEntity))
                .where(PUBLICATION_VIDEO_ENTITY.ID.eq(publicationVideoEntity.getPublicationId()))
                .execute();
    }

    @Override
    public void deleteById(UUID id) {
        dsl.delete(PUBLICATION_VIDEO_ENTITY)
                .where(PUBLICATION_VIDEO_ENTITY.ID.eq(id))
                .execute();
    }

    @Override
    public List<PublicationVideoEntity> findPublicationVideoListByPublicationId(UUID id) {
        return dsl.selectFrom(PUBLICATION_VIDEO_ENTITY)
                .where(PUBLICATION_VIDEO_ENTITY.PUBLICATION_ID.eq(id))
                .fetch(mapper::getVideoEntity);
    }

    @Override
    public void deleteByPublicationId(UUID id) {
        dsl.delete(PUBLICATION_VIDEO_ENTITY)
                .where(PUBLICATION_VIDEO_ENTITY.PUBLICATION_ID.eq(id))
                .execute();
    }
}
