/*
 * This file is generated by jOOQ.
 */
package com.technokratos.model.jooq.tables.records;


import com.technokratos.model.jooq.tables.PublicationVideo;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PublicationVideoRecord extends UpdatableRecordImpl<PublicationVideoRecord> implements Record4<UUID, String, Long, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.publication_video.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.publication_video.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.publication_video.extension</code>.
     */
    public void setExtension(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.publication_video.extension</code>.
     */
    public String getExtension() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.publication_video.size</code>.
     */
    public void setSize(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.publication_video.size</code>.
     */
    public Long getSize() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>public.publication_video.publication_id</code>.
     */
    public void setPublicationId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.publication_video.publication_id</code>.
     */
    public UUID getPublicationId() {
        return (UUID) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, Long, UUID> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, String, Long, UUID> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return PublicationVideo.PUBLICATION_VIDEO_ENTITY.ID;
    }

    @Override
    public Field<String> field2() {
        return PublicationVideo.PUBLICATION_VIDEO_ENTITY.EXTENSION;
    }

    @Override
    public Field<Long> field3() {
        return PublicationVideo.PUBLICATION_VIDEO_ENTITY.SIZE;
    }

    @Override
    public Field<UUID> field4() {
        return PublicationVideo.PUBLICATION_VIDEO_ENTITY.PUBLICATION_ID;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getExtension();
    }

    @Override
    public Long component3() {
        return getSize();
    }

    @Override
    public UUID component4() {
        return getPublicationId();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getExtension();
    }

    @Override
    public Long value3() {
        return getSize();
    }

    @Override
    public UUID value4() {
        return getPublicationId();
    }

    @Override
    public PublicationVideoRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public PublicationVideoRecord value2(String value) {
        setExtension(value);
        return this;
    }

    @Override
    public PublicationVideoRecord value3(Long value) {
        setSize(value);
        return this;
    }

    @Override
    public PublicationVideoRecord value4(UUID value) {
        setPublicationId(value);
        return this;
    }

    @Override
    public PublicationVideoRecord values(UUID value1, String value2, Long value3, UUID value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PublicationVideoRecord
     */
    public PublicationVideoRecord() {
        super(PublicationVideo.PUBLICATION_VIDEO_ENTITY);
    }

    /**
     * Create a detached, initialised PublicationVideoRecord
     */
    public PublicationVideoRecord(UUID id, String extension, Long size, UUID publicationId) {
        super(PublicationVideo.PUBLICATION_VIDEO_ENTITY);

        setId(id);
        setExtension(extension);
        setSize(size);
        setPublicationId(publicationId);
    }
}