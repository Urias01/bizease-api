CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    unit INTEGER NOT NULL,
    minimum_stock INTEGER,
    description VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    com_id BIGINT REFERENCES commerces(id) ON DELETE SET NULL
);