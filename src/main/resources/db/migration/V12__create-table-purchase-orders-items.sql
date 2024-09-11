CREATE TABLE purchase_order_items (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(10, 2),
    unit_selling_price NUMERIC(10, 2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    com_id BIGINT REFERENCES commerces(id),
    cat_id BIGINT REFERENCES categories(id)
);