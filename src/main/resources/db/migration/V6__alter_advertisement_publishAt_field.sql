SET timezone = 'Europe/Minsk';
ALTER TABLE app.advertisements
ALTER COLUMN published_at
SET DATA TYPE TIMESTAMP WITH TIME ZONE;