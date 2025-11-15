# Hướng dẫn sử dụng AmazonMQ

Tài liệu này mô tả cách sử dụng tính năng AmazonMQ đã được tích hợp vào ứng dụng.

## Tổng quan

AmazonMQ là dịch vụ message broker được quản lý bởi AWS, hỗ trợ giao thức ActiveMQ. Tính năng này cho phép ứng dụng gửi và nhận messages thông qua message queue.

## Cấu hình

### 1. Cấu hình trong application.properties

Các cấu hình AmazonMQ được định nghĩa trong `application.properties`:

```properties
# AmazonMQ broker URL
spring.activemq.broker-url=${AMAZONMQ_BROKER_URL:ssl://your-broker-id.mq.ap-southeast-1.amazonaws.com:61617}
spring.activemq.user=${AMAZONMQ_USER:}
spring.activemq.password=${AMAZONMQ_PASSWORD:}
spring.activemq.pool.enabled=true
spring.activemq.pool.max-connections=10
amazonmq.queue.name=${AMAZONMQ_QUEUE_NAME:jobhunter-queue}
```

### 2. Biến môi trường

Khi chạy ứng dụng, bạn có thể override các giá trị mặc định bằng biến môi trường:

- `AMAZONMQ_BROKER_URL`: URL của AmazonMQ broker (ví dụ: `ssl://broker-id.mq.ap-southeast-1.amazonaws.com:61617`)
- `AMAZONMQ_USER`: Username để kết nối với broker
- `AMAZONMQ_PASSWORD`: Password để kết nối với broker
- `AMAZONMQ_QUEUE_NAME`: Tên queue (mặc định: `jobhunter-queue`)

### 3. Tạo AmazonMQ Broker trên AWS

Bạn cần tạo AmazonMQ broker trực tiếp trên AWS Console hoặc sử dụng AWS CLI:

**Qua AWS Console:**
1. Đăng nhập vào AWS Console
2. Điều hướng đến Amazon MQ service
3. Tạo broker mới với engine type là ActiveMQ
4. Lưu lại broker URL, username và password

**Qua AWS CLI:**
```bash
aws mq create-broker \
  --broker-name muji-broker \
  --engine-type ACTIVEMQ \
  --engine-version 5.17.6 \
  --host-instance-type mq.t3.micro \
  --deployment-mode SINGLE_INSTANCE \
  --users Username=admin,Password=your-secure-password
```

Sau khi tạo broker, bạn sẽ nhận được broker URL dạng: `ssl://broker-id.mq.ap-southeast-1.amazonaws.com:61617`

## Sử dụng API

### 1. Gửi text message

**Endpoint**: `POST /api/v1/messages/send`

**Request Body**:
```json
{
  "message": "Hello from AmazonMQ!"
}
```

**Response**:
```json
{
  "statusCode": 200,
  "message": "Message sent successfully to queue",
  "data": {
    "success": true,
    "message": "Message sent successfully to queue",
    "content": "Hello from AmazonMQ!"
  }
}
```

### 2. Gửi object message

**Endpoint**: `POST /api/v1/messages/send-object`

**Request Body**:
```json
{
  "orderId": "12345",
  "customerId": "67890",
  "items": [
    {
      "productId": "prod-001",
      "quantity": 2,
      "price": 29.99
    }
  ],
  "total": 59.98
}
```

**Response**:
```json
{
  "statusCode": 200,
  "message": "Object message sent successfully to queue",
  "data": {
    "success": true,
    "message": "Object message sent successfully to queue",
    "payload": {
      "orderId": "12345",
      "customerId": "67890",
      "items": [...],
      "total": 59.98
    }
  }
}
```

### 3. Health check

**Endpoint**: `GET /api/v1/messages/health`

**Response**:
```json
{
  "statusCode": 200,
  "message": "Message queue service is running",
  "data": {
    "status": "healthy",
    "service": "AmazonMQ Message Service",
    "message": "Message queue service is running"
  }
}
```

## Xử lý Messages

### Message Listener

Ứng dụng tự động lắng nghe messages từ queue thông qua `MessageService`. Khi một message được gửi đến queue, các method sau sẽ được gọi tự động:

1. `receiveMessage(String message)`: Xử lý text messages
2. `receiveObjectMessage(Object payload)`: Xử lý object messages

Bạn có thể tùy chỉnh logic xử lý trong các method `processMessage()` và `processObjectMessage()` trong `MessageService.java`.

## Cấu trúc Code

### 1. JmsConfig.java

File cấu hình JMS để kết nối với AmazonMQ:
- `connectionFactory()`: Tạo connection factory để kết nối với broker
- `messageConverter()`: Converter để serialize/deserialize messages
- `jmsTemplate()`: Template để gửi messages
- `jmsListenerContainerFactory()`: Factory để nhận messages

### 2. MessageService.java

Service chứa logic gửi và nhận messages:
- `sendMessage(String message)`: Gửi text message
- `sendObjectMessage(Object payload)`: Gửi object message
- `receiveMessage(String message)`: Nhận text message (tự động)
- `receiveObjectMessage(Object payload)`: Nhận object message (tự động)

### 3. MessageController.java

REST controller cung cấp API endpoints để test và sử dụng message queue.

## Development

### Chạy local với ActiveMQ

Để test local mà không cần AmazonMQ, bạn có thể sử dụng ActiveMQ local:

1. Cài đặt ActiveMQ local hoặc sử dụng Docker:
```bash
docker run -d -p 61616:61616 -p 8161:8161 apache/activemq-artemis:latest
```

2. Cập nhật `application.properties`:
```properties
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=
spring.activemq.password=
```

### Testing

Bạn có thể test tính năng bằng cách:

1. Khởi động ứng dụng
2. Gửi message qua API:
```bash
curl -X POST http://localhost:8080/api/v1/messages/send \
  -H "Content-Type: application/json" \
  -d '{"message": "Test message"}'
```

3. Kiểm tra logs để xem message đã được nhận và xử lý.

## Troubleshooting

### Lỗi kết nối

Nếu gặp lỗi kết nối với AmazonMQ:

1. Kiểm tra broker URL có đúng không
2. Kiểm tra username và password
3. Kiểm tra security group cho phép traffic từ VPC
4. Kiểm tra network connectivity giữa ứng dụng và broker

### Message không được nhận

Nếu message không được nhận:

1. Kiểm tra queue name có đúng không
2. Kiểm tra `@JmsListener` annotation có đúng destination không
3. Kiểm tra logs để xem có lỗi gì không

## Tài liệu tham khảo

- [AmazonMQ Documentation](https://docs.aws.amazon.com/amazon-mq/)
- [Spring JMS Documentation](https://docs.spring.io/spring-framework/reference/integration/jms.html)
- [ActiveMQ Documentation](https://activemq.apache.org/)

