CREATE TABLE account
(
    id            uuid PRIMARY KEY                     DEFAULT uuid_generate_v4(),
    email         varchar(255) UNIQUE         NOT NULL,
    hash_password varchar(72)                 NOT NULL, --BCrypt hash
    username      varchar(16) UNIQUE          NOT NULL,
    state         account_state               NOT NULL default 'NOT_CONFIRMED',
    role          account_role                NOT NULL default 'USER',
    first_name    varchar(32)                 NOT NULL,
    last_name     varchar(32)                 NOT NULL,
    gender        gender                      NOT NULL,
    about_me      varchar(128),
    birth_date    timestamp without time zone NOT NULL,
    created_date  timestamp without time zone NOT NULL DEFAULT now(),
    updated_date  timestamp without time zone NOT NULL DEFAULT now()
)
