ALTER TABLE movements
    RENAME COLUMN from_location TO origin;

ALTER TABLE movements
    RENAME COLUMN to_location TO destination;