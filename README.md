# MUJI Backend - Hướng dẫn cài đặt và chạy

Dự án Backend sử dụng Java Spring Boot 3.2.4 với MySQL database.

## Yêu cầu hệ thống

- **Docker**: Docker và Docker Compose đã được cài đặt
- **Docker Compose**: Phiên bản 2.0 hoặc cao hơn

## Cài đặt và chạy dự án

### Chạy với Docker Compose

Docker Compose sẽ tự động setup MySQL database và Spring Boot application.

1. **Kiểm tra Docker đã được cài đặt:**
```bash
docker --version
docker-compose --version
```

2. **Di chuyển vào thư mục backend:**
```bash
cd muji-backend-aws-cicd
```

3. **Chạy Docker Compose:**
```bash
docker-compose up -d
```

Lệnh này sẽ:
- Tạo và khởi động MySQL container trên port 3307
- Tạo database `jobhunter`
- Tự động import dữ liệu categories và admin user
- Build và chạy Spring Boot application trên port 8080

4. **Kiểm tra logs:**
```bash
# Xem logs của tất cả services
docker-compose logs -f

# Xem logs của Spring Boot app
docker-compose logs -f app

# Xem logs của MySQL
docker-compose logs -f mysql
```

5. **Dừng services:**
```bash
docker-compose down
```

6. **Dừng và xóa volumes (xóa database):**
```bash
docker-compose down -v
```

### Cấu hình OpenAI API (tùy chọn)

Nếu bạn sử dụng tính năng chatbot, cần cấu hình OpenAI API key:

1. Tạo file `.env` trong thư mục root của backend
2. Thêm API key:
```env
OPENAI_API_KEY=YOUR_ACTUAL_API_KEY_HERE
```

3. Docker Compose sẽ tự động load biến môi trường từ file `.env`

## Cấu trúc dự án

```
muji-backend-aws-cicd/
├── src/
│   ├── main/
│   │   ├── java/                 # Source code Java
│   │   └── resources/
│   │       ├── application.properties      # Cấu hình chính
│   │       └── application-local.properties # Cấu hình local (gitignored)
│   └── test/                     # Test code
├── database/
│   └── init/                     # SQL scripts để init database
├── uploads/
│   └── images/                   # Thư mục lưu ảnh upload
├── build.gradle.kts              # Gradle build configuration
├── docker-compose.yml            # Docker Compose configuration
└── Dockerfile                    # Dockerfile để build image
```

## Cấu hình quan trọng

### Database Configuration

File: `src/main/resources/application.properties`

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/jobhunter
spring.datasource.username=root
spring.datasource.password=password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### JWT Configuration

JWT secret và expiration time được cấu hình trong `application.properties`:

```properties
jwt.base64-secret=...
jwt.access-token-validity-in-seconds=8640000
jwt.refresh-token-validity-in-seconds=8640000
```

### File Upload Configuration

```properties
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

### Email Configuration

Cấu hình SMTP để gửi email (hiện tại dùng Gmail):

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Lưu ý:** Với Gmail, bạn cần sử dụng App Password thay vì mật khẩu thông thường.

## API Endpoints

### Authentication
- `POST /api/v1/auth/login` - Đăng nhập
- `POST /api/v1/auth/refresh` - Refresh token

### Users
- `POST /api/v1/users/register` - Đăng ký user mới
- `POST /api/v1/users/verify-email` - Xác thực email
- `GET /api/v1/users/profile` - Lấy thông tin profile

### Categories
- `GET /api/v1/main-categories` - Lấy danh sách main categories
- `GET /api/v1/sub-categories` - Lấy danh sách sub categories

### Products
- `GET /api/v1/products` - Lấy danh sách sản phẩm
- `GET /api/v1/products/{id}` - Lấy chi tiết sản phẩm

### Chatbot
- `POST /api/v1/chatbot/message` - Gửi tin nhắn đến chatbot

## Tài khoản mặc định

Sau khi import dữ liệu, bạn có thể đăng nhập với tài khoản admin:

- **Email**: admin@gmail.com
- **Password**: (cần được set trong database, xem file `database/init/04-init-admin-user.sql`)

**Lưu ý:** Password trong SQL file là placeholder. Bạn cần tạo BCrypt hash và update vào database.

## Troubleshooting

### Lỗi khi chạy Docker Compose

1. Kiểm tra Docker đã chạy:
```bash
docker ps
```

2. Xem logs chi tiết:
```bash
docker-compose logs app
```

3. Rebuild containers:
```bash
docker-compose down
docker-compose build --no-cache
docker-compose up -d
```

## Development

### Hot Reload

Khi chạy với Docker Compose, hot reload đã được bật tự động. Chỉ cần save file và Spring Boot sẽ tự động reload.

## Production Deployment

### Docker Build

```bash
docker build -t muji-backend .
docker run -p 8080:8080 muji-backend
```
