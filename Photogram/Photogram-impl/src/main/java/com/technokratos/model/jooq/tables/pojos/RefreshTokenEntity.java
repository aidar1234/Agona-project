/*
 * This file is generated by jOOQ.
 */
package com.technokratos.model.jooq.tables.pojos;


import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@Builder
@EqualsAndHashCode
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RefreshTokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long          id;
    private UUID          name;
    private LocalDateTime expire;
    private UUID          accountId;

    public RefreshTokenEntity() {}

    public RefreshTokenEntity(RefreshTokenEntity value) {
        this.id = value.id;
        this.name = value.name;
        this.expire = value.expire;
        this.accountId = value.accountId;
    }

    public RefreshTokenEntity(
        Long          id,
        UUID          name,
        LocalDateTime expire,
        UUID          accountId
    ) {
        this.id = id;
        this.name = name;
        this.expire = expire;
        this.accountId = accountId;
    }

    /**
     * Getter for <code>public.refresh_token.id</code>.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.refresh_token.id</code>.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>public.refresh_token.name</code>.
     */
    public UUID getName() {
        return this.name;
    }

    /**
     * Setter for <code>public.refresh_token.name</code>.
     */
    public void setName(UUID name) {
        this.name = name;
    }

    /**
     * Getter for <code>public.refresh_token.expire</code>.
     */
    public LocalDateTime getExpire() {
        return this.expire;
    }

    /**
     * Setter for <code>public.refresh_token.expire</code>.
     */
    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }

    /**
     * Getter for <code>public.refresh_token.account_id</code>.
     */
    public UUID getAccountId() {
        return this.accountId;
    }

    /**
     * Setter for <code>public.refresh_token.account_id</code>.
     */
    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RefreshTokenEntity (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(expire);
        sb.append(", ").append(accountId);

        sb.append(")");
        return sb.toString();
    }
}
