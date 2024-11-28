ALTER TABLE categories ALTER COLUMN uuid TYPE uuid USING uuid::uuid;
ALTER TABLE suppliers ALTER COLUMN uuid TYPE uuid USING uuid::uuid;
