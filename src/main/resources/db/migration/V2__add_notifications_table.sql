CREATE TABLE app.notifications
(
    id           uuid,
    title        varchar(100),
    content      text,
    image_name   varchar(100),
    published_at DATE,
    PRIMARY KEY (id)
);