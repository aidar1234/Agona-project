/*
 * This file is generated by jOOQ.
 */
package com.technokratos.model.jooq.tables;


import com.technokratos.model.jooq.Keys;
import com.technokratos.model.jooq.Public;
import com.technokratos.model.jooq.enums.PublicationType;
import com.technokratos.model.jooq.tables.records.PublicationRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
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
public class Publication extends TableImpl<PublicationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.publication</code>
     */
    public static final Publication PUBLICATION_ENTITY = new Publication();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PublicationRecord> getRecordType() {
        return PublicationRecord.class;
    }

    /**
     * The column <code>public.publication.id</code>.
     */
    public final TableField<PublicationRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("uuid_generate_v4()", SQLDataType.UUID)), this, "");

    /**
     * The column <code>public.publication.description</code>.
     */
    public final TableField<PublicationRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(512), this, "");

    /**
     * The column <code>public.publication.type</code>.
     */
    public final TableField<PublicationRecord, PublicationType> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.technokratos.model.jooq.enums.PublicationType.class), this, "");

    /**
     * The column <code>public.publication.account_id</code>.
     */
    public final TableField<PublicationRecord, UUID> ACCOUNT_ID = createField(DSL.name("account_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.publication.created_date</code>.
     */
    public final TableField<PublicationRecord, LocalDateTime> CREATED_DATE = createField(DSL.name("created_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.publication.updated_date</code>.
     */
    public final TableField<PublicationRecord, LocalDateTime> UPDATED_DATE = createField(DSL.name("updated_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.LOCALDATETIME)), this, "");

    private Publication(Name alias, Table<PublicationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Publication(Name alias, Table<PublicationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.publication</code> table reference
     */
    public Publication(String alias) {
        this(DSL.name(alias), PUBLICATION_ENTITY);
    }

    /**
     * Create an aliased <code>public.publication</code> table reference
     */
    public Publication(Name alias) {
        this(alias, PUBLICATION_ENTITY);
    }

    /**
     * Create a <code>public.publication</code> table reference
     */
    public Publication() {
        this(DSL.name("publication"), null);
    }

    public <O extends Record> Publication(Table<O> child, ForeignKey<O, PublicationRecord> key) {
        super(child, key, PUBLICATION_ENTITY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<PublicationRecord> getPrimaryKey() {
        return Keys.PUBLICATION_PKEY;
    }

    @Override
    public List<UniqueKey<PublicationRecord>> getKeys() {
        return Arrays.<UniqueKey<PublicationRecord>>asList(Keys.PUBLICATION_PKEY);
    }

    @Override
    public List<ForeignKey<PublicationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PublicationRecord, ?>>asList(Keys.PUBLICATION__PUBLICATION_ACCOUNT_ID_FKEY);
    }

    private transient Account _account;

    public Account account() {
        if (_account == null)
            _account = new Account(this, Keys.PUBLICATION__PUBLICATION_ACCOUNT_ID_FKEY);

        return _account;
    }

    @Override
    public Publication as(String alias) {
        return new Publication(DSL.name(alias), this);
    }

    @Override
    public Publication as(Name alias) {
        return new Publication(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Publication rename(String name) {
        return new Publication(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Publication rename(Name name) {
        return new Publication(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, String, PublicationType, UUID, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}