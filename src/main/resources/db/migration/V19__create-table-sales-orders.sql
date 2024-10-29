CREATE TABLE sales_orders (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    status VARCHAR(30) NOT NULL,
    order_date DATE,
    delivery_date DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    com_id BIGINT REFERENCES commerces(id)
);