CREATE TABLE commerces (
  id BIGSERIAL PRIMARY KEY,
  uuid UUID NOT NULL,
  cnpj VARCHAR(14),
  name VARCHAR(100),
  phone_number VARCHAR(20),
  address VARCHAR(255),
  address_number VARCHAR(6),
  neighborhood VARCHAR(100),
  city VARCHAR(100),
  uf VARCHAR(2),
  postal_code VARCHAR(8),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);