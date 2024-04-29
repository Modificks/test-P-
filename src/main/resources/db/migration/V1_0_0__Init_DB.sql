CREATE TABLE animal
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(64)    NOT NULL,
    weight   NUMERIC(19, 2) NOT NULL,
    cost     NUMERIC(19, 2) NOT NULL,
    category SMALLINT       NOT NULL,
    type     VARCHAR        NOT NULL,
    sex      VARCHAR        NOT NULL
);