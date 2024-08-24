CREATE TABLE categories (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100),
  description VARCHAR(255),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  com_id BIGINT REFERENCES commerces(id)
);