CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    cnpj VARCHAR(14),
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    address_number VARCHAR(6),
    neighborhood VARCHAR(100),
    city VARCHAR(100),
    uf VARCHAR(2),
    postal_code VARCHAR(8),
    category VARCHAR(50),
    phone_number VARCHAR(11),
    email VARCHAR(254),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE commerces_suppliers (
  sup_id BIGINT REFERENCES suppliers(id),
  com_id BIGINT REFERENCES commerces(id),
  PRIMARY KEY (sup_id, com_id)
);