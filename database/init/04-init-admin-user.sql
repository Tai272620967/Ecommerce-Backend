-- Add role column to users table if it doesn't exist
-- MySQL doesn't support IF NOT EXISTS for ALTER TABLE, so we use a stored procedure approach
SET @dbname = DATABASE();
SET @tablename = "users";
SET @columnname = "role";
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (table_name = @tablename)
      AND (table_schema = @dbname)
      AND (column_name = @columnname)
  ) > 0,
  "SELECT 1",
  CONCAT("ALTER TABLE ", @tablename, " ADD COLUMN ", @columnname, " VARCHAR(50) DEFAULT 'USER' NOT NULL")
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- Insert admin user: admin@gmail.com
-- Note: Password should be set manually in database using BCrypt hash
-- You can generate BCrypt hash using Spring Security BCryptPasswordEncoder
-- Only insert if admin user doesn't exist
INSERT INTO users (
    email, 
    password, 
    role, 
    first_name, 
    last_name, 
    is_email_verified,
    created_at, 
    updated_at,
    created_by,
    updated_by
) 
SELECT 
    'admin@gmail.com',
    '$2a$10$vz1UaXRBSPDiHXz2BB3iGen3OgI0yHl/UXyBcJ4RIYaY7YXMwenC.', -- Placeholder hash, update manually
    'ADMIN',
    'Admin',
    'User',
    true,
    NOW(),
    NOW(),
    'system',
    'system'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@gmail.com'
);

