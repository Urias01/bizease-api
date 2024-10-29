CREATE TABLE sales_order_items (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(7, 2) NOT NULL,
    status VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    prod_id BIGINT REFERENCES products(id),
    sor_id BIGINT REFERENCES sales_orders(id)
)