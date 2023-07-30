CREATE TABLE publication_image
(
    id             uuid PRIMARY KEY default uuid_generate_v4(),
    extension      varchar(16) NOT NULL,
    size           bigint      NOT NULL,
    publication_id uuid REFERENCES publication (id)
)