CREATE TABLE refresh_token
(
    id         bigserial PRIMARY KEY,
    name       uuid        NOT NULL,
    expire     timestamp   NOT NULL,
    account_id uuid UNIQUE NOT NULL REFERENCES account (id) ON DELETE CASCADE
)