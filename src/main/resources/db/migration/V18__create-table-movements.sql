CREATE TABLE movements (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    type TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    from_location VARCHAR(100),
    to_location VARCHAR(100),
    movement_date DATE,
    observation VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id BIGINT REFERENCES users(id),
    prod_id BIGINT REFERENCES products(id),
    inv_id BIGINT REFERENCES inventories(id)
);