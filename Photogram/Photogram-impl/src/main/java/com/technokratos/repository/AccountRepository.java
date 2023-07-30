package com.technokratos.repository;

import com.technokratos.model.jooq.tables.pojos.AccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {

    UUID save(AccountEntity account);

    void subscribe(UUID subscriberId, UUID subscriptionId);

    void unsubscribe(UUID subscriberId, UUID subscriptionId);

    List<String> findSubscribersById(UUID id);

    List<String> findSubscriptionsById(UUID id);

    List<String> findSubscribersByUsername(String username);

    List<String> findSubscriptionsByUsername(String username);

    Optional<AccountEntity> findById(UUID id);

    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByUsername(String username);
}
