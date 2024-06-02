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



CREATE TABLE "materials"
(
    "id"            BIGSERIAL PRIMARY KEY NOT NULL,
    "material_name" varchar(100)          NOT NULL,
    "is_active"     boolean               NOT NULL DEFAULT true,
    "created_at"    bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"    bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "material_price_list"
(
    "id"          BIGSERIAL PRIMARY KEY NOT NULL,
    "material_id" bigint                NOT NULL,
    "buy_price"   bigint                NOT NULL,
    "sell_price"  bigint                NOT NULL,
    "effect_date" bigint                NOT NULL,
    "is_active"   boolean               NOT NULL DEFAULT true,
    "created_at"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "product_categories"
(
    "id"            BIGSERIAL PRIMARY KEY NOT NULL,
    "category_name" varchar(100)          NOT NULL,
    "category_type" varchar(50),
    "is_active"     boolean               NOT NULL DEFAULT true,
    "created_at"    bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"    bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "product_materials"
(
    "id"          BIGSERIAL PRIMARY KEY NOT NULL,
    "product_id"  bigint                NOT NULL,
    "material_id" bigint                NOT NULL,
    "weight"      float                 NOT NULL,
    "is_active"   boolean               NOT NULL DEFAULT true,
    "created_at"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "gems"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "gem_code"   varchar(50),
    "gem_name"   varchar(100),
    "origin"     varchar(50),
    "color"      varchar(50),
    "clarity"    varchar(50),
    "cut"        varchar,
    "carat"      float                 NOT NULL,
    "quantity"   bigint                NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "gem_price_list"
(
    "id"          BIGSERIAL PRIMARY KEY NOT NULL,
    "origin"      varchar(50),
    "color"       varchar(50),
    "clarity"     varchar(50),
    "cut"         varchar,
    "carat"       float                 NOT NULL,
    "buy_price"   bigint                NOT NULL,
    "sell_price"  bigint                NOT NULL,
    "effect_date" bigint                NOT NULL,

    "is_active"   boolean               NOT NULL DEFAULT true,
    "created_at"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "products"
(
    "id"              BIGSERIAL PRIMARY KEY NOT NULL,
    "product_code"    varchar(20)           NOT NULL,
    "product_name"    varchar(100)          NOT NULL,
    "product_image"   text,
    "gem_cost"        bigint,
    "production_cost" bigint,
    "gender"          varchar(10),
    "category_id"     bigint,
    "guarantee"       bigint,
    "is_gem"          boolean,
    "is_active"       boolean               NOT NULL DEFAULT true,
    "created_at"      bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"      bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "product_assets"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "media_key"  nvarchar              NOT NULL,
    "media_url"  nvarchar              NOT NULL,
    "product_id" bigint,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" timestamp             NOT NULL DEFAULT (now()),
    "updated_at" timestamp             NOT NULL DEFAULT (now())
);


CREATE TABLE "product_gem"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "gem_id"     bigint                NOT NULL,
    "product_id" bigint                NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "sizes"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "size"       float                 NOT NULL,
    "diameter"   float                 NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "size_products"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "product_id" bigint                NOT NULL,
    "size_id"    bigint                NOT NULL,
    "quantity"   bigint                NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
)



CREATE TABLE "orders"
(
    "id"             BIGSERIAL PRIMARY KEY NOT NULL,
    "customer_id"    bigint                NOT NULL,
    "staff_id"       bigint                NOT NULL,
    "total_amount"   float                 NOT NULL,
    "payment_status" varchar(50)           NOT NULL,
    "payment_method" varchar(50)           NOT NULL,
    "is_active"      boolean               NOT NULL DEFAULT true,
    "created_at"     bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"     bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "guarantees"
(
    "id"              BIGSERIAL PRIMARY KEY NOT NULL,
    "guarantee_name"  varchar(100),
    "guarantee_from"  bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "guarantee_to"    timestamp,
    "order_detail_id" bigint,
    "is_active"       boolean               NOT NULL DEFAULT true,
    "created_at"      bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"      bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);


CREATE TABLE "order_details"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "order_id"   bigint                NOT NULL,
    "product_id" bigint                NOT NULL,
    "size_id"    bigint,
    "quantity"   int                   NOT NULL DEFAULT 1,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "customers"
(
    "id"                     BIGSERIAL PRIMARY KEY NOT NULL,
    "customer_name"          varchar(255)          NOT NULL,
    "phone"                  varchar(20) UNIQUE    NOT NULL,
    "percent_discount"       float,
    "total_amount_purchased" bigint,
    "is_active"              boolean               NOT NULL DEFAULT true,
    "created_at"             bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"             bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);


CREATE TABLE "payments"
(
    "id"           BIGSERIAL PRIMARY KEY NOT NULL,
    "payment_code" varchar(100)          NOT NULL,
    "order_id"     bigint                NOT NULL,
    "status"       varchar,
    "total_price"  float,
    "is_active"    boolean               NOT NULL DEFAULT true,
    "created_at"   bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"   bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "purchase_order"
(
    "id"               BIGSERIAL PRIMARY KEY NOT NULL,
    "staff_id"         bigint                NOT NULL,
    "customer_name"    varchar(50),
    "phone"            varchar(16)           NOT NULL,
    "is_product_store" bool                  NOT NULL DEFAULT false,
    "total_price"      float,

    "is_active"        boolean               NOT NULL DEFAULT true,
    "created_at"       bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"       bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);

CREATE TABLE "purchase_detail_order"
(
    "id"                BIGSERIAL PRIMARY KEY NOT NULL,
    "name"              varchar(60),
    "product_id"        bigint,
    "purchase_order_id" bigint                NOT NULL,
    "material_id"       bigint,
    "weight"            float,
    "origin"            varchar(50),
    "color"             varchar(50),
    "clarity"           varchar(50),
    "cut"               varchar,
    "carat"             float,
    "price"             float,

    "is_active"         boolean               NOT NULL DEFAULT true,
    "created_at"        bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric),
    "updated_at"        bigint                NOT NULL DEFAULT (EXTRACT(epoch FROM now()) * 1000::numeric)
);





