CREATE TABLE account_image
(
    id         uuid PRIMARY KEY default uuid_generate_v4(),
    extension  varchar(16) NOT NULL,
    size       bigint      NOT NULL,
    account_id uuid REFERENCES account (id)
)