/*
 * This file is generated by jOOQ.
 */
package com.technokratos.model.jooq.tables;


import com.technokratos.model.jooq.Keys;
import com.technokratos.model.jooq.Public;
import com.technokratos.model.jooq.tables.records.PublicationImageRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PublicationImage extends TableImpl<PublicationImageRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.publication_image</code>
     */
    public static final PublicationImage PUBLICATION_IMAGE_ENTITY = new PublicationImage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PublicationImageRecord> getRecordType() {
        return PublicationImageRecord.class;
    }

    /**
     * The column <code>public.publication_image.id</code>.
     */
    public final TableField<PublicationImageRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("uuid_generate_v4()", SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.publication_image.extension</code>.
     */
    public final TableField<PublicationImageRecord, String> EXTENSION = createField(DSL.name("extension"), SQLDataType.VARCHAR(16).nullable(false), this, "");

    /**
     * The column <code>public.publication_image.size</code>.
     */
    public final TableField<PublicationImageRecord, Long> SIZE = createField(DSL.name("size"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.publication_image.publication_id</code>.
     */
    public final TableField<PublicationImageRecord, UUID> PUBLICATION_ID = createField(DSL.name("publication_id"), SQLDataType.UUID, this, "");

    private PublicationImage(Name alias, Table<PublicationImageRecord> aliased) {
        this(alias, aliased, null);
    }

    private PublicationImage(Name alias, Table<PublicationImageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.publication_image</code> table reference
     */
    public PublicationImage(String alias) {
        this(DSL.name(alias), PUBLICATION_IMAGE_ENTITY);
    }

    /**
     * Create an aliased <code>public.publication_image</code> table reference
     */
    public PublicationImage(Name alias) {
        this(alias, PUBLICATION_IMAGE_ENTITY);
    }

    /**
     * Create a <code>public.publication_image</code> table reference
     */
    public PublicationImage() {
        this(DSL.name("publication_image"), null);
    }

    public <O extends Record> PublicationImage(Table<O> child, ForeignKey<O, PublicationImageRecord> key) {
        super(child, key, PUBLICATION_IMAGE_ENTITY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<PublicationImageRecord> getPrimaryKey() {
        return Keys.PUBLICATION_IMAGE_PKEY;
    }

    @Override
    public List<UniqueKey<PublicationImageRecord>> getKeys() {
        return Arrays.<UniqueKey<PublicationImageRecord>>asList(Keys.PUBLICATION_IMAGE_PKEY);
    }

    @Override
    public List<ForeignKey<PublicationImageRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PublicationImageRecord, ?>>asList(Keys.PUBLICATION_IMAGE__PUBLICATION_IMAGE_PUBLICATION_ID_FKEY);
    }

    private transient Publication _publication;

    public Publication publication() {
        if (_publication == null)
            _publication = new Publication(this, Keys.PUBLICATION_IMAGE__PUBLICATION_IMAGE_PUBLICATION_ID_FKEY);

        return _publication;
    }

    @Override
    public PublicationImage as(String alias) {
        return new PublicationImage(DSL.name(alias), this);
    }

    @Override
    public PublicationImage as(Name alias) {
        return new PublicationImage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PublicationImage rename(String name) {
        return new PublicationImage(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PublicationImage rename(Name name) {
        return new PublicationImage(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, Long, UUID> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
