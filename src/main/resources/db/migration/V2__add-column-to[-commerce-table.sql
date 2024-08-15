ALTER TABLE commerces ADD COLUMN is_active BOOLEAN;
UPDATE commerces SET is_active = true WHERE is_active IS NULL;
