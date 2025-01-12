CREATE TABLE app.categories
(
    id      varchar(20) PRIMARY KEY,
    name    varchar(100),
    version varchar(10),
    ordered varchar(10)
);

CREATE TABLE app.subcategories
(
    id       varchar(20) PRIMARY KEY,
    parent   varchar(100),
    name     varchar(100),
    version  varchar(10),
    redirect varchar(10),
    ordered  varchar(10),
    FOREIGN KEY (parent) REFERENCES app.categories (id)

)