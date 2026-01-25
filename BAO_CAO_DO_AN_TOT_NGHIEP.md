# BÁO CÁO ĐỒ ÁN TỐT NGHIỆP
## HỆ THỐNG THƯƠNG MẠI ĐIỆN TỬ MUJI

---

## MỤC LỤC

1. [GIỚI THIỆU DỰ ÁN](#1-giới-thiệu-dự-án)
2. [PHẦN BACKEND](#2-phần-backend)
3. [PHẦN FRONTEND](#3-phần-frontend)
4. [KIẾN TRÚC HỆ THỐNG](#4-kiến-trúc-hệ-thống)
5. [CÔNG NGHỆ SỬ DỤNG](#5-công-nghệ-sử-dụng)
6. [CHỨC NĂNG HỆ THỐNG](#6-chức-năng-hệ-thống)
7. [KẾT LUẬN](#7-kết-luận)

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

## 2. PHẦN BACKEND

### 2.1. Tổng quan Backend
Backend được xây dựng bằng Spring Boot 3.2.4, sử dụng Java 17, cung cấp RESTful API cho ứng dụng Frontend. Hệ thống sử dụng kiến trúc 3 lớp (Controller - Service - Repository) để đảm bảo tính modular và dễ bảo trì.

### 2.2. Công nghệ Backend

#### 2.2.1. Framework và Thư viện chính
- **Spring Boot 3.2.4**: Framework chính cho việc xây dựng ứng dụng
- **Spring Security**: Xử lý xác thực và phân quyền
- **Spring Data JPA**: Truy cập và quản lý dữ liệu
- **OAuth2 Resource Server**: Xác thực JWT token
- **MySQL Connector**: Kết nối với cơ sở dữ liệu MySQL
- **Lombok**: Giảm boilerplate code
- **Spring Filter**: Hỗ trợ filtering và pagination

#### 2.2.2. Cơ sở dữ liệu
- **MySQL**: Hệ quản trị cơ sở dữ liệu quan hệ
- **JPA/Hibernate**: ORM framework

### 2.3. Kiến trúc Backend

#### 2.3.1. Cấu trúc thư mục
```
src/main/java/vn/hoidanit/jobhunter/
├── config/          # Cấu hình Security, JWT
├── controller/      # REST Controllers
├── domain/          # Entity models và DTOs
├── repository/      # Data access layer
├── service/         # Business logic layer
└── util/            # Utilities và helpers
```

#### 2.3.2. Các Controller chính
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

### 2.4. Bảo mật và Xác thực

#### 2.4.1. JWT Authentication
- Sử dụng JWT (JSON Web Token) cho xác thực người dùng
- Access Token: Có thời hạn 100 ngày
- Refresh Token: Lưu trữ trong cookie httpOnly, secure
- Token được mã hóa bằng Base64 secret key

#### 2.4.2. Spring Security Configuration
- Cấu hình OAuth2 Resource Server
- Phân quyền theo role (USER, ADMIN)
- CORS được cấu hình để cho phép Frontend kết nối
- Custom Authentication Entry Point xử lý lỗi 401

#### 2.4.3. Password Encryption
- Sử dụng BCryptPasswordEncoder để mã hóa mật khẩu
- Mật khẩu được hash trước khi lưu vào database

### 2.5. Các Entity chính

#### 2.5.1. User
- Thông tin người dùng: email, password, firstName, lastName
- Địa chỉ: address1, address2, address3, address4
- Thông tin cá nhân: phone, birthday, gender, avatarUrl
- Xác thực: refreshToken
- Phân quyền: role (USER, ADMIN)

#### 2.5.2. Product
- Thông tin sản phẩm: name, description
- Giá: minPrice, maxPrice
- Tồn kho: stockQuantity
- Hình ảnh: imageUrl
- Liên kết: categoryId

#### 2.5.3. Category
- Danh mục sản phẩm với cấu trúc phân cấp
- MainCategory → SubCategory → Category → Product

#### 2.5.4. Order & OrderItem
- Quản lý đơn hàng và chi tiết đơn hàng
- Thông tin: userId, orderDate, totalAmount, status

#### 2.5.5. Cart & CartItem
- Quản lý giỏ hàng của người dùng
- Lưu trữ sản phẩm và số lượng

#### 2.5.6. Wishlist
- Danh sách sản phẩm yêu thích của người dùng

#### 2.5.7. Review
- Đánh giá và nhận xét về sản phẩm

### 2.6. API Endpoints chính

#### 2.6.1. Authentication
- `POST /api/v1/auth/login`: Đăng nhập
- `POST /api/v1/auth/logout`: Đăng xuất
- `GET /api/v1/auth/refresh`: Làm mới token
- `GET /api/v1/auth/account`: Lấy thông tin tài khoản hiện tại

#### 2.6.2. User Management
- `POST /api/v1/users/register`: Đăng ký tài khoản mới
- `GET /api/v1/users`: Lấy danh sách người dùng (Admin)
- `GET /api/v1/users/{id}`: Lấy thông tin người dùng theo ID
- `GET /api/v1/users/me`: Lấy thông tin người dùng hiện tại
- `PUT /api/v1/users/me`: Cập nhật thông tin người dùng hiện tại
- `PUT /api/v1/users/{id}`: Cập nhật người dùng (Admin)
- `DELETE /api/v1/users/{id}`: Xóa người dùng (Admin)

#### 2.6.3. Product Management
- `GET /api/v1/products`: Lấy danh sách sản phẩm (có phân trang, filter)
- `GET /api/v1/products/{id}`: Lấy chi tiết sản phẩm
- `GET /api/v1/products/sub-category/{subCategoryId}`: Lấy sản phẩm theo sub-category
- `GET /api/v1/products/category/{categoryId}`: Lấy sản phẩm theo category
- `POST /api/v1/products`: Tạo sản phẩm mới (có upload hình ảnh)
- `PUT /api/v1/products/{id}`: Cập nhật sản phẩm
- `DELETE /api/v1/products/{id}`: Xóa sản phẩm

#### 2.6.4. Category Management
- `GET /api/v1/main-categories`: Lấy danh sách danh mục chính
- `GET /api/v1/sub-categories`: Lấy danh sách danh mục phụ
- `GET /api/v1/categories/{id}`: Lấy thông tin category
- `GET /api/v1/categories/sub-category/{id}`: Lấy categories theo sub-category

#### 2.6.5. Cart Management
- `GET /api/v1/cart`: Lấy giỏ hàng của người dùng hiện tại
- `POST /api/v1/cart`: Thêm sản phẩm vào giỏ hàng
- `PUT /api/v1/cart/{cartItemId}`: Cập nhật số lượng sản phẩm
- `DELETE /api/v1/cart/{cartItemId}`: Xóa sản phẩm khỏi giỏ hàng
- `GET /api/v1/cart/total-quantity`: Lấy tổng số lượng sản phẩm trong giỏ

#### 2.6.6. Order Management
- `POST /api/v1/orders`: Tạo đơn hàng mới
- `GET /api/v1/orders`: Lấy danh sách đơn hàng của người dùng
- `GET /api/v1/orders?page=&size=`: Lấy tất cả đơn hàng với phân trang (Admin)
- `GET /api/v1/orders/{id}`: Lấy chi tiết đơn hàng

#### 2.6.7. Wishlist Management
- `GET /api/v1/wishlist`: Lấy danh sách yêu thích
- `POST /api/v1/wishlist`: Thêm sản phẩm vào wishlist
- `DELETE /api/v1/wishlist/{productId}`: Xóa sản phẩm khỏi wishlist

### 2.7. Xử lý File Upload
- Hỗ trợ upload hình ảnh sản phẩm và avatar người dùng
- Giới hạn kích thước file: 50MB
- Lưu trữ file trong thư mục `/uploads/images/`
- Phân loại theo: products, categories, users

### 2.8. Pagination và Filtering
- Sử dụng Spring Data JPA Specification
- Hỗ trợ filtering động với Spring Filter
- Pagination với Pageable interface
- Trang bắt đầu từ 1 (one-indexed)

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

### 3.4. State Management

#### 3.4.1. Redux Store
Sử dụng Redux Toolkit với các slices:
- **authSlice**: Quản lý trạng thái xác thực người dùng
- **cartSlice**: Quản lý giỏ hàng
- **wishlistSlice**: Quản lý danh sách yêu thích

#### 3.4.2. API Integration
- Axios được cấu hình với interceptors
- Tự động thêm JWT token vào headers
- Xử lý refresh token khi access token hết hạn
- Error handling tập trung

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

### 3.9. Authentication Flow
1. Người dùng đăng nhập → Nhận access token và refresh token
2. Access token lưu trong Redux store
3. Refresh token lưu trong httpOnly cookie
4. Axios interceptor tự động thêm token vào requests
5. Khi token hết hạn → Tự động refresh token
6. Protected routes kiểm tra authentication

### 3.10. Form Validation
- Sử dụng React Hook Form với Yup schema
- Validation real-time
- Error messages hiển thị rõ ràng
- Email validation, password strength

---

## 4. KIẾN TRÚC HỆ THỐNG

### 4.1. Kiến trúc tổng thể
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

### 4.2. Luồng xử lý request
1. User tương tác với Frontend
2. Frontend gửi HTTP request đến Backend API
3. Backend xử lý request (authentication, validation, business logic)
4. Backend truy vấn/ cập nhật database
5. Backend trả về response (JSON)
6. Frontend cập nhật UI dựa trên response

### 4.3. Security Flow
1. User đăng nhập → Backend xác thực → Trả về JWT token
2. Frontend lưu token → Gửi kèm trong header mỗi request
3. Backend verify token → Xác thực quyền truy cập
4. Refresh token tự động khi access token hết hạn

---

## 5. CÔNG NGHỆ SỬ DỤNG

### 5.1. Backend Stack
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

### 5.2. Frontend Stack
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

### 5.3. Development Tools
- **Docker**: Containerization
- **Docker Compose**: Multi-container orchestration
- **Git**: Version control
- **Postman/Insomnia**: API testing

---

## 6. CHỨC NĂNG HỆ THỐNG

### 6.1. Chức năng người dùng (User)

#### 6.1.1. Đăng ký và Xác thực
- Đăng ký tài khoản mới với email và mật khẩu
- Đăng nhập với email và mật khẩu
- Đăng xuất
- Làm mới token tự động

#### 6.1.2. Quản lý tài khoản
- Xem thông tin cá nhân
- Cập nhật thông tin (tên, địa chỉ, số điện thoại)
- Upload và thay đổi avatar
- Quản lý địa chỉ giao hàng

#### 6.1.3. Mua sắm
- Duyệt danh mục sản phẩm
- Xem chi tiết sản phẩm
- Tìm kiếm sản phẩm
- Lọc sản phẩm theo danh mục, giá
- Thêm sản phẩm vào giỏ hàng
- Quản lý giỏ hàng (thêm, sửa, xóa)
- Đặt hàng
- Xem lịch sử đơn hàng
- Xem chi tiết đơn hàng

#### 6.1.4. Danh sách yêu thích
- Thêm sản phẩm vào wishlist
- Xem danh sách yêu thích
- Xóa sản phẩm khỏi wishlist

### 6.2. Chức năng quản trị viên (Admin)

#### 6.2.1. Dashboard
- Tổng quan thống kê (số người dùng, sản phẩm, đơn hàng)
- Biểu đồ doanh thu theo thời gian
- Biểu đồ đơn hàng theo trạng thái
- Thống kê sản phẩm bán chạy

#### 6.2.2. Quản lý người dùng
- Xem danh sách người dùng (có phân trang, tìm kiếm)
- Xem chi tiết người dùng
- Cập nhật thông tin người dùng
- Xóa người dùng
- Phân quyền (USER, ADMIN)

#### 6.2.3. Quản lý sản phẩm
- Xem danh sách sản phẩm (có phân trang, filter)
- Tạo sản phẩm mới
- Upload hình ảnh sản phẩm
- Cập nhật thông tin sản phẩm
- Xóa sản phẩm
- Quản lý tồn kho

#### 6.2.4. Quản lý danh mục
- Quản lý danh mục chính (Main Category)
- Quản lý danh mục phụ (Sub Category)
- Quản lý danh mục (Category)
- Upload hình ảnh danh mục

#### 6.2.5. Quản lý đơn hàng
- Xem tất cả đơn hàng (có phân trang, filter)
- Xem chi tiết đơn hàng
- Cập nhật trạng thái đơn hàng
- Thống kê đơn hàng

### 6.3. Chức năng hệ thống

#### 6.3.1. Bảo mật
- Xác thực JWT token
- Mã hóa mật khẩu bằng BCrypt
- Phân quyền theo role
- CORS protection
- XSS protection
- SQL injection prevention (JPA)

#### 6.3.2. Upload và quản lý file
- Upload hình ảnh sản phẩm
- Upload avatar người dùng
- Upload hình ảnh danh mục
- Giới hạn kích thước file
- Validate file type

#### 6.3.3. Pagination và Filtering
- Phân trang cho danh sách dữ liệu
- Filter động với nhiều tiêu chí
- Sắp xếp dữ liệu

---

## 7. KẾT LUẬN

### 7.1. Tổng kết
Dự án đã xây dựng thành công một hệ thống thương mại điện tử hoàn chỉnh với đầy đủ các chức năng cơ bản và nâng cao. Hệ thống được phát triển với các công nghệ hiện đại, đảm bảo tính bảo mật, hiệu suất và khả năng mở rộng.

### 7.2. Điểm mạnh
- Kiến trúc tách biệt rõ ràng giữa Frontend và Backend
- Sử dụng các công nghệ và framework hiện đại, phổ biến
- Bảo mật tốt với JWT authentication và Spring Security
- Giao diện người dùng thân thiện, responsive
- Code được tổ chức tốt, dễ bảo trì và mở rộng
- Hệ thống quản trị mạnh mẽ với đầy đủ chức năng CRUD

### 7.3. Hướng phát triển
- Tích hợp thanh toán trực tuyến (VNPay, PayPal, Stripe)
- Tích hợp đánh giá và nhận xét sản phẩm
- Hệ thống khuyến mãi và giảm giá
- Tích hợp chat hỗ trợ khách hàng
- Tối ưu SEO cho trang web
- Tích hợp social login (Google, Facebook)
- Hệ thống thông báo real-time
- Mobile app (React Native)

### 7.4. Kinh nghiệm đạt được
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

---

**Tác giả**: [Tên sinh viên]  
**Lớp**: [Lớp]  
**Mã sinh viên**: [Mã sinh viên]  
**Ngày hoàn thành**: [Ngày/Tháng/Năm]

---

*Tài liệu này mô tả chi tiết về hệ thống thương mại điện tử Muji, bao gồm cả phần Backend và Frontend, phục vụ cho đồ án tốt nghiệp.*
