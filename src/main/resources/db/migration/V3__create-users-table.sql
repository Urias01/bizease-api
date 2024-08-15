CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  uuid UUID NOT NULL,
  name VARCHAR(100),
  email VARCHAR(254),
  password VARCHAR(255),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);