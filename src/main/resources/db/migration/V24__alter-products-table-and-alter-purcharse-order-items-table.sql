ALTER TABLE products
DROP COLUMN IF EXISTS expiration_date;

ALTER TABLE purchase_order_items
ADD COLUMN expiration_date DATE;