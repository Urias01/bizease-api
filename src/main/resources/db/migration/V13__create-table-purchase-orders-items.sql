CREATE TABLE purchase_order_items (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(10, 2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    prod_id BIGINT REFERENCES products(id),
    por_id BIGINT REFERENCES purchase_orders(id)
);