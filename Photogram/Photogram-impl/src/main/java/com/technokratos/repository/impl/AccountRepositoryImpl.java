package com.technokratos.repository.impl;

import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.model.jooq.tables.records.SubscriberSubscriptionRecord;
import com.technokratos.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.technokratos.model.jooq.Tables.ACCOUNT_ENTITY;
import static com.technokratos.model.jooq.Tables.SUBSCRIBER_SUBSCRIPTION_ENTITY;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final DSLContext dsl;

    @Override
    public UUID save(AccountEntity account) {
        return dsl.insertInto(ACCOUNT_ENTITY)
                .set(dsl.newRecord(ACCOUNT_ENTITY, account))
                .returning()
                .fetchOne(ACCOUNT_ENTITY.ID);
    }

    @Override
    public void subscribe(UUID subscriberId, UUID subscriptionId) {
        dsl.insertInto(SUBSCRIBER_SUBSCRIPTION_ENTITY)
                .set(new SubscriberSubscriptionRecord(subscriberId, subscriptionId))
                .execute();
    }

    @Override
    public void unsubscribe(UUID subscriberId, UUID subscriptionId) {
        dsl.execute("DELETE FROM subscriber_subscription WHERE subscriber_id = :subscriber_id AND subscription_id = :subscription_id", subscriberId, subscriptionId);
    }

    @Override
    public List<String> findSubscribersById(UUID id) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                        .where(ACCOUNT_ENTITY.ID.in(dsl.fetch("SELECT subscriber_id FROM subscriber_subscription WHERE subscription_id = :id", id)))
                        .fetch(ACCOUNT_ENTITY.USERNAME);
    }

    @Override
    public List<String> findSubscriptionsById(UUID id) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                        .where(ACCOUNT_ENTITY.ID.in(dsl.fetch("SELECT subscription_id FROM subscriber_subscription WHERE subscriber_id = :id", id)))
                        .fetch(ACCOUNT_ENTITY.USERNAME);
    }

    @Override
    public List<String> findSubscribersByUsername(String username) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                .where(ACCOUNT_ENTITY.ID.in(dsl.fetch("SELECT subscriber_id FROM subscriber_subscription WHERE subscription_id = :id",
                        dsl.selectFrom(ACCOUNT_ENTITY).where(ACCOUNT_ENTITY.USERNAME.eq(username)).fetchOne(ACCOUNT_ENTITY.ID))))
                .fetch(ACCOUNT_ENTITY.USERNAME);
    }

    @Override
    public List<String> findSubscriptionsByUsername(String username) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                .where(ACCOUNT_ENTITY.ID.in(dsl.fetch("SELECT subscription_id FROM subscriber_subscription WHERE subscriber_id = :id",
                        dsl.selectFrom(ACCOUNT_ENTITY).where(ACCOUNT_ENTITY.USERNAME.eq(username)).fetchOne(ACCOUNT_ENTITY.ID))))
                .fetch(ACCOUNT_ENTITY.USERNAME);
    }

    @Override
    public Optional<AccountEntity> findById(UUID id) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                .where(ACCOUNT_ENTITY.ID.eq(id))
                .fetchOptionalInto(AccountEntity.class);
    }


    @Override
    public Optional<AccountEntity> findByEmail(String email) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                .where(ACCOUNT_ENTITY.EMAIL.eq(email))
                .fetchOptionalInto(AccountEntity.class);
    }

    @Override
    public Optional<AccountEntity> findByUsername(String username) {
        return dsl.selectFrom(ACCOUNT_ENTITY)
                .where(ACCOUNT_ENTITY.USERNAME.eq(username))
                .fetchOptionalInto(AccountEntity.class);
    }

    @Override
    public void update(AccountEntity account) {
        dsl.update(ACCOUNT_ENTITY)
                .set(dsl.newRecord(ACCOUNT_ENTITY, account))
                .where(ACCOUNT_ENTITY.ID.eq(account.getId()))
                .execute();
    }

    @Override
    public void deleteById(UUID id) {
        dsl.delete(ACCOUNT_ENTITY)
                .where(ACCOUNT_ENTITY.ID.eq(id))
                .execute();
    }
}
