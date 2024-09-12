CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    status TEXT NOT NULL,
    order_date DATE,
    expected_delivery_date DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    sup_id BIGINT REFERENCES suppliers(id),
    com_id BIGINT REFERENCES commerces(id)
);