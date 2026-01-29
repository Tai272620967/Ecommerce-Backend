# KỊCH BẢN TRÌNH BÀY LUẬN VĂN TỐT NGHIỆP
## HỆ THỐNG THƯƠNG MẠI ĐIỆN TỬ MUJI

---

## PHẦN 1: MỞ ĐẦU (2-3 phút)

### 1.1. Lời chào và giới thiệu
**Nội dung:**
- Kính chào Hội đồng chấm thi, các thầy cô và các bạn.
- Tên sinh viên: [Tên của bạn]
- Mã sinh viên: [Mã sinh viên]
- Lớp: [Lớp]
- Đề tài: "Xây dựng hệ thống thương mại điện tử Muji"

**Slide 1: Tiêu đề**
- Tên đề tài
- Thông tin sinh viên
- Ngày bảo vệ

### 1.2. Lý do chọn đề tài
**Nội dung trình bày:**
- Thương mại điện tử đang phát triển mạnh mẽ tại Việt Nam
- Nhu cầu mua sắm trực tuyến ngày càng tăng cao
- Muốn áp dụng kiến thức đã học vào thực tế
- Xây dựng một hệ thống hoàn chỉnh với các công nghệ hiện đại

**Slide 2: Lý do chọn đề tài**

---

## PHẦN 2: TỔNG QUAN DỰ ÁN (3-4 phút)

### 2.1. Giới thiệu dự án
**Nội dung trình bày:**
"Hệ thống thương mại điện tử Muji là một nền tảng bán hàng trực tuyến được xây dựng với kiến trúc hiện đại, tách biệt giữa Frontend và Backend. Hệ thống cho phép người dùng mua sắm các sản phẩm nội thất và đồ dùng gia đình trực tuyến, đồng thời cung cấp công cụ quản lý toàn diện cho quản trị viên."

**Slide 3: Tổng quan dự án**
- Mô tả ngắn gọn về hệ thống
- Đối tượng sử dụng: Người dùng và Quản trị viên

### 2.2. Mục tiêu dự án
**Nội dung trình bày:**
- Xây dựng hệ thống thương mại điện tử hoàn chỉnh với đầy đủ các chức năng cơ bản
- Áp dụng các công nghệ và framework hiện đại trong phát triển web
- Tạo ra trải nghiệm người dùng tốt với giao diện thân thiện, dễ sử dụng
- Đảm bảo tính bảo mật và hiệu suất của hệ thống
- Xây dựng hệ thống quản trị mạnh mẽ cho việc quản lý sản phẩm, đơn hàng và người dùng

**Slide 4: Mục tiêu dự án**
- Liệt kê các mục tiêu chính

### 2.3. Phạm vi dự án
**Nội dung trình bày:**
"Dự án bao gồm hai phần chính:
- Backend API: Xây dựng RESTful API với Spring Boot
- Frontend Web Application: Xây dựng giao diện người dùng với Next.js"

**Slide 5: Phạm vi dự án**
- Sơ đồ kiến trúc tổng thể

---

## PHẦN 3: CÔNG NGHỆ SỬ DỤNG (4-5 phút)

### 3.1. Backend Stack
**Nội dung trình bày:**
"Phần Backend được xây dựng với các công nghệ sau:
- Java 17: Ngôn ngữ lập trình chính
- Spring Boot 3.2.4: Framework chính cho việc xây dựng ứng dụng
- Spring Security: Xử lý xác thực và phân quyền với JWT
- Spring Data JPA: Truy cập và quản lý dữ liệu
- MySQL: Hệ quản trị cơ sở dữ liệu quan hệ
- Lombok: Giảm boilerplate code
- Gradle: Build tool"

**Slide 6: Backend Stack**
- Bảng công nghệ Backend với phiên bản

### 3.2. Frontend Stack
**Nội dung trình bày:**
"Phần Frontend được xây dựng với:
- Next.js 14.2.15: React framework với SSR và SSG
- React 18: UI library
- TypeScript 5: Type-safe JavaScript
- Redux Toolkit: State management
- Axios: HTTP client cho API calls
- Bootstrap 5.3.3 và Ant Design 5.21.6: UI component libraries
- SCSS: CSS preprocessor
- React Hook Form và Yup: Form handling và validation"

**Slide 7: Frontend Stack**
- Bảng công nghệ Frontend với phiên bản

### 3.3. Lý do chọn công nghệ
**Nội dung trình bày:**
- Spring Boot: Framework phổ biến, mạnh mẽ, có cộng đồng lớn
- Next.js: Framework React hiện đại, hỗ trợ SSR tốt cho SEO
- TypeScript: Tăng tính an toàn và dễ bảo trì code
- MySQL: Database quan hệ phổ biến, dễ sử dụng

**Slide 8: Lý do chọn công nghệ**

---

## PHẦN 4: KIẾN TRÚC HỆ THỐNG (5-6 phút)

### 4.1. Kiến trúc tổng thể
**Nội dung trình bày:**
"Hệ thống sử dụng kiến trúc Client-Server với sự tách biệt rõ ràng giữa Frontend và Backend:
- Frontend (Next.js) chạy trên port 3000
- Backend (Spring Boot) chạy trên port 8080
- Database MySQL lưu trữ dữ liệu
- Giao tiếp giữa Frontend và Backend thông qua RESTful API"

**Slide 9: Kiến trúc tổng thể**
- Sơ đồ kiến trúc với các thành phần

### 4.2. Kiến trúc Backend
**Nội dung trình bày:**
"Backend sử dụng kiến trúc 3 lớp:
- Controller Layer: Xử lý HTTP requests và responses
- Service Layer: Chứa business logic
- Repository Layer: Truy cập cơ sở dữ liệu

Cấu trúc thư mục:
- config/: Cấu hình Security, JWT
- controller/: REST Controllers
- domain/: Entity models và DTOs
- repository/: Data access layer
- service/: Business logic layer
- util/: Utilities và helpers"

**Slide 10: Kiến trúc Backend**
- Sơ đồ kiến trúc 3 lớp
- Cấu trúc thư mục

### 4.3. Kiến trúc Frontend
**Nội dung trình bày:**
"Frontend sử dụng kiến trúc component-based với Next.js App Router:
- Pages: Các trang của ứng dụng
- Components: Các component tái sử dụng
- Redux Store: Quản lý state toàn cục
- Utils: Các hàm tiện ích
- Types: TypeScript type definitions"

**Slide 11: Kiến trúc Frontend**
- Sơ đồ cấu trúc thư mục
- Luồng dữ liệu

### 4.4. Database Design
**Nội dung trình bày:**
"Hệ thống sử dụng MySQL với các bảng chính:
- User: Thông tin người dùng
- Product: Thông tin sản phẩm
- Category, SubCategory, MainCategory: Cấu trúc danh mục phân cấp
- Cart, CartItem: Quản lý giỏ hàng
- Order, OrderItem: Quản lý đơn hàng
- Wishlist: Danh sách yêu thích
- Review: Đánh giá sản phẩm

Hệ thống có cấu trúc quan hệ rõ ràng:
- Cấu trúc danh mục phân cấp 3 cấp: MainCategory → SubCategory → Category → Product
- Mỗi người dùng có thể có nhiều giỏ hàng, đơn hàng, wishlist và đánh giá
- Mỗi giỏ hàng và đơn hàng chứa nhiều sản phẩm thông qua CartItem và OrderItem
- Tất cả foreign key đều có ràng buộc NOT NULL để đảm bảo tính toàn vẹn dữ liệu"

**Slide 12: Database Design**
- Sơ đồ ERD (Entity Relationship Diagram)
- Mô tả các quan hệ giữa các bảng

#### 4.4.1. Mô tả chi tiết các quan hệ giữa các bảng

**1. Quan hệ phân cấp danh mục (Category Hierarchy)**

**MainCategory → SubCategory → Category → Product**

- **MainCategory (1) ──< (N) SubCategory**: 
  - Một danh mục chính có nhiều danh mục phụ
  - Quan hệ One-to-Many
  - Foreign key: `subcategories.main_category_id` → `maincategories.id`
  - Ví dụ: "Nội thất" (MainCategory) có "Bàn ghế", "Tủ kệ" (SubCategory)

- **SubCategory (1) ──< (N) Category**: 
  - Một danh mục phụ có nhiều danh mục con
  - Quan hệ One-to-Many
  - Foreign key: `categories.sub_category_id` → `subcategories.id`
  - Ví dụ: "Bàn ghế" (SubCategory) có "Bàn ăn", "Ghế sofa" (Category)

- **Category (1) ──< (N) Product**: 
  - Một danh mục có nhiều sản phẩm
  - Quan hệ One-to-Many
  - Foreign key: `products.category_id` → `categories.id`
  - Mỗi sản phẩm phải thuộc một danh mục (NOT NULL)
  - Ví dụ: "Bàn ăn" (Category) có nhiều sản phẩm bàn ăn khác nhau

**2. Quan hệ giữa User và các bảng khác**

- **User (1) ──< (N) Cart**: 
  - Một người dùng có thể có nhiều giỏ hàng (theo thời gian)
  - Quan hệ logic qua `carts.user_id` → `users.id`
  - Mỗi user thường có một giỏ hàng "active" tại một thời điểm
  - Khi đặt hàng, giỏ hàng chuyển sang trạng thái "completed"

- **User (1) ──< (N) Order**: 
  - Một người dùng có thể có nhiều đơn hàng
  - Quan hệ logic qua `orders.user_id` → `users.id`
  - Mỗi đơn hàng thuộc về một người dùng cụ thể
  - Lưu thông tin giao hàng trong bảng Order (không phụ thuộc vào thông tin User)

- **User (1) ──< (N) Wishlist**: 
  - Một người dùng có thể có nhiều sản phẩm trong danh sách yêu thích
  - Quan hệ logic qua `wishlists.user_id` → `users.id`
  - Mỗi bản ghi Wishlist là một cặp (user_id, product_id)

- **User (1) ──< (N) Review**: 
  - Một người dùng có thể đánh giá nhiều sản phẩm
  - Quan hệ Many-to-One qua `reviews.user_id` → `users.id`
  - Mỗi đánh giá thuộc về một người dùng cụ thể

**3. Quan hệ giỏ hàng (Shopping Cart)**

- **Cart (1) ──< (N) CartItem**: 
  - Một giỏ hàng có nhiều sản phẩm (CartItem)
  - Quan hệ One-to-Many
  - Foreign key: `cartitems.cart_id` → `carts.id`
  - Mỗi CartItem đại diện cho một sản phẩm trong giỏ hàng với số lượng cụ thể

- **CartItem (N) ──> (1) Product**: 
  - Một CartItem tham chiếu đến một sản phẩm cụ thể
  - Quan hệ Many-to-One
  - Foreign key: `cartitems.product_id` → `products.id`
  - Lưu giá tại thời điểm thêm vào giỏ (price) để tránh thay đổi giá sau này

**4. Quan hệ đơn hàng (Order)**

- **Order (1) ──< (N) OrderItem**: 
  - Một đơn hàng có nhiều sản phẩm (OrderItem)
  - Quan hệ One-to-Many
  - Foreign key: `orderitems.order_id` → `orders.id`
  - Mỗi OrderItem đại diện cho một sản phẩm trong đơn hàng với số lượng và giá cụ thể

- **OrderItem (N) ──> (1) Product**: 
  - Một OrderItem tham chiếu đến một sản phẩm cụ thể
  - Quan hệ Many-to-One
  - Foreign key: `orderitems.product_id` → `products.id`
  - Lưu giá tại thời điểm đặt hàng (price) và tính subtotal = price × quantity

**5. Quan hệ danh sách yêu thích (Wishlist)**

- **Wishlist (N) ──> (1) Product**: 
  - Một bản ghi Wishlist tham chiếu đến một sản phẩm
  - Quan hệ Many-to-One
  - Foreign key: `wishlists.product_id` → `products.id`
  - Một sản phẩm có thể được nhiều người dùng yêu thích
  - Một người dùng có thể yêu thích nhiều sản phẩm

**6. Quan hệ đánh giá sản phẩm (Review)**

- **Review (N) ──> (1) Product**: 
  - Một đánh giá thuộc về một sản phẩm
  - Quan hệ Many-to-One
  - Foreign key: `reviews.product_id` → `products.id`
  - Một sản phẩm có thể có nhiều đánh giá từ nhiều người dùng

- **Review (N) ──> (1) User**: 
  - Một đánh giá được viết bởi một người dùng
  - Quan hệ Many-to-One
  - Foreign key: `reviews.user_id` → `users.id`
  - Một người dùng có thể đánh giá nhiều sản phẩm

**7. Tóm tắt sơ đồ quan hệ tổng thể**

```
User (1) ──< (N) Cart ──< (N) CartItem >── (1) Product
User (1) ──< (N) Order ──< (N) OrderItem >── (1) Product
User (1) ──< (N) Wishlist >── (1) Product
User (1) ──< (N) Review >── (1) Product

MainCategory (1) ──< (N) SubCategory (1) ──< (N) Category (1) ──< (N) Product
```

**8. Các ràng buộc và quy tắc nghiệp vụ**

- **Cascade Operations**: 
  - Khi xóa Category, cần xử lý các Product thuộc Category đó (không cho phép xóa nếu có sản phẩm)
  - Khi xóa Product, cần kiểm tra xem có trong đơn hàng không (không cho phép xóa nếu đã có trong OrderItem)

- **Foreign Key Constraints**: 
  - Tất cả foreign key đều có `nullable = false` để đảm bảo tính toàn vẹn dữ liệu
  - Sử dụng `ON DELETE RESTRICT` để ngăn xóa dữ liệu cha khi còn dữ liệu con

- **Indexes**: 
  - Index trên `users.email` để tìm kiếm nhanh khi đăng nhập
  - Index trên `products.category_id` để filter sản phẩm theo danh mục
  - Index trên `orders.user_id` để lấy đơn hàng của user
  - Index trên `carts.user_id` và `carts.status` để tìm giỏ hàng active

**9. Các trường metadata chung**

Hầu hết các bảng đều có các trường:
- `createdAt`: Thời gian tạo bản ghi
- `updatedAt`: Thời gian cập nhật cuối cùng
- `createdBy`: Người tạo (email của user)
- `updatedBy`: Người cập nhật cuối cùng

Các trường này được tự động cập nhật bằng `@PrePersist` và `@PreUpdate` annotations.

---

## PHẦN 5: BẢO MẬT VÀ XÁC THỰC (4-5 phút)

### 5.1. JWT Authentication
**Nội dung trình bày:**
"Hệ thống sử dụng JWT (JSON Web Token) để xác thực:
- Khi người dùng đăng nhập thành công, hệ thống tạo Access Token và Refresh Token
- Access Token có thời hạn ngắn, được gửi kèm trong header mỗi request
- Refresh Token có thời hạn dài hơn, được lưu trong httpOnly cookie để bảo mật
- Khi Access Token hết hạn, hệ thống tự động làm mới bằng Refresh Token"

**Slide 13: JWT Authentication**
- Sơ đồ luồng xác thực
- Cấu trúc JWT token

### 5.2. Password Encryption
**Nội dung trình bày:**
"Mật khẩu được mã hóa bằng BCrypt với strength = 10:
- Mỗi lần hash tạo ra salt ngẫu nhiên khác nhau
- Mật khẩu không bao giờ được lưu dạng plain text
- Không thể reverse hash để lấy lại mật khẩu gốc"

**Slide 14: Password Encryption**
- Quy trình mã hóa và xác thực

### 5.3. Phân quyền
**Nội dung trình bày:**
"Hệ thống có 2 loại người dùng:
- USER: Người dùng thông thường, có quyền mua sắm, quản lý đơn hàng
- ADMIN: Quản trị viên, có quyền quản lý toàn bộ hệ thống

Phân quyền được thực hiện bằng Spring Security với @PreAuthorize annotation"

**Slide 15: Phân quyền**
- Bảng phân quyền theo role

---

## PHẦN 6: CHỨC NĂNG HỆ THỐNG - DEMO (10-12 phút)

### 6.1. Chức năng người dùng (User)

#### 6.1.1. Đăng ký và Đăng nhập
**Nội dung trình bày:**
"Bây giờ em sẽ demo các chức năng chính của hệ thống. Đầu tiên là chức năng đăng ký và đăng nhập."

**Demo:**
1. Mở trình duyệt, truy cập trang web
2. Click vào "Đăng ký"
3. Điền thông tin: email, mật khẩu, họ tên
4. Submit form
5. Quay lại trang đăng nhập
6. Đăng nhập với tài khoản vừa tạo
7. Giải thích: Hệ thống tạo JWT token và lưu vào Redux store

**Slide 16: Đăng ký và Đăng nhập**
- Form đăng ký
- Form đăng nhập
- Luồng xác thực

#### 6.1.2. Duyệt và Tìm kiếm Sản phẩm
**Nội dung trình bày:**
"Người dùng có thể duyệt sản phẩm, tìm kiếm và lọc theo danh mục."

**Demo:**
1. Xem trang chủ với danh sách sản phẩm
2. Click vào một danh mục (ví dụ: Nội thất)
3. Xem danh sách sản phẩm theo danh mục
4. Sử dụng thanh tìm kiếm để tìm sản phẩm
5. Click vào một sản phẩm để xem chi tiết
6. Giải thích: API trả về dữ liệu với phân trang và filter

**Slide 17: Duyệt và Tìm kiếm Sản phẩm**
- Giao diện trang chủ
- Trang chi tiết sản phẩm

#### 6.1.3. Giỏ hàng và Đặt hàng
**Nội dung trình bày:**
"Người dùng có thể thêm sản phẩm vào giỏ hàng và đặt hàng."

**Demo:**
1. Thêm sản phẩm vào giỏ hàng
2. Xem giỏ hàng với các sản phẩm đã thêm
3. Cập nhật số lượng sản phẩm
4. Xóa sản phẩm khỏi giỏ hàng
5. Click "Thanh toán"
6. Điền thông tin giao hàng
7. Chọn phương thức thanh toán
8. Xác nhận đơn hàng
9. Xem trang xác nhận đơn hàng
10. Giải thích: Hệ thống tạo Order từ Cart, cập nhật tồn kho

**Slide 18: Giỏ hàng và Đặt hàng**
- Giao diện giỏ hàng
- Form thanh toán
- Trang xác nhận đơn hàng

#### 6.1.4. Quản lý Tài khoản và Đơn hàng
**Nội dung trình bày:**
"Người dùng có thể quản lý thông tin cá nhân và xem lịch sử đơn hàng."

**Demo:**
1. Click vào "Tài khoản" hoặc "Profile"
2. Xem thông tin cá nhân
3. Cập nhật thông tin (tên, địa chỉ, số điện thoại)
4. Upload avatar mới
5. Xem "Lịch sử đơn hàng"
6. Click vào một đơn hàng để xem chi tiết
7. Giải thích: API lấy đơn hàng của user hiện tại

**Slide 19: Quản lý Tài khoản và Đơn hàng**
- Giao diện profile
- Lịch sử đơn hàng

#### 6.1.5. Danh sách Yêu thích (Wishlist)
**Nội dung trình bày:**
"Người dùng có thể lưu sản phẩm yêu thích vào wishlist."

**Demo:**
1. Click vào icon "Yêu thích" trên sản phẩm
2. Xem trang wishlist với các sản phẩm đã lưu
3. Xóa sản phẩm khỏi wishlist
4. Giải thích: API quản lý wishlist của user

**Slide 20: Danh sách Yêu thích**

#### 6.1.6. Chatbot AI Hỗ trợ Khách hàng
**Nội dung trình bày:**
"Hệ thống có tích hợp chatbot AI sử dụng OpenAI GPT API để hỗ trợ khách hàng."

**Demo:**
1. Click vào icon chatbot ở góc dưới bên phải
2. Mở cửa sổ chat
3. Gửi câu hỏi về sản phẩm (ví dụ: "Bạn có bán sofa không?")
4. Chatbot trả lời và có thể đề xuất sản phẩm
5. Hỏi về thông tin đơn hàng
6. Giải thích: Backend tích hợp OpenAI GPT API, tự động tìm kiếm sản phẩm

**Slide 21: Chatbot AI**
- Giao diện chatbot
- Luồng xử lý tin nhắn

### 6.2. Chức năng Quản trị viên (Admin)

#### 6.2.1. Dashboard
**Nội dung trình bày:**
"Bây giờ em sẽ demo phần quản trị. Đầu tiên là Dashboard với các thống kê tổng quan."

**Demo:**
1. Đăng nhập với tài khoản Admin
2. Truy cập Dashboard
3. Xem các thống kê: Tổng số người dùng, sản phẩm, đơn hàng
4. Xem biểu đồ doanh thu theo thời gian
5. Xem biểu đồ đơn hàng theo trạng thái
6. Xem thống kê sản phẩm bán chạy
7. Giải thích: API tổng hợp dữ liệu từ database

**Slide 22: Dashboard Admin**
- Giao diện dashboard
- Các biểu đồ thống kê

#### 6.2.2. Quản lý Người dùng
**Nội dung trình bày:**
"Admin có thể quản lý người dùng trong hệ thống."

**Demo:**
1. Click vào "Quản lý Người dùng"
2. Xem danh sách người dùng với phân trang
3. Tìm kiếm người dùng theo email hoặc tên
4. Click "Tạo mới" để thêm người dùng
5. Click "Sửa" để cập nhật thông tin người dùng
6. Click "Xóa" để xóa người dùng (có xác nhận)
7. Giải thích: API CRUD với phân quyền ADMIN

**Slide 23: Quản lý Người dùng**
- Giao diện quản lý người dùng
- Form tạo/sửa người dùng

#### 6.2.3. Quản lý Sản phẩm
**Nội dung trình bày:**
"Admin có thể quản lý sản phẩm: tạo mới, cập nhật, xóa, upload hình ảnh."

**Demo:**
1. Click vào "Quản lý Sản phẩm"
2. Xem danh sách sản phẩm với phân trang và filter
3. Click "Tạo mới"
4. Điền thông tin sản phẩm: tên, mô tả, giá, số lượng tồn kho
5. Chọn danh mục
6. Upload hình ảnh sản phẩm
7. Submit form
8. Click "Sửa" để cập nhật sản phẩm
9. Upload hình ảnh mới (hệ thống tự động xóa hình cũ)
10. Click "Xóa" để xóa sản phẩm
11. Giải thích: API xử lý multipart/form-data, lưu file vào server

**Slide 24: Quản lý Sản phẩm**
- Giao diện quản lý sản phẩm
- Form tạo/sửa sản phẩm

#### 6.2.4. Quản lý Danh mục
**Nội dung trình bày:**
"Hệ thống có cấu trúc danh mục phân cấp: Main Category → Sub Category → Category."

**Demo:**
1. Click vào "Quản lý Danh mục"
2. Xem cấu trúc danh mục phân cấp
3. Tạo Main Category mới
4. Tạo Sub Category thuộc Main Category
5. Tạo Category thuộc Sub Category
6. Upload hình ảnh cho danh mục
7. Giải thích: Cấu trúc phân cấp trong database

**Slide 25: Quản lý Danh mục**
- Cấu trúc danh mục phân cấp
- Giao diện quản lý danh mục

#### 6.2.5. Quản lý Đơn hàng
**Nội dung trình bày:**
"Admin có thể xem tất cả đơn hàng và cập nhật trạng thái."

**Demo:**
1. Click vào "Quản lý Đơn hàng"
2. Xem danh sách tất cả đơn hàng với phân trang
3. Lọc đơn hàng theo trạng thái (pending, processing, shipped, delivered)
4. Click vào một đơn hàng để xem chi tiết
5. Cập nhật trạng thái đơn hàng
6. Giải thích: API lấy tất cả đơn hàng, không chỉ của user hiện tại

**Slide 26: Quản lý Đơn hàng**
- Giao diện quản lý đơn hàng
- Chi tiết đơn hàng

---

## PHẦN 7: ĐIỂM NỔI BẬT VÀ KỸ THUẬT (3-4 phút)

### 7.1. Upload và Quản lý File
**Nội dung trình bày:**
"Hệ thống hỗ trợ upload hình ảnh với các tính năng:
- Validate file type và kích thước (tối đa 50MB)
- Tạo tên file unique để tránh conflict
- Lưu file vào thư mục tương ứng (products, categories, users)
- Tự động xóa file cũ khi cập nhật"

**Slide 27: Upload và Quản lý File**

### 7.2. Pagination và Filtering
**Nội dung trình bày:**
"Hệ thống sử dụng Spring Filter để hỗ trợ pagination và filtering động:
- Phân trang với page và size
- Filter động với nhiều tiêu chí (tên, giá, danh mục)
- Sắp xếp dữ liệu
- Response format chuẩn với meta và result"

**Slide 28: Pagination và Filtering**

### 7.3. Error Handling
**Nội dung trình bày:**
"Hệ thống có Global Exception Handler để xử lý lỗi tập trung:
- Custom exceptions cho các lỗi business logic
- Trả về HTTP status code phù hợp
- Message rõ ràng, dễ hiểu
- Log lỗi để debug"

**Slide 29: Error Handling**

### 7.4. State Management
**Nội dung trình bày:**
"Frontend sử dụng Redux Toolkit để quản lý state:
- Auth state: User info và access token
- Cart state: Danh sách sản phẩm trong giỏ
- Wishlist state: Danh sách yêu thích
- Axios interceptors tự động refresh token khi hết hạn"

**Slide 30: State Management**

---

## PHẦN 8: KẾT QUẢ ĐẠT ĐƯỢC (2-3 phút)

### 8.1. Kết quả
**Nội dung trình bày:**
"Qua quá trình phát triển, em đã xây dựng thành công:
- Hệ thống thương mại điện tử hoàn chỉnh với đầy đủ chức năng cơ bản
- RESTful API với Spring Boot, có đầy đủ CRUD operations
- Giao diện người dùng hiện đại, responsive với Next.js
- Hệ thống bảo mật với JWT authentication
- Hệ thống quản trị mạnh mẽ với dashboard và thống kê
- Tích hợp chatbot AI với OpenAI GPT API"

**Slide 31: Kết quả đạt được**
- Checklist các chức năng đã hoàn thành

### 8.2. Kinh nghiệm đạt được
**Nội dung trình bày:**
"Qua dự án này, em đã học được và áp dụng:
- Xây dựng RESTful API với Spring Boot
- Xác thực và phân quyền với JWT và Spring Security
- Phát triển ứng dụng web với Next.js và React
- Quản lý state với Redux Toolkit
- Làm việc với cơ sở dữ liệu MySQL và JPA
- Upload và quản lý file
- Xây dựng giao diện responsive với Bootstrap và SCSS
- Tích hợp OpenAI GPT API cho chatbot AI"

**Slide 32: Kinh nghiệm đạt được**

---

## PHẦN 9: HẠN CHẾ VÀ HƯỚNG PHÁT TRIỂN (2-3 phút)

### 9.1. Hạn chế
**Nội dung trình bày:**
"Hệ thống hiện tại còn một số hạn chế:
- Chưa tích hợp thanh toán trực tuyến (chỉ có COD)
- Chưa có hệ thống đánh giá và nhận xét sản phẩm đầy đủ
- Chatbot chưa có khả năng truy cập trực tiếp vào database để tra cứu đơn hàng
- Chưa tối ưu SEO cho trang web
- Chưa có mobile app"

**Slide 33: Hạn chế**

### 9.2. Hướng phát triển
**Nội dung trình bày:**
"Trong tương lai, hệ thống có thể phát triển thêm:
- Tích hợp thanh toán trực tuyến (VNPay, PayPal, Stripe)
- Hoàn thiện hệ thống đánh giá và nhận xét sản phẩm
- Cải thiện chatbot với RAG (Retrieval-Augmented Generation) để truy cập database
- Tối ưu SEO cho trang web
- Tích hợp social login (Google, Facebook)
- Hệ thống thông báo real-time
- Mobile app với React Native
- Hệ thống khuyến mãi và giảm giá"

**Slide 34: Hướng phát triển**

---

## PHẦN 10: KẾT LUẬN (1-2 phút)

### 10.1. Tổng kết
**Nội dung trình bày:**
"Tóm lại, em đã xây dựng thành công hệ thống thương mại điện tử Muji với đầy đủ các chức năng cơ bản và nâng cao. Hệ thống được phát triển với các công nghệ hiện đại, đảm bảo tính bảo mật, hiệu suất và khả năng mở rộng. Qua dự án này, em đã học được nhiều kiến thức và kỹ năng quan trọng trong phát triển web."

**Slide 35: Kết luận**

### 10.2. Lời cảm ơn
**Nội dung trình bày:**
"Em xin chân thành cảm ơn Hội đồng chấm thi, các thầy cô đã dành thời gian lắng nghe và đánh giá đồ án của em. Em rất mong nhận được những ý kiến đóng góp quý báu từ các thầy cô để em có thể hoàn thiện dự án tốt hơn.

Em xin cảm ơn!"

**Slide 36: Lời cảm ơn**

---

## PHẦN 11: HỎI ĐÁP (5-10 phút)

### Các câu hỏi có thể gặp và cách trả lời:

#### Câu hỏi 1: "Tại sao em chọn Spring Boot thay vì các framework khác?"
**Trả lời:**
"Em chọn Spring Boot vì:
- Spring Boot là framework phổ biến và mạnh mẽ nhất trong hệ sinh thái Java
- Có cộng đồng lớn, tài liệu phong phú
- Hỗ trợ tốt cho việc xây dựng RESTful API
- Tích hợp sẵn Spring Security cho bảo mật
- Dễ dàng cấu hình và deploy
- Phù hợp với yêu cầu của dự án"

#### Câu hỏi 2: "Em giải thích cách JWT hoạt động trong hệ thống?"
**Trả lời:**
"JWT hoạt động như sau:
1. Khi user đăng nhập thành công, backend tạo Access Token và Refresh Token
2. Access Token chứa thông tin user (email, role, permissions) và có thời hạn ngắn
3. Frontend lưu Access Token và gửi kèm trong header Authorization mỗi request
4. Backend verify token bằng secret key, nếu hợp lệ thì cho phép truy cập
5. Khi Access Token hết hạn, frontend tự động gọi API refresh để lấy token mới
6. Refresh Token được lưu trong httpOnly cookie để bảo mật"

#### Câu hỏi 3: "Em xử lý upload file như thế nào?"
**Trả lời:**
"Em xử lý upload file như sau:
1. Frontend gửi multipart/form-data với file
2. Backend nhận MultipartFile trong Controller
3. Validate file: kiểm tra kích thước (tối đa 50MB) và định dạng
4. Tạo tên file unique bằng timestamp + tên file gốc
5. Lưu file vào thư mục tương ứng (/uploads/images/products/, categories/, users/)
6. Lưu đường dẫn file vào database
7. Khi cập nhật, nếu có file mới thì xóa file cũ trước khi lưu file mới"

#### Câu hỏi 4: "Em giải thích cách phân quyền trong hệ thống?"
**Trả lời:**
"Hệ thống có 2 role: USER và ADMIN
- USER: Có quyền mua sắm, quản lý đơn hàng của mình, quản lý profile
- ADMIN: Có quyền quản lý toàn bộ hệ thống (users, products, orders, categories)

Phân quyền được thực hiện bằng:
1. Spring Security với @PreAuthorize annotation
2. Kiểm tra role trong JWT token
3. Backend kiểm tra quyền trước khi thực hiện action
4. Frontend ẩn/hiện các chức năng dựa trên role"

#### Câu hỏi 5: "Em tích hợp chatbot AI như thế nào?"
**Trả lời:**
"Em tích hợp chatbot AI như sau:
1. Frontend gửi tin nhắn đến API /api/v1/chatbot/message
2. Backend nhận tin nhắn và gọi OpenAI GPT API (GPT-3.5-turbo)
3. Backend xử lý tin nhắn, có thể tìm kiếm sản phẩm nếu user hỏi về sản phẩm
4. GPT API trả về phản hồi tự nhiên
5. Backend trả về response cho frontend
6. Frontend hiển thị phản hồi trong chat window
7. Nếu có đề xuất sản phẩm, frontend hiển thị link đến sản phẩm"

#### Câu hỏi 6: "Em xử lý lỗi trong hệ thống như thế nào?"
**Trả lời:**
"Em xử lý lỗi bằng Global Exception Handler:
1. Tạo custom exceptions cho các lỗi business logic (IdInvalidException, etc.)
2. Sử dụng @ControllerAdvice để bắt tất cả exceptions
3. Xử lý từng loại exception và trả về HTTP status code phù hợp
4. Response format chuẩn với statusCode, message, error
5. Log lỗi để debug
6. Frontend hiển thị thông báo lỗi phù hợp cho user"

#### Câu hỏi 7: "Em tối ưu hiệu suất như thế nào?"
**Trả lời:**
"Em tối ưu hiệu suất bằng:
1. Backend: Sử dụng JPA pagination để giảm data transfer
2. Frontend: Code splitting với Next.js, lazy loading images
3. Caching: Có thể cache responses khi cần
4. Database: Index các trường thường query
5. Image optimization: Sử dụng Next.js Image component
6. Debounce cho search input để giảm số lượng API calls"

#### Câu hỏi 8: "Em test hệ thống như thế nào?"
**Trả lời:**
"Em test hệ thống bằng:
1. Manual testing: Test từng chức năng thủ công
2. Postman/Insomnia: Test API endpoints
3. Browser DevTools: Kiểm tra network requests, console errors
4. Test các trường hợp edge cases: invalid input, unauthorized access, etc.
5. Test trên nhiều trình duyệt khác nhau
6. Test responsive trên nhiều kích thước màn hình"

#### Câu hỏi 9: "Em giải thích thiết kế database và các quan hệ giữa các bảng?"
**Trả lời:**
"Database được thiết kế với các quan hệ chính như sau:

1. **Cấu trúc danh mục phân cấp 3 cấp**:
   - MainCategory (1) ──< (N) SubCategory ──< (N) Category ──< (N) Product
   - Cho phép tổ chức sản phẩm theo nhiều cấp độ, dễ quản lý và tìm kiếm

2. **Quan hệ User với các bảng khác**:
   - User (1) ──< (N) Cart: Mỗi user có thể có nhiều giỏ hàng
   - User (1) ──< (N) Order: Mỗi user có nhiều đơn hàng
   - User (1) ──< (N) Wishlist: Mỗi user có nhiều sản phẩm yêu thích
   - User (1) ──< (N) Review: Mỗi user có thể đánh giá nhiều sản phẩm

3. **Quan hệ giỏ hàng và đơn hàng**:
   - Cart (1) ──< (N) CartItem >── (1) Product: Giỏ hàng chứa nhiều sản phẩm
   - Order (1) ──< (N) OrderItem >── (1) Product: Đơn hàng chứa nhiều sản phẩm
   - CartItem và OrderItem lưu giá tại thời điểm thêm vào giỏ/đặt hàng để tránh thay đổi giá

4. **Ràng buộc và bảo vệ dữ liệu**:
   - Tất cả foreign key đều NOT NULL để đảm bảo tính toàn vẹn
   - Sử dụng ON DELETE RESTRICT để ngăn xóa dữ liệu cha khi còn dữ liệu con
   - Index trên các trường thường query để tối ưu hiệu suất

5. **Metadata tracking**:
   - Các bảng đều có createdAt, updatedAt, createdBy, updatedBy để theo dõi lịch sử thay đổi"

---

## GỢI Ý VỀ SLIDE

### Tổng số slide: 36 slides

**Cấu trúc slide:**
1. Slide 1: Tiêu đề
2. Slide 2: Lý do chọn đề tài
3. Slide 3: Tổng quan dự án
4. Slide 4: Mục tiêu dự án
5. Slide 5: Phạm vi dự án
6. Slide 6: Backend Stack
7. Slide 7: Frontend Stack
8. Slide 8: Lý do chọn công nghệ
9. Slide 9: Kiến trúc tổng thể
10. Slide 10: Kiến trúc Backend
11. Slide 11: Kiến trúc Frontend
12. Slide 12: Database Design
13. Slide 13: JWT Authentication
14. Slide 14: Password Encryption
15. Slide 15: Phân quyền
16. Slide 16: Đăng ký và Đăng nhập
17. Slide 17: Duyệt và Tìm kiếm Sản phẩm
18. Slide 18: Giỏ hàng và Đặt hàng
19. Slide 19: Quản lý Tài khoản và Đơn hàng
20. Slide 20: Danh sách Yêu thích
21. Slide 21: Chatbot AI
22. Slide 22: Dashboard Admin
23. Slide 23: Quản lý Người dùng
24. Slide 24: Quản lý Sản phẩm
25. Slide 25: Quản lý Danh mục
26. Slide 26: Quản lý Đơn hàng
27. Slide 27: Upload và Quản lý File
28. Slide 28: Pagination và Filtering
29. Slide 29: Error Handling
30. Slide 30: State Management
31. Slide 31: Kết quả đạt được
32. Slide 32: Kinh nghiệm đạt được
33. Slide 33: Hạn chế
34. Slide 34: Hướng phát triển
35. Slide 35: Kết luận
36. Slide 36: Lời cảm ơn

### Gợi ý thiết kế slide:
- Sử dụng template chuyên nghiệp, màu sắc nhất quán
- Font chữ rõ ràng, dễ đọc (Arial, Calibri, hoặc tương tự)
- Kích thước chữ đủ lớn (tối thiểu 24pt cho nội dung)
- Sử dụng hình ảnh, sơ đồ, biểu đồ để minh họa
- Tránh quá nhiều text trên một slide
- Sử dụng bullet points thay vì đoạn văn dài
- Highlight các điểm quan trọng

---

## LƯU Ý KHI TRÌNH BÀY

### 1. Thời gian
- Tổng thời gian trình bày: 20-25 phút
- Thời gian hỏi đáp: 5-10 phút
- Phân bổ thời gian hợp lý cho từng phần

### 2. Phong cách trình bày
- Nói rõ ràng, không quá nhanh
- Giữ liên hệ bằng mắt với hội đồng
- Tự tin nhưng khiêm tốn
- Sử dụng ngôn ngữ cơ thể phù hợp
- Tránh đọc slide, nên giải thích và mở rộng

### 3. Chuẩn bị
- Test kỹ demo trước khi trình bày
- Chuẩn bị sẵn dữ liệu test (tài khoản admin, sản phẩm mẫu)
- Kiểm tra kết nối internet, trình duyệt
- Có backup plan nếu demo bị lỗi (screenshot, video)
- In slide để phát cho hội đồng (nếu cần)

### 4. Demo
- Demo chậm rãi, rõ ràng
- Giải thích từng bước đang làm
- Highlight các điểm kỹ thuật quan trọng
- Nếu có lỗi, bình tĩnh xử lý hoặc bỏ qua và tiếp tục

### 5. Trả lời câu hỏi
- Lắng nghe câu hỏi cẩn thận
- Suy nghĩ trước khi trả lời
- Trả lời trực tiếp, không lan man
- Nếu không biết, thành thật thừa nhận và hứa sẽ tìm hiểu thêm
- Cảm ơn sau mỗi câu hỏi

---

## CHECKLIST TRƯỚC KHI TRÌNH BÀY

- [ ] Đã chuẩn bị đầy đủ slide (36 slides)
- [ ] Đã test demo tất cả chức năng
- [ ] Đã chuẩn bị tài khoản admin và user để demo
- [ ] Đã chuẩn bị dữ liệu mẫu (sản phẩm, đơn hàng)
- [ ] Đã kiểm tra kết nối internet
- [ ] Đã test trình duyệt và các công cụ cần thiết
- [ ] Đã đọc lại kịch bản và luyện tập trình bày
- [ ] Đã chuẩn bị câu trả lời cho các câu hỏi thường gặp
- [ ] Đã in slide (nếu cần)
- [ ] Đã chuẩn bị backup plan (screenshot, video demo)
- [ ] Đã kiểm tra thời gian trình bày

---

**Chúc bạn trình bày thành công! 🎓**
