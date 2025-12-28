-- Initialize Categories Data for Muji Furniture Store
-- This script creates sample data for maincategories, subcategories, and categories focused on furniture

-- Insert Main Categories for Furniture Store (5 main categories)
INSERT INTO maincategories (id, name, image_url, created_at, updated_at, created_by, updated_by) VALUES
(1, 'Living Room', '/uploads/images/categories/category-01.avif', NOW(), NOW(), 'system', 'system'),
(2, 'Bedroom', '/uploads/images/categories/category-02.avif', NOW(), NOW(), 'system', 'system'),
(3, 'Dining Room', '/uploads/images/categories/category-03.jpg', NOW(), NOW(), 'system', 'system'),
(4, 'Office', '/uploads/images/categories/category-04.avif', NOW(), NOW(), 'system', 'system'),
(5, 'Storage', '/uploads/images/categories/category-05.jpg', NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Sub Categories for Living Room
INSERT INTO subcategories (id, name, image_url, main_category_id, created_at, updated_at, created_by, updated_by) VALUES
(1, 'Sofas', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system'),
(2, 'Coffee Tables', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system'),
(3, 'TV Stands', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system'),
(4, 'Side Tables', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Sub Categories for Bedroom
INSERT INTO subcategories (id, name, image_url, main_category_id, created_at, updated_at, created_by, updated_by) VALUES
(5, 'Beds', '/uploads/images/categories/category-02.avif', 2, NOW(), NOW(), 'system', 'system'),
(6, 'Wardrobes', '/uploads/images/categories/category-02.avif', 2, NOW(), NOW(), 'system', 'system'),
(7, 'Dressers', '/uploads/images/categories/category-02.avif', 2, NOW(), NOW(), 'system', 'system'),
(8, 'Nightstands', '/uploads/images/categories/category-02.avif', 2, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Sub Categories for Dining Room
INSERT INTO subcategories (id, name, image_url, main_category_id, created_at, updated_at, created_by, updated_by) VALUES
(9, 'Dining Tables', '/uploads/images/categories/category-03.jpg', 3, NOW(), NOW(), 'system', 'system'),
(10, 'Dining Chairs', '/uploads/images/categories/category-03.jpg', 3, NOW(), NOW(), 'system', 'system'),
(11, 'Dining Sets', '/uploads/images/categories/category-03.jpg', 3, NOW(), NOW(), 'system', 'system'),
(12, 'Buffets & Sideboards', '/uploads/images/categories/category-03.jpg', 3, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Sub Categories for Office
INSERT INTO subcategories (id, name, image_url, main_category_id, created_at, updated_at, created_by, updated_by) VALUES
(13, 'Desks', '/uploads/images/categories/category-04.avif', 4, NOW(), NOW(), 'system', 'system'),
(14, 'Office Chairs', '/uploads/images/categories/category-04.avif', 4, NOW(), NOW(), 'system', 'system'),
(15, 'Bookcases', '/uploads/images/categories/category-04.avif', 4, NOW(), NOW(), 'system', 'system'),
(16, 'Filing Cabinets', '/uploads/images/categories/category-04.avif', 4, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Sub Categories for Storage
INSERT INTO subcategories (id, name, image_url, main_category_id, created_at, updated_at, created_by, updated_by) VALUES
(17, 'Shelving Units', '/uploads/images/categories/category-05.jpg', 5, NOW(), NOW(), 'system', 'system'),
(18, 'Storage Cabinets', '/uploads/images/categories/category-05.jpg', 5, NOW(), NOW(), 'system', 'system'),
(19, 'Storage Boxes', '/uploads/images/categories/category-05.jpg', 5, NOW(), NOW(), 'system', 'system'),
(20, 'Storage Baskets', '/uploads/images/categories/category-05.jpg', 5, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Sofas
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(1, '2-Seater Sofas', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system'),
(2, '3-Seater Sofas', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system'),
(3, 'Corner Sofas', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system'),
(4, 'Sofa Beds', '/uploads/images/categories/category-01.avif', 1, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Coffee Tables
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(5, 'Wood Coffee Tables', '/uploads/images/categories/category-01.avif', 2, NOW(), NOW(), 'system', 'system'),
(6, 'Glass Coffee Tables', '/uploads/images/categories/category-01.avif', 2, NOW(), NOW(), 'system', 'system'),
(7, 'Metal Coffee Tables', '/uploads/images/categories/category-01.avif', 2, NOW(), NOW(), 'system', 'system'),
(8, 'Nested Coffee Tables', '/uploads/images/categories/category-01.avif', 2, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for TV Stands
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(9, 'Low TV Stands', '/uploads/images/categories/category-01.avif', 3, NOW(), NOW(), 'system', 'system'),
(10, 'Wall-Mounted TV Stands', '/uploads/images/categories/category-01.avif', 3, NOW(), NOW(), 'system', 'system'),
(11, 'TV Cabinets', '/uploads/images/categories/category-01.avif', 3, NOW(), NOW(), 'system', 'system'),
(12, 'Media Consoles', '/uploads/images/categories/category-01.avif', 3, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Beds
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(13, 'Single Beds', '/uploads/images/categories/category-02.avif', 5, NOW(), NOW(), 'system', 'system'),
(14, 'Double Beds', '/uploads/images/categories/category-02.avif', 5, NOW(), NOW(), 'system', 'system'),
(15, 'Queen Beds', '/uploads/images/categories/category-02.avif', 5, NOW(), NOW(), 'system', 'system'),
(16, 'King Beds', '/uploads/images/categories/category-02.avif', 5, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Wardrobes
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(17, 'Sliding Door Wardrobes', '/uploads/images/categories/category-02.avif', 6, NOW(), NOW(), 'system', 'system'),
(18, 'Hinged Door Wardrobes', '/uploads/images/categories/category-02.avif', 6, NOW(), NOW(), 'system', 'system'),
(19, 'Open Wardrobes', '/uploads/images/categories/category-02.avif', 6, NOW(), NOW(), 'system', 'system'),
(20, 'Built-in Wardrobes', '/uploads/images/categories/category-02.avif', 6, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Dining Tables
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(21, 'Round Dining Tables', '/uploads/images/categories/category-03.jpg', 9, NOW(), NOW(), 'system', 'system'),
(22, 'Rectangular Dining Tables', '/uploads/images/categories/category-03.jpg', 9, NOW(), NOW(), 'system', 'system'),
(23, 'Square Dining Tables', '/uploads/images/categories/category-03.jpg', 9, NOW(), NOW(), 'system', 'system'),
(24, 'Extendable Dining Tables', '/uploads/images/categories/category-03.jpg', 9, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Dining Chairs
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(25, 'Wooden Dining Chairs', '/uploads/images/categories/category-03.jpg', 10, NOW(), NOW(), 'system', 'system'),
(26, 'Upholstered Dining Chairs', '/uploads/images/categories/category-03.jpg', 10, NOW(), NOW(), 'system', 'system'),
(27, 'Metal Dining Chairs', '/uploads/images/categories/category-03.jpg', 10, NOW(), NOW(), 'system', 'system'),
(28, 'Dining Bench Seats', '/uploads/images/categories/category-03.jpg', 10, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Desks
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(29, 'Writing Desks', '/uploads/images/categories/category-04.avif', 13, NOW(), NOW(), 'system', 'system'),
(30, 'Computer Desks', '/uploads/images/categories/category-04.avif', 13, NOW(), NOW(), 'system', 'system'),
(31, 'Standing Desks', '/uploads/images/categories/category-04.avif', 13, NOW(), NOW(), 'system', 'system'),
(32, 'Corner Desks', '/uploads/images/categories/category-04.avif', 13, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Office Chairs
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(33, 'Ergonomic Office Chairs', '/uploads/images/categories/category-04.avif', 14, NOW(), NOW(), 'system', 'system'),
(34, 'Executive Chairs', '/uploads/images/categories/category-04.avif', 14, NOW(), NOW(), 'system', 'system'),
(35, 'Task Chairs', '/uploads/images/categories/category-04.avif', 14, NOW(), NOW(), 'system', 'system'),
(36, 'Gaming Chairs', '/uploads/images/categories/category-04.avif', 14, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Shelving Units
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(37, 'Wall Shelves', '/uploads/images/categories/category-05.jpg', 17, NOW(), NOW(), 'system', 'system'),
(38, 'Freestanding Shelves', '/uploads/images/categories/category-05.jpg', 17, NOW(), NOW(), 'system', 'system'),
(39, 'Corner Shelves', '/uploads/images/categories/category-05.jpg', 17, NOW(), NOW(), 'system', 'system'),
(40, 'Ladder Shelves', '/uploads/images/categories/category-05.jpg', 17, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();

-- Insert Categories for Storage Cabinets
INSERT INTO categories (id, name, image_url, sub_category_id, created_at, updated_at, created_by, updated_by) VALUES
(41, 'Display Cabinets', '/uploads/images/categories/category-05.jpg', 18, NOW(), NOW(), 'system', 'system'),
(42, 'Storage Cabinets', '/uploads/images/categories/category-05.jpg', 18, NOW(), NOW(), 'system', 'system'),
(43, 'Sideboards', '/uploads/images/categories/category-05.jpg', 18, NOW(), NOW(), 'system', 'system'),
(44, 'Chests of Drawers', '/uploads/images/categories/category-05.jpg', 18, NOW(), NOW(), 'system', 'system')
ON DUPLICATE KEY UPDATE name=VALUES(name), image_url=VALUES(image_url), updated_at=NOW();
