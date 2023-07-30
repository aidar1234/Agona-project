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


/**
 * Convenience access to all tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.account</code>.
     */
    public static final Account ACCOUNT_ENTITY = Account.ACCOUNT_ENTITY;

    /**
     * The table <code>public.account_image</code>.
     */
    public static final AccountImage ACCOUNT_IMAGE_ENTITY = AccountImage.ACCOUNT_IMAGE_ENTITY;

    /**
     * The table <code>public.publication</code>.
     */
    public static final Publication PUBLICATION_ENTITY = Publication.PUBLICATION_ENTITY;

    /**
     * The table <code>public.publication_image</code>.
     */
    public static final PublicationImage PUBLICATION_IMAGE_ENTITY = PublicationImage.PUBLICATION_IMAGE_ENTITY;

    /**
     * The table <code>public.publication_video</code>.
     */
    public static final PublicationVideo PUBLICATION_VIDEO_ENTITY = PublicationVideo.PUBLICATION_VIDEO_ENTITY;

    /**
     * The table <code>public.refresh_token</code>.
     */
    public static final RefreshToken REFRESH_TOKEN_ENTITY = RefreshToken.REFRESH_TOKEN_ENTITY;

    /**
     * The table <code>public.subscriber_subscription</code>.
     */
    public static final SubscriberSubscription SUBSCRIBER_SUBSCRIPTION_ENTITY = SubscriberSubscription.SUBSCRIBER_SUBSCRIPTION_ENTITY;
}