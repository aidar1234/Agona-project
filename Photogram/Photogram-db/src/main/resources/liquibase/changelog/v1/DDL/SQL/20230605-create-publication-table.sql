CREATE TABLE publication
(
    id           uuid PRIMARY KEY                     DEFAULT uuid_generate_v4(),
    description  varchar(512),
    type         publication_type            NOT NULL,
    account_id   uuid REFERENCES account (id),
    created_date timestamp without time zone NOT NULL DEFAULT now(),
    updated_date timestamp without time zone NOT NULL DEFAULT now()
)