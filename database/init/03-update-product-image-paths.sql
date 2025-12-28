-- Update product image paths from /uploads/images/products/furniture/ to /uploads/images/products/
-- This migration removes the 'furniture' subdirectory from image paths

UPDATE products 
SET image_url = REPLACE(image_url, '/uploads/images/products/furniture/', '/uploads/images/products/')
WHERE image_url LIKE '/uploads/images/products/furniture/%';

-- Verify the update
-- SELECT id, name, image_url FROM products WHERE image_url LIKE '/uploads/images/products/%' LIMIT 10;

