CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.advertisement
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
    CONSTRAINT uniq_ad_id UNIQUE (ad_id)
);

CREATE TABLE app.parameters
(
    id               UUID PRIMARY KEY,
    advertisement_id UUID,
    identity         VARCHAR(100),
    value            VARCHAR(100),
    label            VARCHAR(100),
    CONSTRAINT fk_parameter_on_advertisement FOREIGN KEY (advertisement_id) REFERENCES app.advertisement (id)
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
    tag    VARCHAR(100),
    region INTEGER,
    area   INTEGER
);
