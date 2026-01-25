# BÁO CÁO ĐỒ ÁN TỐT NGHIỆP
## HỆ THỐNG THƯƠNG MẠI ĐIỆN TỬ MUJI

---

## MỤC LỤC

1. [GIỚI THIỆU DỰ ÁN](#1-giới-thiệu-dự-án)
2. [PHẦN NGHIỆP VỤ DỰ ÁN](#2-phần-nghiệp-vụ-dự-án)
3. [PHẦN BACKEND](#3-phần-backend)
4. [PHẦN FRONTEND](#4-phần-frontend)
5. [KIẾN TRÚC HỆ THỐNG](#5-kiến-trúc-hệ-thống)
6. [CÔNG NGHỆ SỬ DỤNG](#6-công-nghệ-sử-dụng)
7. [CHỨC NĂNG HỆ THỐNG](#7-chức-năng-hệ-thống)
8. [KẾT LUẬN](#8-kết-luận)

---

## 1. GIỚI THIỆU DỰ ÁN

### 1.1. Tổng quan
Hệ thống thương mại điện tử Muji là một nền tảng bán hàng trực tuyến được xây dựng với kiến trúc hiện đại, tách biệt giữa Frontend và Backend. Hệ thống cho phép người dùng mua sắm các sản phẩm nội thất và đồ dùng gia đình trực tuyến, đồng thời cung cấp công cụ quản lý toàn diện cho quản trị viên.

### 1.2. Mục tiêu dự án
- Xây dựng hệ thống thương mại điện tử hoàn chỉnh với đầy đủ các chức năng cơ bản
- Áp dụng các công nghệ và framework hiện đại trong phát triển web
- Tạo ra trải nghiệm người dùng tốt với giao diện thân thiện, dễ sử dụng
- Đảm bảo tính bảo mật và hiệu suất của hệ thống
- Xây dựng hệ thống quản trị mạnh mẽ cho việc quản lý sản phẩm, đơn hàng và người dùng

### 1.3. Phạm vi dự án
Dự án bao gồm hai phần chính:
- **Backend API**: Xây dựng RESTful API với Spring Boot
- **Frontend Web Application**: Xây dựng giao diện người dùng với Next.js

---

## 2. PHẦN NGHIỆP VỤ DỰ ÁN

### 2.1. Use Cases của hệ thống

#### 2.1.1. Actors

- **USER**: Người dùng thông thường, khách hàng mua sắm
- **ADMIN**: Quản trị viên, quản lý hệ thống

#### 2.1.2. Use Cases cho USER

- **UC01**: Đăng ký tài khoản
- **UC02**: Đăng nhập
- **UC03**: Đăng xuất
- **UC04**: Duyệt sản phẩm (Xem danh sách, Tìm kiếm, Lọc theo danh mục, Xem chi tiết)
- **UC05**: Quản lý giỏ hàng (Thêm, Cập nhật số lượng, Xóa, Xem giỏ hàng)
- **UC06**: Đặt hàng (Nhập thông tin giao hàng, Chọn phương thức thanh toán, Xác nhận đơn hàng)
- **UC07**: Quản lý đơn hàng (Xem lịch sử, Xem chi tiết)
- **UC08**: Quản lý tài khoản (Xem thông tin, Cập nhật thông tin, Upload avatar)
- **UC09**: Danh sách yêu thích (Thêm, Xem, Xóa khỏi wishlist)

#### 2.1.3. Use Cases cho ADMIN

- **UC10**: Quản lý người dùng (Xem danh sách, Tạo, Cập nhật, Xóa, Phân quyền)
- **UC11**: Quản lý sản phẩm (Xem danh sách, Tạo, Cập nhật, Xóa, Upload hình ảnh, Quản lý tồn kho)
- **UC12**: Quản lý danh mục (Quản lý Main Category, Sub Category, Category)
- **UC13**: Quản lý đơn hàng (Xem tất cả đơn hàng, Xem chi tiết, Cập nhật trạng thái)
- **UC14**: Dashboard & Thống kê (Tổng quan, Biểu đồ doanh thu, Biểu đồ đơn hàng, Thống kê sản phẩm bán chạy)

### 2.2. Quy trình nghiệp vụ chính

#### 2.2.1. Quy trình đăng ký và đăng nhập

**Đăng ký tài khoản:**
1. Người dùng truy cập trang đăng ký
2. Nhập thông tin: email, mật khẩu, họ tên
3. Hệ thống kiểm tra email đã tồn tại chưa
4. Nếu chưa tồn tại: Mã hóa mật khẩu bằng BCrypt
5. Tạo tài khoản mới với role mặc định là USER
6. Trả về thông báo thành công

**Đăng nhập:**
1. Người dùng nhập email và mật khẩu
2. Hệ thống xác thực thông tin đăng nhập
3. Nếu hợp lệ: Tạo JWT access token và refresh token
4. Lưu refresh token vào httpOnly cookie
5. Trả về access token và thông tin người dùng
6. Frontend lưu access token vào Redux store

#### 2.2.2. Quy trình mua hàng

**Duyệt và tìm kiếm sản phẩm:**
1. Người dùng xem danh sách sản phẩm trên trang chủ
2. Có thể lọc theo danh mục (Main Category → Sub Category → Category)
3. Có thể tìm kiếm sản phẩm theo tên
4. Xem chi tiết sản phẩm: hình ảnh, mô tả, giá, tồn kho

**Thêm vào giỏ hàng:**
1. Người dùng chọn sản phẩm và số lượng
2. Hệ thống kiểm tra tồn kho
3. Nếu đủ hàng: Tạo hoặc cập nhật CartItem trong giỏ hàng
4. Nếu sản phẩm đã có trong giỏ: Cộng dồn số lượng
5. Cập nhật tổng số lượng trong giỏ hàng

**Đặt hàng:**
1. Người dùng xem giỏ hàng và kiểm tra lại sản phẩm
2. Nhập thông tin giao hàng (địa chỉ, số điện thoại)
3. Chọn phương thức thanh toán (COD hoặc Credit Card)
4. Nếu chọn Credit Card: Nhập thông tin thẻ
5. Hệ thống tính toán: Subtotal + Shipping Fee + Tax = Total
6. Xác nhận đơn hàng
7. Tạo Order và OrderItem từ Cart và CartItem
8. Cập nhật trạng thái Cart thành "completed"
9. Trả về thông tin đơn hàng đã tạo

#### 2.2.3. Quy trình quản lý sản phẩm (Admin)

**Tạo sản phẩm mới:**
1. Admin truy cập trang quản lý sản phẩm
2. Chọn "Tạo sản phẩm mới"
3. Nhập thông tin: tên, mô tả, giá (minPrice, maxPrice), số lượng tồn kho
4. Chọn danh mục (Category)
5. Upload hình ảnh sản phẩm
6. Hệ thống lưu file vào `/uploads/images/products/`
7. Tạo Product entity và lưu vào database
8. Trả về thông báo thành công

**Cập nhật sản phẩm:**
1. Admin chọn sản phẩm cần sửa
2. Cập nhật thông tin sản phẩm
3. Có thể upload hình ảnh mới (tùy chọn)
4. Nếu có hình ảnh mới: Xóa hình cũ, lưu hình mới
5. Cập nhật Product entity
6. Trả về thông báo thành công

**Xóa sản phẩm:**
1. Admin chọn sản phẩm cần xóa
2. Hệ thống kiểm tra sản phẩm có trong đơn hàng không
3. Nếu không có: Xóa sản phẩm và hình ảnh liên quan
4. Trả về thông báo thành công

#### 2.2.4. Quy trình quản lý đơn hàng

**Xem đơn hàng (User):**
1. Người dùng đăng nhập
2. Truy cập trang "Lịch sử đơn hàng"
3. Hệ thống lấy danh sách đơn hàng của user hiện tại
4. Hiển thị: Mã đơn hàng, ngày đặt, tổng tiền, trạng thái
5. Có thể xem chi tiết từng đơn hàng

**Quản lý đơn hàng (Admin):**
1. Admin truy cập trang quản lý đơn hàng
2. Xem tất cả đơn hàng với phân trang và filter
3. Có thể lọc theo: trạng thái, ngày đặt, người dùng
4. Xem chi tiết đơn hàng: thông tin giao hàng, sản phẩm, thanh toán
5. Cập nhật trạng thái đơn hàng: pending → processing → shipped → delivered

#### 2.2.5. Quy trình quản lý danh mục

**Cấu trúc danh mục:**
- Main Category (Danh mục chính)
  - Sub Category (Danh mục phụ)
    - Category (Danh mục)
      - Product (Sản phẩm)

**Tạo danh mục:**
1. Admin chọn loại danh mục cần tạo (Main/Sub/Category)
2. Nhập tên danh mục
3. Upload hình ảnh đại diện (nếu có)
4. Chọn danh mục cha (đối với Sub Category và Category)
5. Lưu vào database

### 2.3. Luồng dữ liệu chính

#### 2.3.1. Luồng xác thực
```
User → Frontend (Login Form)
  → POST /api/v1/auth/login
  → Backend (AuthController)
  → UserDetailsService (loadUserByUsername)
  → UserService (verify password)
  → SecurityUtil (create JWT token)
  → Response (Access Token + Refresh Token)
  → Frontend (Store token in Redux + Cookie)
```

#### 2.3.2. Luồng đặt hàng
```
User → Frontend (Checkout Page)
  → POST /api/v1/orders
  → Backend (OrderController)
  → OrderService (handleCreateOrder)
  → CartRepository (get cart)
  → CartItemRepository (get cart items)
  → Calculate totals
  → OrderRepository (save order)
  → OrderItemRepository (save order items)
  → Update cart status
  → Response (Order details)
  → Frontend (Confirmation page)
```

#### 2.3.3. Luồng quản lý sản phẩm
```
Admin → Frontend (Product Management)
  → POST /api/v1/products (multipart/form-data)
  → Backend (ProductController)
  → ProductService (handleCreateProduct)
  → ImageService (upload image)
  → ProductRepository (save product)
  → Response (Product details)
  → Frontend (Product list updated)
```

---

## 3. PHẦN BACKEND

### 3.1. Tổng quan Backend
Backend được xây dựng bằng Spring Boot 3.2.4, sử dụng Java 17, cung cấp RESTful API cho ứng dụng Frontend. Hệ thống sử dụng kiến trúc 3 lớp (Controller - Service - Repository) để đảm bảo tính modular và dễ bảo trì.

### 3.2. Công nghệ Backend

#### 3.2.1. Framework và Thư viện chính
- **Spring Boot 3.2.4**: Framework chính cho việc xây dựng ứng dụng
- **Spring Security**: Xử lý xác thực và phân quyền
- **Spring Data JPA**: Truy cập và quản lý dữ liệu
- **OAuth2 Resource Server**: Xác thực JWT token
- **MySQL Connector**: Kết nối với cơ sở dữ liệu MySQL
- **Lombok**: Giảm boilerplate code
- **Spring Filter**: Hỗ trợ filtering và pagination

#### 3.2.2. Cơ sở dữ liệu
- **MySQL**: Hệ quản trị cơ sở dữ liệu quan hệ
- **JPA/Hibernate**: ORM framework

### 3.3. Kiến trúc Backend

#### 3.3.1. Cấu trúc thư mục
```
src/main/java/vn/hoidanit/jobhunter/
├── config/          # Cấu hình Security, JWT
├── controller/      # REST Controllers
├── domain/          # Entity models và DTOs
├── repository/      # Data access layer
├── service/         # Business logic layer
└── util/            # Utilities và helpers
```

#### 3.3.2. Các Controller chính
1. **AuthController**: Xử lý đăng nhập, đăng xuất, refresh token
2. **UserController**: Quản lý người dùng (CRUD)
3. **ProductController**: Quản lý sản phẩm (CRUD, tìm kiếm, phân trang)
4. **CategoryController**: Quản lý danh mục sản phẩm
5. **MainCategoryController**: Quản lý danh mục chính
6. **SubCategoryController**: Quản lý danh mục phụ
7. **CartController**: Quản lý giỏ hàng
8. **OrderController**: Quản lý đơn hàng
9. **WishlistController**: Quản lý danh sách yêu thích
10. **ReviewController**: Quản lý đánh giá sản phẩm
11. **ImageController**: Xử lý upload và quản lý hình ảnh

### 3.4. Bảo mật và Xác thực

#### 3.4.1. JWT Authentication - Chi tiết kỹ thuật

**Cơ chế JWT:**
- Sử dụng thuật toán HS512 (HMAC-SHA512) để ký token
- Secret key được mã hóa Base64 và lưu trong `application.properties`
- Token bao gồm 3 phần: Header, Payload, Signature

**Cấu trúc Access Token:**
```json
{
  "sub": "user@example.com",
  "iat": 1234567890,
  "exp": 1234654290,
  "user": {
    "id": 1,
    "email": "user@example.com",
    "role": "USER"
  },
  "permission": ["ROLE_USER"],
  "role": "USER"
}
```

**Quy trình tạo token:**
1. User đăng nhập thành công
2. `SecurityUtil.createAccessToken()` được gọi
3. Tạo JwtClaimsSet với thông tin user và permissions
4. Sử dụng JwtEncoder để encode token
5. Token được trả về trong response body

**Refresh Token:**
- Thời hạn: 100 ngày (8,640,000 giây)
- Lưu trữ trong httpOnly cookie để bảo mật
- Cookie được set với flags: `httpOnly=true`, `secure=true`, `path=/`
- Khi access token hết hạn, frontend gọi `/api/v1/auth/refresh` để lấy token mới

**Xử lý token hết hạn:**
- Backend kiểm tra `exp` claim trong token
- Nếu hết hạn: Trả về 401 Unauthorized
- Frontend interceptor tự động gọi refresh token endpoint
- Nếu refresh token hợp lệ: Tạo access token mới và tiếp tục request

#### 3.4.2. Spring Security Configuration - Chi tiết implementation

**SecurityFilterChain Configuration:**
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) {
    http
        .csrf(c -> c.disable())  // Disable CSRF cho REST API
        .cors(Customizer.withDefaults())  // Enable CORS
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
            .requestMatchers("/api/v1/auth/login", "/api/v1/users/register").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(Customizer.withDefaults())
            .authenticationEntryPoint(customAuthenticationEntryPoint)
        )
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
    return http.build();
}
```

**Phân quyền theo Role:**
- Sử dụng `@PreAuthorize` và `RoleUtil.isAdmin()` để kiểm tra quyền
- ADMIN có quyền: ROLE_ADMIN, ROLE_USER_CREATE, ROLE_USER_UPDATE, ROLE_USER_DELETE
- USER chỉ có quyền: ROLE_USER
- Permissions được embed trong JWT token claims

**CORS Configuration:**
- Cho phép requests từ `http://localhost:3000` và `http://localhost:3001`
- Headers được phép: Authorization, Content-Type
- Methods được phép: GET, POST, PUT, DELETE, OPTIONS

**Custom Authentication Entry Point:**
- Xử lý lỗi 401 Unauthorized
- Trả về JSON response với format chuẩn
- Log lỗi để debug

#### 3.4.3. Password Encryption - Chi tiết kỹ thuật

**BCrypt Hashing:**
- Sử dụng BCrypt với strength = 10 (mặc định)
- Mỗi lần hash tạo ra salt ngẫu nhiên khác nhau
- Format: `$2a$10$[salt][hash]` (22 ký tự salt + 31 ký tự hash)

**Quy trình mã hóa:**
1. User đăng ký/đổi mật khẩu
2. `BCryptPasswordEncoder.encode(password)` được gọi
3. Mật khẩu được hash với salt ngẫu nhiên
4. Hash được lưu vào database

**Quy trình xác thực:**
1. User đăng nhập với email và password
2. Lấy password hash từ database
3. `BCryptPasswordEncoder.matches(rawPassword, encodedPassword)` so sánh
4. Nếu khớp: Xác thực thành công

**Bảo mật:**
- Mật khẩu không bao giờ được lưu dạng plain text
- Không thể reverse hash để lấy lại mật khẩu gốc
- Mỗi hash là duy nhất ngay cả với cùng mật khẩu

### 3.5. Các Entity chính

#### 3.5.1. User
- Thông tin người dùng: email, password, firstName, lastName
- Địa chỉ: address1, address2, address3, address4
- Thông tin cá nhân: phone, birthday, gender, avatarUrl
- Xác thực: refreshToken
- Phân quyền: role (USER, ADMIN)

#### 3.5.2. Product
- Thông tin sản phẩm: name, description
- Giá: minPrice, maxPrice
- Tồn kho: stockQuantity
- Hình ảnh: imageUrl
- Liên kết: categoryId

#### 3.5.3. Category
- Danh mục sản phẩm với cấu trúc phân cấp
- MainCategory → SubCategory → Category → Product

#### 3.5.4. Order & OrderItem
- Quản lý đơn hàng và chi tiết đơn hàng
- Thông tin: userId, orderDate, totalAmount, status

#### 3.5.5. Cart & CartItem
- Quản lý giỏ hàng của người dùng
- Lưu trữ sản phẩm và số lượng

#### 3.5.6. Wishlist
- Danh sách sản phẩm yêu thích của người dùng

#### 3.5.7. Review
- Đánh giá và nhận xét về sản phẩm

### 3.6. API Endpoints chính

#### 3.6.1. Authentication
- `POST /api/v1/auth/login`: Đăng nhập
- `POST /api/v1/auth/logout`: Đăng xuất
- `GET /api/v1/auth/refresh`: Làm mới token
- `GET /api/v1/auth/account`: Lấy thông tin tài khoản hiện tại

#### 3.6.2. User Management
- `POST /api/v1/users/register`: Đăng ký tài khoản mới
- `GET /api/v1/users`: Lấy danh sách người dùng (Admin)
- `GET /api/v1/users/{id}`: Lấy thông tin người dùng theo ID
- `GET /api/v1/users/me`: Lấy thông tin người dùng hiện tại
- `PUT /api/v1/users/me`: Cập nhật thông tin người dùng hiện tại
- `PUT /api/v1/users/{id}`: Cập nhật người dùng (Admin)
- `DELETE /api/v1/users/{id}`: Xóa người dùng (Admin)

#### 3.6.3. Product Management
- `GET /api/v1/products`: Lấy danh sách sản phẩm (có phân trang, filter)
- `GET /api/v1/products/{id}`: Lấy chi tiết sản phẩm
- `GET /api/v1/products/sub-category/{subCategoryId}`: Lấy sản phẩm theo sub-category
- `GET /api/v1/products/category/{categoryId}`: Lấy sản phẩm theo category
- `POST /api/v1/products`: Tạo sản phẩm mới (có upload hình ảnh)
- `PUT /api/v1/products/{id}`: Cập nhật sản phẩm
- `DELETE /api/v1/products/{id}`: Xóa sản phẩm

#### 3.6.4. Category Management
- `GET /api/v1/main-categories`: Lấy danh sách danh mục chính
- `GET /api/v1/sub-categories`: Lấy danh sách danh mục phụ
- `GET /api/v1/categories/{id}`: Lấy thông tin category
- `GET /api/v1/categories/sub-category/{id}`: Lấy categories theo sub-category

#### 3.6.5. Cart Management
- `GET /api/v1/cart`: Lấy giỏ hàng của người dùng hiện tại
- `POST /api/v1/cart`: Thêm sản phẩm vào giỏ hàng
- `PUT /api/v1/cart/{cartItemId}`: Cập nhật số lượng sản phẩm
- `DELETE /api/v1/cart/{cartItemId}`: Xóa sản phẩm khỏi giỏ hàng
- `GET /api/v1/cart/total-quantity`: Lấy tổng số lượng sản phẩm trong giỏ

#### 3.6.6. Order Management
- `POST /api/v1/orders`: Tạo đơn hàng mới
- `GET /api/v1/orders`: Lấy danh sách đơn hàng của người dùng
- `GET /api/v1/orders?page=&size=`: Lấy tất cả đơn hàng với phân trang (Admin)
- `GET /api/v1/orders/{id}`: Lấy chi tiết đơn hàng

#### 3.6.7. Wishlist Management
- `GET /api/v1/wishlist`: Lấy danh sách yêu thích
- `POST /api/v1/wishlist`: Thêm sản phẩm vào wishlist
- `DELETE /api/v1/wishlist/{productId}`: Xóa sản phẩm khỏi wishlist

### 3.7. Xử lý File Upload - Chi tiết kỹ thuật

**Cấu hình:**
- `spring.servlet.multipart.max-file-size=50MB`: Giới hạn kích thước file
- `spring.servlet.multipart.max-request-size=50MB`: Giới hạn tổng kích thước request
- `spring.servlet.multipart.enabled=true`: Bật multipart support

**Quy trình upload:**
1. Frontend gửi `multipart/form-data` với file
2. Backend nhận `MultipartFile` trong Controller
3. Validate file: kiểm tra kích thước, định dạng
4. Tạo tên file unique: `timestamp-originalFilename`
5. Lưu file vào thư mục tương ứng:
   - Products: `/uploads/images/products/`
   - Categories: `/uploads/images/categories/`
   - Users: `/uploads/images/users/`
6. Lưu đường dẫn vào database: `/uploads/images/{type}/{filename}`
7. Trả về URL để frontend hiển thị

**Xử lý lỗi:**
- File quá lớn: Trả về 413 Payload Too Large
- Định dạng không hợp lệ: Trả về 400 Bad Request
- Lỗi I/O: Log và trả về 500 Internal Server Error

**Tối ưu:**
- Validate file type trước khi lưu
- Tạo tên file unique để tránh conflict
- Xóa file cũ khi update (nếu có file mới)

### 3.8. Pagination và Filtering - Chi tiết kỹ thuật

**Spring Data JPA Pagination:**
```java
@GetMapping("/products")
public ResponseEntity<ResultPaginationDTO> getAllProduct(
    @Filter Specification<Product> spec, 
    Pageable pageable
) {
    // Pageable: page, size, sort
    // Specification: dynamic filtering
    return ResponseEntity.ok(productService.handleGetAllProduct(spec, pageable));
}
```

**Spring Filter Integration:**
- Sử dụng `@Filter` annotation để parse query parameters
- Hỗ trợ filter động: `?name=sofa&minPrice=100&maxPrice=500`
- Tự động convert thành JPA Specification

**Pagination Parameters:**
- `page`: Số trang (bắt đầu từ 1, one-indexed)
- `size`: Số items mỗi trang (mặc định: 20)
- `sort`: Sắp xếp (ví dụ: `sort=name,asc`)

**Response Format:**
```json
{
  "meta": {
    "current": 1,
    "pageSize": 20,
    "pages": 5,
    "total": 100
  },
  "result": [...]
}
```

**Specification Pattern:**
- Cho phép build dynamic queries
- Kết hợp nhiều điều kiện với AND/OR
- Hỗ trợ các toán tử: equals, like, greaterThan, lessThan, in, etc.

### 3.9. Database Design - Chi tiết kỹ thuật

**Entity Relationships:**
```
User (1) ──< (N) Cart ──< (N) CartItem >── (1) Product
User (1) ──< (N) Order ──< (N) OrderItem >── (1) Product
User (1) ──< (N) Wishlist >── (1) Product
MainCategory (1) ──< (N) SubCategory (1) ──< (N) Category (1) ──< (N) Product
```

**JPA Annotations sử dụng:**
- `@Entity`: Đánh dấu class là entity
- `@Table`: Chỉ định tên bảng
- `@Id`, `@GeneratedValue`: Primary key
- `@OneToMany`, `@ManyToOne`: Quan hệ giữa các entity
- `@Column`: Cấu hình column
- `@PrePersist`, `@PreUpdate`: Callback trước khi save/update

**Transaction Management:**
- Sử dụng `@Transactional` cho các method modify data
- `@Transactional(readOnly = true)` cho các method chỉ đọc
- Tự động rollback khi có exception

### 3.10. Error Handling - Chi tiết kỹ thuật

**Custom Exception:**
- `IdInvalidException`: Xử lý lỗi validation và business logic
- Trả về HTTP status code phù hợp (400, 404, 403, 500)
- Message rõ ràng, dễ hiểu

**Global Exception Handler:**
- `@ControllerAdvice` để xử lý exception toàn cục
- Convert exception thành JSON response chuẩn
- Log lỗi để debug

**Response Format:**
```json
{
  "statusCode": 400,
  "message": "Error message",
  "error": "Bad Request"
}
```

---

## 3. PHẦN FRONTEND

### 3.1. Tổng quan Frontend
Frontend được xây dựng bằng Next.js 14.2.15 với React 18 và TypeScript, sử dụng kiến trúc App Router mới nhất. Ứng dụng được thiết kế với giao diện hiện đại, responsive và tối ưu trải nghiệm người dùng.

### 3.2. Công nghệ Frontend

#### 3.2.1. Framework và Thư viện chính
- **Next.js 14.2.15**: React framework với SSR và SSG
- **React 18**: UI library
- **TypeScript 5**: Type-safe JavaScript
- **Redux Toolkit**: State management
- **React Redux**: React bindings cho Redux
- **Axios**: HTTP client cho API calls
- **React Hook Form**: Form handling
- **Yup**: Schema validation
- **SCSS/SASS**: CSS preprocessor

#### 3.2.2. UI Libraries
- **Bootstrap 5.3.3**: CSS framework
- **React Bootstrap**: Bootstrap components cho React
- **Ant Design 5.21.6**: UI component library
- **Font Awesome**: Icon library

#### 3.2.3. Thư viện hỗ trợ
- **Chart.js & Recharts**: Vẽ biểu đồ thống kê
- **Swiper**: Carousel và slider
- **React Select**: Dropdown components
- **Date-fns**: Xử lý ngày tháng
- **Lodash**: Utility functions
- **Universal Cookie**: Cookie management

### 3.3. Kiến trúc Frontend

#### 3.3.1. Cấu trúc thư mục
```
src/app/
├── auth/              # Authentication pages
│   ├── login/
│   └── registration/
├── components/        # Reusable components
├── dashboard/         # Admin dashboard
│   ├── user/
│   ├── product/
│   ├── order/
│   └── chart/
├── product/           # Product pages
│   ├── category/
│   ├── detail/
│   ├── search/
│   └── subCategory/
├── cart/              # Shopping cart
├── order/            # Order pages
│   ├── checkout/
│   └── confirmation/
├── profile/           # User profile
├── wishlist/          # Wishlist page
├── billing/           # Billing page
├── redux/             # Redux store và slices
├── utils/             # Utility functions
├── types/             # TypeScript types
└── storage/           # Local storage helpers
```

### 3.4. State Management - Chi tiết kỹ thuật

#### 3.4.1. Redux Store Architecture

**Store Structure:**
```typescript
{
  user: {
    user: User | null,
    accessToken: string | null,
    isAuthenticated: boolean
  },
  cart: {
    items: CartItem[],
    totalQuantity: number
  },
  wishlist: {
    items: Product[]
  }
}
```

**Redux Toolkit Slices:**

**authSlice:**
- `setUser`: Lưu thông tin user và token sau khi đăng nhập
- `clearUser`: Xóa thông tin khi đăng xuất
- `updateToken`: Cập nhật access token mới

**cartSlice:**
- `setCartItems`: Lưu danh sách sản phẩm trong giỏ
- `setTotalQuantity`: Cập nhật tổng số lượng
- `clearCart`: Xóa giỏ hàng sau khi đặt hàng thành công

**wishlistSlice:**
- `setWishlistItems`: Lưu danh sách yêu thích
- `addToWishlist`: Thêm sản phẩm vào wishlist
- `removeFromWishlist`: Xóa sản phẩm khỏi wishlist

**Redux Persist (Optional):**
- Có thể sử dụng redux-persist để lưu state vào localStorage
- Giữ trạng thái khi refresh page
- Hiện tại không sử dụng để đảm bảo tính nhất quán với backend

#### 3.4.2. API Integration - Chi tiết implementation

**Axios Configuration:**
```typescript
const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});
```

**Request Interceptor:**
```typescript
axiosInstance.interceptors.request.use(
  (config) => {
    const token = getAccessToken(); // Lấy từ Redux store
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
```

**Response Interceptor:**
```typescript
axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    
    // Nếu lỗi 401 và chưa retry
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      
      try {
        // Gọi refresh token endpoint
        const response = await axios.get('/auth/refresh', {
          withCredentials: true // Gửi cookie chứa refresh token
        });
        
        const { accessToken } = response.data;
        // Lưu token mới vào Redux
        dispatch(setUser({ accessToken }));
        
        // Retry request ban đầu với token mới
        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
        return axiosInstance(originalRequest);
      } catch (refreshError) {
        // Refresh token hết hạn, đăng xuất
        dispatch(clearUser());
        router.push('/auth/login');
        return Promise.reject(refreshError);
      }
    }
    
    return Promise.reject(error);
  }
);
```

**Error Handling:**
- Tập trung xử lý lỗi trong interceptor
- Hiển thị thông báo lỗi phù hợp với từng status code
- Log lỗi để debug

### 3.5. Routing và Navigation
- Sử dụng Next.js App Router
- Client-side navigation với `useRouter`
- Protected routes cho các trang yêu cầu đăng nhập
- Middleware xử lý authentication

### 3.6. Các trang chính

#### 3.6.1. Trang chủ (Home)
- Carousel banner quảng cáo
- Gallery danh mục sản phẩm
- Sản phẩm nổi bật (Featured Products)
- Lifestyle section

#### 3.6.2. Trang sản phẩm
- Danh sách sản phẩm với phân trang
- Lọc sản phẩm theo danh mục
- Tìm kiếm sản phẩm
- Chi tiết sản phẩm

#### 3.6.3. Giỏ hàng (Cart)
- Hiển thị sản phẩm trong giỏ hàng
- Cập nhật số lượng
- Xóa sản phẩm
- Tính tổng tiền

#### 3.6.4. Đặt hàng (Order)
- Trang checkout
- Xác nhận đơn hàng
- Lịch sử đơn hàng

#### 3.6.5. Trang cá nhân (Profile)
- Xem và chỉnh sửa thông tin cá nhân
- Upload avatar
- Quản lý địa chỉ

#### 3.6.6. Danh sách yêu thích (Wishlist)
- Xem danh sách sản phẩm yêu thích
- Thêm/xóa sản phẩm

#### 3.6.7. Dashboard (Admin)
- **Overview**: Tổng quan thống kê với biểu đồ
- **User Management**: Quản lý người dùng (CRUD)
- **Product Management**: Quản lý sản phẩm (CRUD, upload hình ảnh)
- **Order Management**: Quản lý đơn hàng
- **Charts**: Biểu đồ thống kê doanh thu, đơn hàng

### 3.7. Components chính

#### 3.7.1. Layout Components
- **Navbar**: Thanh điều hướng chính
- **TopNavbar**: Thanh điều hướng trên cùng
- **Footer**: Chân trang
- **Sidebar**: Menu bên cho dashboard

#### 3.7.2. Product Components
- **ProductCard**: Card hiển thị sản phẩm
- **ProductDetail**: Chi tiết sản phẩm
- **ProductGallery**: Gallery hình ảnh sản phẩm
- **ImageGallery**: Gallery danh mục

#### 3.7.3. Cart Components
- **CartItem**: Item trong giỏ hàng
- **CartSummary**: Tóm tắt giỏ hàng

#### 3.7.4. Form Components
- **LoginForm**: Form đăng nhập
- **RegistrationForm**: Form đăng ký
- **ProductForm**: Form tạo/sửa sản phẩm
- **UserForm**: Form tạo/sửa người dùng

#### 3.7.5. UI Components
- **CustomCarousel**: Carousel tùy chỉnh
- **Modal**: Modal dialog
- **Accordion**: Accordion component
- **Table**: Bảng dữ liệu với pagination

### 3.8. Styling
- **SCSS Modules**: Component-scoped styles
- **Global SCSS**: Styles chung
- **Bootstrap**: Utility classes
- **Responsive Design**: Mobile-first approach

### 3.9. Authentication Flow - Chi tiết kỹ thuật

**Luồng đăng nhập:**
1. User nhập email và password
2. Frontend gửi POST `/api/v1/auth/login`
3. Backend xác thực và trả về:
   - Access token (trong response body)
   - Refresh token (trong httpOnly cookie)
   - User information
4. Frontend lưu access token vào Redux store
5. Cookie chứa refresh token được browser tự động quản lý

**Protected Routes:**
```typescript
// middleware.ts
export function middleware(request: NextRequest) {
  const token = request.cookies.get('accessToken');
  const { pathname } = request.nextUrl;
  
  // Routes cần authentication
  const protectedRoutes = ['/dashboard', '/profile', '/cart', '/order'];
  
  if (protectedRoutes.some(route => pathname.startsWith(route))) {
    if (!token) {
      return NextResponse.redirect(new URL('/auth/login', request.url));
    }
  }
  
  return NextResponse.next();
}
```

**Token Refresh Flow:**
1. Request bị reject với 401 Unauthorized
2. Interceptor phát hiện lỗi 401
3. Gọi GET `/api/v1/auth/refresh` với cookie
4. Backend verify refresh token và tạo access token mới
5. Frontend cập nhật token mới vào Redux
6. Retry request ban đầu với token mới

**Logout Flow:**
1. User click logout
2. Gọi POST `/api/v1/auth/logout`
3. Backend xóa refresh token trong database
4. Frontend xóa access token khỏi Redux
5. Clear cookie (nếu có)
6. Redirect về trang login

### 3.10. Form Validation - Chi tiết kỹ thuật

**React Hook Form Integration:**
```typescript
const { register, handleSubmit, formState: { errors } } = useForm({
  resolver: yupResolver(validationSchema)
});
```

**Yup Schema Example:**
```typescript
const loginSchema = yup.object({
  email: yup
    .string()
    .required('Email is required')
    .email('Invalid email format'),
  password: yup
    .string()
    .required('Password is required')
    .min(6, 'Password must be at least 6 characters')
});
```

**Validation Features:**
- **Real-time validation**: Validate khi user nhập (onChange)
- **Submit validation**: Validate toàn bộ form khi submit
- **Custom validators**: Validate phức tạp (ví dụ: password strength)
- **Async validation**: Validate với server (ví dụ: check email exists)

**Error Display:**
- Hiển thị lỗi ngay dưới field
- Màu sắc và icon để dễ nhận biết
- Tổng hợp lỗi ở đầu form (nếu có)

### 3.11. Performance Optimization

**Code Splitting:**
- Next.js tự động code splitting theo route
- Dynamic import cho components lớn
- Lazy loading cho images

**Image Optimization:**
- Sử dụng Next.js Image component
- Tự động resize và optimize images
- Lazy loading images ngoài viewport

**State Optimization:**
- Chỉ re-render components cần thiết
- Sử dụng React.memo cho components không thay đổi
- useMemo và useCallback để tránh re-computation

**API Optimization:**
- Caching responses khi có thể
- Debounce cho search input
- Pagination để giảm data transfer

### 3.12. TypeScript Integration

**Type Safety:**
- Tất cả components và functions đều có type definitions
- API responses được type với interfaces
- Redux state được type với TypeScript

**Type Definitions:**
```typescript
interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: 'USER' | 'ADMIN';
}

interface Product {
  id: number;
  name: string;
  minPrice: number;
  maxPrice: number;
  description: string;
  stockQuantity: number;
  imageUrl: string;
}
```

**Benefits:**
- Phát hiện lỗi tại compile time
- Autocomplete tốt hơn trong IDE
- Dễ refactor và maintain code
- Self-documenting code

---

## 5. KIẾN TRÚC HỆ THỐNG

### 5.1. Kiến trúc tổng thể
Hệ thống sử dụng kiến trúc Client-Server với sự tách biệt rõ ràng giữa Frontend và Backend:

```
┌─────────────────┐         ┌─────────────────┐
│   Frontend      │         │    Backend      │
│   (Next.js)     │◄───────►│  (Spring Boot)  │
│   Port: 3000    │  HTTP   │   Port: 8080    │
└─────────────────┘         └─────────────────┘
                                      │
                                      ▼
                            ┌─────────────────┐
                            │   MySQL Database │
                            └─────────────────┘
```

### 5.2. Luồng xử lý request
1. User tương tác với Frontend
2. Frontend gửi HTTP request đến Backend API
3. Backend xử lý request (authentication, validation, business logic)
4. Backend truy vấn/ cập nhật database
5. Backend trả về response (JSON)
6. Frontend cập nhật UI dựa trên response

### 5.3. Security Flow
1. User đăng nhập → Backend xác thực → Trả về JWT token
2. Frontend lưu token → Gửi kèm trong header mỗi request
3. Backend verify token → Xác thực quyền truy cập
4. Refresh token tự động khi access token hết hạn

---

## 6. CÔNG NGHỆ SỬ DỤNG

### 6.1. Backend Stack
| Công nghệ | Phiên bản | Mục đích |
|-----------|-----------|----------|
| Java | 17 | Ngôn ngữ lập trình |
| Spring Boot | 3.2.4 | Framework chính |
| Spring Security | 3.2.4 | Bảo mật và xác thực |
| Spring Data JPA | 3.2.4 | ORM và truy cập dữ liệu |
| MySQL | Latest | Cơ sở dữ liệu |
| JWT | - | Xác thực token |
| Lombok | 8.6 | Giảm boilerplate code |
| Gradle | - | Build tool |

### 6.2. Frontend Stack
| Công nghệ | Phiên bản | Mục đích |
|-----------|-----------|----------|
| Next.js | 14.2.15 | React framework |
| React | 18 | UI library |
| TypeScript | 5 | Type safety |
| Redux Toolkit | 2.3.0 | State management |
| Axios | 1.7.7 | HTTP client |
| Bootstrap | 5.3.3 | CSS framework |
| Ant Design | 5.21.6 | UI components |
| SCSS | 1.80.1 | CSS preprocessor |
| React Hook Form | 7.53.2 | Form handling |
| Yup | 1.4.0 | Validation |

### 6.3. Development Tools
- **Docker**: Containerization
- **Docker Compose**: Multi-container orchestration
- **Git**: Version control
- **Postman/Insomnia**: API testing

---

## 7. CHỨC NĂNG HỆ THỐNG

### 7.1. Chức năng người dùng (User)

#### 7.1.1. Đăng ký và Xác thực
- Đăng ký tài khoản mới với email và mật khẩu
- Đăng nhập với email và mật khẩu
- Đăng xuất
- Làm mới token tự động

#### 7.1.2. Quản lý tài khoản
- Xem thông tin cá nhân
- Cập nhật thông tin (tên, địa chỉ, số điện thoại)
- Upload và thay đổi avatar
- Quản lý địa chỉ giao hàng

#### 7.1.3. Mua sắm
- Duyệt danh mục sản phẩm
- Xem chi tiết sản phẩm
- Tìm kiếm sản phẩm
- Lọc sản phẩm theo danh mục, giá
- Thêm sản phẩm vào giỏ hàng
- Quản lý giỏ hàng (thêm, sửa, xóa)
- Đặt hàng
- Xem lịch sử đơn hàng
- Xem chi tiết đơn hàng

#### 7.1.4. Danh sách yêu thích
- Thêm sản phẩm vào wishlist
- Xem danh sách yêu thích
- Xóa sản phẩm khỏi wishlist

### 7.2. Chức năng quản trị viên (Admin)

#### 7.2.1. Dashboard
- Tổng quan thống kê (số người dùng, sản phẩm, đơn hàng)
- Biểu đồ doanh thu theo thời gian
- Biểu đồ đơn hàng theo trạng thái
- Thống kê sản phẩm bán chạy

#### 7.2.2. Quản lý người dùng
- Xem danh sách người dùng (có phân trang, tìm kiếm)
- Xem chi tiết người dùng
- Cập nhật thông tin người dùng
- Xóa người dùng
- Phân quyền (USER, ADMIN)

#### 7.2.3. Quản lý sản phẩm
- Xem danh sách sản phẩm (có phân trang, filter)
- Tạo sản phẩm mới
- Upload hình ảnh sản phẩm
- Cập nhật thông tin sản phẩm
- Xóa sản phẩm
- Quản lý tồn kho

#### 7.2.4. Quản lý danh mục
- Quản lý danh mục chính (Main Category)
- Quản lý danh mục phụ (Sub Category)
- Quản lý danh mục (Category)
- Upload hình ảnh danh mục

#### 7.2.5. Quản lý đơn hàng
- Xem tất cả đơn hàng (có phân trang, filter)
- Xem chi tiết đơn hàng
- Cập nhật trạng thái đơn hàng
- Thống kê đơn hàng

### 7.3. Chức năng hệ thống

#### 7.3.1. Bảo mật
- Xác thực JWT token
- Mã hóa mật khẩu bằng BCrypt
- Phân quyền theo role
- CORS protection
- XSS protection
- SQL injection prevention (JPA)

#### 7.3.2. Upload và quản lý file
- Upload hình ảnh sản phẩm
- Upload avatar người dùng
- Upload hình ảnh danh mục
- Giới hạn kích thước file
- Validate file type

#### 7.3.3. Pagination và Filtering
- Phân trang cho danh sách dữ liệu
- Filter động với nhiều tiêu chí
- Sắp xếp dữ liệu

---

## 8. KẾT LUẬN

### 8.1. Tổng kết
Dự án đã xây dựng thành công một hệ thống thương mại điện tử hoàn chỉnh với đầy đủ các chức năng cơ bản và nâng cao. Hệ thống được phát triển với các công nghệ hiện đại, đảm bảo tính bảo mật, hiệu suất và khả năng mở rộng.

### 8.2. Điểm mạnh
- Kiến trúc tách biệt rõ ràng giữa Frontend và Backend
- Sử dụng các công nghệ và framework hiện đại, phổ biến
- Bảo mật tốt với JWT authentication và Spring Security
- Giao diện người dùng thân thiện, responsive
- Code được tổ chức tốt, dễ bảo trì và mở rộng
- Hệ thống quản trị mạnh mẽ với đầy đủ chức năng CRUD

### 8.3. Hướng phát triển
- Tích hợp thanh toán trực tuyến (VNPay, PayPal, Stripe)
- Tích hợp đánh giá và nhận xét sản phẩm
- Hệ thống khuyến mãi và giảm giá
- Tích hợp chat hỗ trợ khách hàng
- Tối ưu SEO cho trang web
- Tích hợp social login (Google, Facebook)
- Hệ thống thông báo real-time
- Mobile app (React Native)

### 8.4. Kinh nghiệm đạt được
Qua quá trình phát triển dự án, đã học được và áp dụng:
- Xây dựng RESTful API với Spring Boot
- Xác thực và phân quyền với JWT và Spring Security
- Phát triển ứng dụng web với Next.js và React
- Quản lý state với Redux Toolkit
- Làm việc với cơ sở dữ liệu MySQL và JPA
- Upload và quản lý file
- Xây dựng giao diện responsive với Bootstrap và SCSS
- Xử lý form và validation
- Pagination và filtering
- Docker và Docker Compose

### 8.5. Hạn chế và khó khăn

**Hạn chế:**
- Chưa tích hợp thanh toán trực tuyến
- Chưa có hệ thống đánh giá và nhận xét sản phẩm đầy đủ
- Chưa có tính năng chat hỗ trợ khách hàng
- Chưa tối ưu SEO cho trang web
- Chưa có mobile app

**Khó khăn gặp phải:**
- Xử lý authentication và authorization với JWT
- Quản lý state phức tạp với Redux
- Upload và quản lý file trên server
- Tối ưu hiệu suất cho ứng dụng web
- Xử lý lỗi và exception handling

**Giải pháp đã áp dụng:**
- Nghiên cứu tài liệu và thực hành với JWT authentication
- Sử dụng Redux Toolkit để đơn giản hóa quản lý state
- Tích hợp Spring Boot MultipartFile để xử lý upload
- Sử dụng Next.js Image component để tối ưu hình ảnh
- Xây dựng Global Exception Handler để xử lý lỗi tập trung

---

**Tác giả**: [Tên sinh viên]  
**Lớp**: [Lớp]  
**Mã sinh viên**: [Mã sinh viên]  
**Ngày hoàn thành**: [Ngày/Tháng/Năm]

---

## TÀI LIỆU THAM KHẢO

1. Spring Boot Documentation. https://spring.io/projects/spring-boot
2. Next.js Documentation. https://nextjs.org/docs
3. React Documentation. https://react.dev/
4. Spring Security Reference. https://docs.spring.io/spring-security/reference/
5. JWT.io - Introduction to JSON Web Tokens. https://jwt.io/introduction
6. MySQL Documentation. https://dev.mysql.com/doc/
7. TypeScript Handbook. https://www.typescriptlang.org/docs/
8. Redux Toolkit Documentation. https://redux-toolkit.js.org/
9. Bootstrap Documentation. https://getbootstrap.com/docs/
10. Ant Design Documentation. https://ant.design/

---

*Tài liệu này mô tả chi tiết về hệ thống thương mại điện tử Muji, bao gồm cả phần Backend và Frontend, phục vụ cho đồ án tốt nghiệp.*
