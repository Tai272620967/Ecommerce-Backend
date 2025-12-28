-- Initialize Products Data for Muji Furniture Store
-- This script creates sample product data for various furniture categories

-- Insert Products for Sofas (Category ID: 1-4)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Minimalist 2-Seater Sofa', 899.00, 1199.00, 'JPY', 'Clean and simple 2-seater sofa with natural wood legs. Perfect for small living spaces. Made from sustainable materials with removable covers for easy cleaning.', '/uploads/images/products/F-1.avif', 15, 1, NOW(), NOW(), 'system', 'system'),
('Comfort 2-Seater Sofa', 799.00, 999.00, 'JPY', 'Cozy 2-seater sofa with soft cushions and ergonomic design. Ideal for relaxation and reading.', '/uploads/images/products/F-2.avif', 12, 1, NOW(), NOW(), 'system', 'system'),
('Modern 3-Seater Sofa', 1299.00, 1699.00, 'JPY', 'Spacious 3-seater sofa with deep seating and supportive backrest. Perfect for family gatherings.', '/uploads/images/products/F-3.avif', 10, 2, NOW(), NOW(), 'system', 'system'),
('Luxury 3-Seater Sofa', 1499.00, 1999.00, 'JPY', 'Premium 3-seater sofa with high-quality fabric and elegant design. Features adjustable headrests for ultimate comfort.', '/uploads/images/products/F-4.webp', 8, 2, NOW(), NOW(), 'system', 'system'),
('Corner Sofa Set', 1999.00, 2499.00, 'JPY', 'Large corner sofa perfect for spacious living rooms. Includes matching ottoman and cushions.', '/uploads/images/products/F-5.avif', 6, 3, NOW(), NOW(), 'system', 'system'),
('Convertible Sofa Bed', 1199.00, 1499.00, 'JPY', 'Versatile sofa that converts into a comfortable bed. Perfect for guest rooms or small apartments.', '/uploads/images/products/F-6.avif', 9, 4, NOW(), NOW(), 'system', 'system');

-- Insert Products for Coffee Tables (Category ID: 5-8)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Oak Wood Coffee Table', 299.00, 399.00, 'JPY', 'Solid oak coffee table with clean lines. Features a spacious top and lower shelf for storage.', '/uploads/images/products/F-7.avif', 20, 5, NOW(), NOW(), 'system', 'system'),
('Glass Top Coffee Table', 249.00, 349.00, 'JPY', 'Modern glass coffee table with metal frame. Easy to clean and perfect for contemporary interiors.', '/uploads/images/products/F-8.avif', 18, 6, NOW(), NOW(), 'system', 'system'),
('Metal Frame Coffee Table', 199.00, 299.00, 'JPY', 'Lightweight metal coffee table with wooden top. Minimalist design that fits any decor style.', '/uploads/images/products/F-1.avif', 22, 7, NOW(), NOW(), 'system', 'system'),
('Nested Coffee Table Set', 349.00, 449.00, 'JPY', 'Set of three nested coffee tables in different sizes. Versatile and space-saving design.', '/uploads/images/products/F-2.avif', 15, 8, NOW(), NOW(), 'system', 'system');

-- Insert Products for TV Stands (Category ID: 9-12)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Low Profile TV Stand', 399.00, 499.00, 'JPY', 'Low TV stand with cable management system. Accommodates TVs up to 65 inches.', '/uploads/images/products/F-3.avif', 14, 9, NOW(), NOW(), 'system', 'system'),
('Wall-Mounted TV Stand', 499.00, 599.00, 'JPY', 'Sleek wall-mounted TV stand with floating design. Includes storage compartments for media devices.', '/uploads/images/products/F-4.webp', 11, 10, NOW(), NOW(), 'system', 'system'),
('TV Cabinet with Doors', 599.00, 799.00, 'JPY', 'TV cabinet with sliding doors to hide media equipment. Features adjustable shelves and cable management.', '/uploads/images/products/F-5.avif', 9, 11, NOW(), NOW(), 'system', 'system'),
('Media Console', 449.00, 549.00, 'JPY', 'Modern media console with open and closed storage. Perfect for organizing entertainment systems.', '/uploads/images/products/F-6.avif', 13, 12, NOW(), NOW(), 'system', 'system');

-- Insert Products for Beds (Category ID: 13-16)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Single Bed Frame', 499.00, 699.00, 'JPY', 'Simple single bed frame made from solid wood. Low profile design with slatted base for mattress support.', '/uploads/images/products/sofa.avif', 16, 13, NOW(), NOW(), 'system', 'system'),
('Double Bed Frame', 699.00, 899.00, 'JPY', 'Sturdy double bed frame with minimalist design. Includes headboard and under-bed storage options.', '/uploads/images/products/hannyu.avif', 12, 14, NOW(), NOW(), 'system', 'system'),
('Queen Size Bed Frame', 899.00, 1199.00, 'JPY', 'Elegant queen size bed frame with upholstered headboard. Features built-in storage drawers.', '/uploads/images/products/F-1.avif', 10, 15, NOW(), NOW(), 'system', 'system'),
('King Size Bed Frame', 1199.00, 1499.00, 'JPY', 'Luxurious king size bed frame with premium materials. Spacious design for maximum comfort.', '/uploads/images/products/F-2.avif', 7, 16, NOW(), NOW(), 'system', 'system');

-- Insert Products for Dining Tables (Category ID: 21-24)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Round Oak Dining Table', 599.00, 799.00, 'JPY', 'Beautiful round dining table seating 4-6 people. Made from solid oak with natural finish.', '/uploads/images/products/F-3.avif', 11, 21, NOW(), NOW(), 'system', 'system'),
('Rectangular Dining Table', 699.00, 899.00, 'JPY', 'Classic rectangular dining table seating 6-8 people. Features extendable leaves for larger gatherings.', '/uploads/images/products/F-4.webp', 9, 22, NOW(), NOW(), 'system', 'system'),
('Square Dining Table', 549.00, 749.00, 'JPY', 'Modern square dining table perfect for small spaces. Seats 4 comfortably with clean geometric design.', '/uploads/images/products/F-5.avif', 13, 23, NOW(), NOW(), 'system', 'system'),
('Extendable Dining Table', 899.00, 1199.00, 'JPY', 'Versatile extendable dining table that seats 6-10 people. Perfect for hosting dinner parties.', '/uploads/images/products/F-6.avif', 8, 24, NOW(), NOW(), 'system', 'system');

-- Insert Products for Dining Chairs (Category ID: 25-28)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Wooden Dining Chair', 89.00, 129.00, 'JPY', 'Classic wooden dining chair with ergonomic design. Stackable for easy storage.', '/uploads/images/products/F-7.avif', 30, 25, NOW(), NOW(), 'system', 'system'),
('Upholstered Dining Chair', 149.00, 199.00, 'JPY', 'Comfortable upholstered dining chair with padded seat and backrest. Available in multiple colors.', '/uploads/images/products/F-8.avif', 25, 26, NOW(), NOW(), 'system', 'system'),
('Metal Dining Chair', 79.00, 119.00, 'JPY', 'Lightweight metal dining chair with modern design. Easy to clean and maintain.', '/uploads/images/products/F-1.avif', 35, 27, NOW(), NOW(), 'system', 'system'),
('Dining Bench', 199.00, 299.00, 'JPY', 'Long dining bench seating 2-3 people. Perfect for maximizing space at dining tables.', '/uploads/images/products/F-2.avif', 18, 28, NOW(), NOW(), 'system', 'system');

-- Insert Products for Desks (Category ID: 29-32)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Writing Desk', 399.00, 549.00, 'JPY', 'Compact writing desk with drawer storage. Perfect for home office or study area.', '/uploads/images/products/F-3.avif', 17, 29, NOW(), NOW(), 'system', 'system'),
('Computer Desk', 499.00, 699.00, 'JPY', 'Spacious computer desk with cable management. Features keyboard tray and monitor shelf.', '/uploads/images/products/F-4.webp', 14, 30, NOW(), NOW(), 'system', 'system'),
('Standing Desk', 799.00, 999.00, 'JPY', 'Adjustable height standing desk for ergonomic work. Electric lift mechanism with memory presets.', '/uploads/images/products/F-5.avif', 10, 31, NOW(), NOW(), 'system', 'system'),
('Corner Desk', 599.00, 799.00, 'JPY', 'Space-efficient corner desk maximizing room layout. Includes storage shelves and drawers.', '/uploads/images/products/F-6.avif', 12, 32, NOW(), NOW(), 'system', 'system');

-- Insert Products for Office Chairs (Category ID: 33-36)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Ergonomic Office Chair', 399.00, 549.00, 'JPY', 'Comfortable ergonomic office chair with lumbar support. Adjustable height and armrests.', '/uploads/images/products/F-7.avif', 20, 33, NOW(), NOW(), 'system', 'system'),
('Executive Chair', 599.00, 799.00, 'JPY', 'Premium executive chair with leather upholstery. High back design with multiple adjustment options.', '/uploads/images/products/F-8.avif', 8, 34, NOW(), NOW(), 'system', 'system'),
('Task Chair', 299.00, 399.00, 'JPY', 'Affordable task chair for daily office use. Comfortable padding and swivel base.', '/uploads/images/products/F-1.avif', 25, 35, NOW(), NOW(), 'system', 'system'),
('Gaming Chair', 499.00, 699.00, 'JPY', 'Ergonomic gaming chair with racing-style design. Features adjustable armrests and headrest pillow.', '/uploads/images/products/F-2.avif', 15, 36, NOW(), NOW(), 'system', 'system');

-- Insert Products for Shelving Units (Category ID: 37-40)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Wall Shelf Set', 149.00, 199.00, 'JPY', 'Set of three floating wall shelves. Perfect for displaying books, plants, and decor items.', '/uploads/images/products/F-3.avif', 22, 37, NOW(), NOW(), 'system', 'system'),
('Freestanding Bookshelf', 299.00, 399.00, 'JPY', 'Tall freestanding bookshelf with 5 shelves. Adjustable shelf heights for flexibility.', '/uploads/images/products/F-4.webp', 16, 38, NOW(), NOW(), 'system', 'system'),
('Corner Shelf Unit', 199.00, 299.00, 'JPY', 'Space-saving corner shelf unit. Maximizes unused corner space with multiple storage levels.', '/uploads/images/products/F-5.avif', 19, 39, NOW(), NOW(), 'system', 'system'),
('Ladder Shelf', 249.00, 349.00, 'JPY', 'Stylish ladder-style shelf with leaning design. Perfect for modern minimalist interiors.', '/uploads/images/products/F-6.avif', 14, 40, NOW(), NOW(), 'system', 'system');

-- Insert Products for Storage Cabinets (Category ID: 41-44)
INSERT INTO products (name, min_price, max_price, currency, description, image_url, stock_quantity, category_id, created_at, updated_at, created_by, updated_by) VALUES
('Display Cabinet', 599.00, 799.00, 'JPY', 'Glass-front display cabinet for showcasing collectibles. Features adjustable shelves and lighting.', '/uploads/images/products/F-7.avif', 9, 41, NOW(), NOW(), 'system', 'system'),
('Storage Cabinet', 449.00, 599.00, 'JPY', 'Versatile storage cabinet with doors and drawers. Perfect for organizing various items.', '/uploads/images/products/F-8.avif', 13, 42, NOW(), NOW(), 'system', 'system'),
('Sideboard', 699.00, 899.00, 'JPY', 'Elegant sideboard with multiple storage compartments. Ideal for dining room storage and serving.', '/uploads/images/products/F-1.avif', 11, 43, NOW(), NOW(), 'system', 'system'),
('Chest of Drawers', 549.00, 749.00, 'JPY', 'Classic chest of drawers with 5 drawers. Spacious storage for bedroom or hallway.', '/uploads/images/products/F-2.avif', 10, 44, NOW(), NOW(), 'system', 'system');

