/*
 * This file is generated by jOOQ.
 */
package com.technokratos.model.jooq;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.refresh_token_id_seq</code>
     */
    public static final Sequence<Long> REFRESH_TOKEN_ID_SEQ = Internal.createSequence("refresh_token_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
