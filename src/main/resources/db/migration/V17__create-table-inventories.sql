CREATE TABLE inventories (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    quantity INTEGER NOT NULL,
    location VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    com_id BIGINT REFERENCES commerces(id)
);