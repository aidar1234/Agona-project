/*
 * This file is generated by jOOQ.
 */
package com.technokratos.model.jooq;


import com.technokratos.model.jooq.tables.Account;
import com.technokratos.model.jooq.tables.AccountImage;
import com.technokratos.model.jooq.tables.Publication;
import com.technokratos.model.jooq.tables.PublicationImage;
import com.technokratos.model.jooq.tables.PublicationVideo;
import com.technokratos.model.jooq.tables.RefreshToken;
import com.technokratos.model.jooq.tables.SubscriberSubscription;
import com.technokratos.model.jooq.tables.records.AccountImageRecord;
import com.technokratos.model.jooq.tables.records.AccountRecord;
import com.technokratos.model.jooq.tables.records.PublicationImageRecord;
import com.technokratos.model.jooq.tables.records.PublicationRecord;
import com.technokratos.model.jooq.tables.records.PublicationVideoRecord;
import com.technokratos.model.jooq.tables.records.RefreshTokenRecord;
import com.technokratos.model.jooq.tables.records.SubscriberSubscriptionRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> ACCOUNT_EMAIL_KEY = Internal.createUniqueKey(Account.ACCOUNT_ENTITY, DSL.name("account_email_key"), new TableField[] { Account.ACCOUNT_ENTITY.EMAIL }, true);
    public static final UniqueKey<AccountRecord> ACCOUNT_PKEY = Internal.createUniqueKey(Account.ACCOUNT_ENTITY, DSL.name("account_pkey"), new TableField[] { Account.ACCOUNT_ENTITY.ID }, true);
    public static final UniqueKey<AccountRecord> ACCOUNT_USERNAME_KEY = Internal.createUniqueKey(Account.ACCOUNT_ENTITY, DSL.name("account_username_key"), new TableField[] { Account.ACCOUNT_ENTITY.USERNAME }, true);
    public static final UniqueKey<AccountImageRecord> ACCOUNT_IMAGE_PKEY = Internal.createUniqueKey(AccountImage.ACCOUNT_IMAGE_ENTITY, DSL.name("account_image_pkey"), new TableField[] { AccountImage.ACCOUNT_IMAGE_ENTITY.ID }, true);
    public static final UniqueKey<PublicationRecord> PUBLICATION_PKEY = Internal.createUniqueKey(Publication.PUBLICATION_ENTITY, DSL.name("publication_pkey"), new TableField[] { Publication.PUBLICATION_ENTITY.ID }, true);
    public static final UniqueKey<PublicationImageRecord> PUBLICATION_IMAGE_PKEY = Internal.createUniqueKey(PublicationImage.PUBLICATION_IMAGE_ENTITY, DSL.name("publication_image_pkey"), new TableField[] { PublicationImage.PUBLICATION_IMAGE_ENTITY.ID }, true);
    public static final UniqueKey<PublicationVideoRecord> PUBLICATION_VIDEO_PKEY = Internal.createUniqueKey(PublicationVideo.PUBLICATION_VIDEO_ENTITY, DSL.name("publication_video_pkey"), new TableField[] { PublicationVideo.PUBLICATION_VIDEO_ENTITY.ID }, true);
    public static final UniqueKey<RefreshTokenRecord> REFRESH_TOKEN_ACCOUNT_ID_KEY = Internal.createUniqueKey(RefreshToken.REFRESH_TOKEN_ENTITY, DSL.name("refresh_token_account_id_key"), new TableField[] { RefreshToken.REFRESH_TOKEN_ENTITY.ACCOUNT_ID }, true);
    public static final UniqueKey<RefreshTokenRecord> REFRESH_TOKEN_PKEY = Internal.createUniqueKey(RefreshToken.REFRESH_TOKEN_ENTITY, DSL.name("refresh_token_pkey"), new TableField[] { RefreshToken.REFRESH_TOKEN_ENTITY.ID }, true);
    public static final UniqueKey<SubscriberSubscriptionRecord> SUBSCRIBER_SUBSCRIPTION_PKEY = Internal.createUniqueKey(SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY, DSL.name("subscriber_subscription_pkey"), new TableField[] { SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY.SUBSCRIBER_ID, SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY.SUBSCRIPTION_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AccountImageRecord, AccountRecord> ACCOUNT_IMAGE__ACCOUNT_IMAGE_ACCOUNT_ID_FKEY = Internal.createForeignKey(AccountImage.ACCOUNT_IMAGE_ENTITY, DSL.name("account_image_account_id_fkey"), new TableField[] { AccountImage.ACCOUNT_IMAGE_ENTITY.ACCOUNT_ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT_ENTITY.ID }, true);
    public static final ForeignKey<PublicationRecord, AccountRecord> PUBLICATION__PUBLICATION_ACCOUNT_ID_FKEY = Internal.createForeignKey(Publication.PUBLICATION_ENTITY, DSL.name("publication_account_id_fkey"), new TableField[] { Publication.PUBLICATION_ENTITY.ACCOUNT_ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT_ENTITY.ID }, true);
    public static final ForeignKey<PublicationImageRecord, PublicationRecord> PUBLICATION_IMAGE__PUBLICATION_IMAGE_PUBLICATION_ID_FKEY = Internal.createForeignKey(PublicationImage.PUBLICATION_IMAGE_ENTITY, DSL.name("publication_image_publication_id_fkey"), new TableField[] { PublicationImage.PUBLICATION_IMAGE_ENTITY.PUBLICATION_ID }, Keys.PUBLICATION_PKEY, new TableField[] { Publication.PUBLICATION_ENTITY.ID }, true);
    public static final ForeignKey<PublicationVideoRecord, PublicationRecord> PUBLICATION_VIDEO__PUBLICATION_VIDEO_PUBLICATION_ID_FKEY = Internal.createForeignKey(PublicationVideo.PUBLICATION_VIDEO_ENTITY, DSL.name("publication_video_publication_id_fkey"), new TableField[] { PublicationVideo.PUBLICATION_VIDEO_ENTITY.PUBLICATION_ID }, Keys.PUBLICATION_PKEY, new TableField[] { Publication.PUBLICATION_ENTITY.ID }, true);
    public static final ForeignKey<RefreshTokenRecord, AccountRecord> REFRESH_TOKEN__REFRESH_TOKEN_ACCOUNT_ID_FKEY = Internal.createForeignKey(RefreshToken.REFRESH_TOKEN_ENTITY, DSL.name("refresh_token_account_id_fkey"), new TableField[] { RefreshToken.REFRESH_TOKEN_ENTITY.ACCOUNT_ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT_ENTITY.ID }, true);
    public static final ForeignKey<SubscriberSubscriptionRecord, AccountRecord> SUBSCRIBER_SUBSCRIPTION__SUBSCRIBER_SUBSCRIPTION_SUBSCRIBER_ID_FKEY = Internal.createForeignKey(SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY, DSL.name("subscriber_subscription_subscriber_id_fkey"), new TableField[] { SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY.SUBSCRIBER_ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT_ENTITY.ID }, true);
    public static final ForeignKey<SubscriberSubscriptionRecord, AccountRecord> SUBSCRIBER_SUBSCRIPTION__SUBSCRIBER_SUBSCRIPTION_SUBSCRIPTION_ID_FKEY = Internal.createForeignKey(SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY, DSL.name("subscriber_subscription_subscription_id_fkey"), new TableField[] { SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY.SUBSCRIPTION_ID }, Keys.ACCOUNT_PKEY, new TableField[] { Account.ACCOUNT_ENTITY.ID }, true);
}