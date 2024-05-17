CREATE TABLE "users"
(
    "id"             BIGSERIAL PRIMARY KEY NOT NULL,
    "email"          varchar(255) UNIQUE   NOT NULL,
    "name"           varchar(255)          NOT NULL,
    "gender"         varchar(10),
    "phone"          varchar(20) UNIQUE    NOT NULL,
    "password"       varchar(255)          NOT NULL,
    "date_of_birth"  timestamp             NOT NULL,
    "avatar"         text,
    "address"        text,
    "is_first_login" bool                  NOT NULL DEFAULT true,

    "is_active"      boolean               NOT NULL DEFAULT true,
    "created_at"     bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"     bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "refresh_tokens"
(
    "id"            BIGSERIAL PRIMARY KEY NOT NULL,
    "user_id"       bigint                NOT NULL,
    "refresh_token" text                  NOT NULL,

    "is_active"     boolean               NOT NULL DEFAULT true,
    "created_at"    bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"    bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);


CREATE TABLE "roles"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "role_name"  varchar(100)          NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,

    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "user_roles"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "user_id"    bigint                NOT NULL,
    "role_id"    bigint                NOT NULL,

    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);



