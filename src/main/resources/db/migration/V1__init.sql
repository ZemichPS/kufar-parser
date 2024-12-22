DROP TABLE IF EXISTS app.users CASCADE;
DROP TABLE IF EXISTS app.advertisements CASCADE;
DROP TABLE IF EXISTS app.geo CASCADE;
DROP TABLE IF EXISTS app.manufactures CASCADE;
DROP TABLE IF EXISTS app.models CASCADE ;

CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.advertisements
(
    id           UUID PRIMARY KEY,
    ad_id        BIGSERIAL,
    link         TEXT,
    category     VARCHAR(100),
    company_ad   BOOLEAN,
    currency     VARCHAR(10),
    published_at TIMESTAMP,
    subject      TEXT,
    type         VARCHAR(10),
    price_in_byn NUMERIC,
    price_in_usd NUMERIC,
    details      TEXT,
    parameters   JSONB,
    fully_functional BOOLEAN,
    CONSTRAINT uniq_ad_id UNIQUE (ad_id)
);

CREATE TABLE app.users
(
    id               UUID PRIMARY KEY,
    username         VARCHAR(50),
    first_name       VARCHAR(50),
    last_name        VARCHAR(50),
    register_at      TIMESTAMP,
    telegram_chat_id BIGSERIAL,
    CONSTRAINT unique_chat_id UNIQUE (telegram_chat_id)
);

CREATE TABLE app.geo
(
    id     BIGSERIAL PRIMARY KEY,
    pid    BIGSERIAL,
    name   VARCHAR(100),
    type   VARCHAR(100),
    tag    TEXT,
    region INTEGER,
    area   INTEGER
);

CREATE TABLE app.manufactures
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE app.models
(
    id             UUID PRIMARY KEY,
    manufacture_id BIGSERIAL,
    name           VARCHAR(100),
    FOREIGN KEY (manufacture_id) REFERENCES app.manufactures (id)

)