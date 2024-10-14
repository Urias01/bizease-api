ALTER TABLE users
ADD COLUMN is_active integer;

update users set is_active = 1 where is_active is null;