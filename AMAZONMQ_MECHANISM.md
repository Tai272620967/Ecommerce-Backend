# Cơ chế hoạt động của MessageController với AmazonMQ

Tài liệu này giải thích chi tiết cách MessageController và các thành phần liên quan hoạt động với AmazonMQ.

## Tổng quan kiến trúc

```
Client (HTTP Request)
    ↓
MessageController (REST API)
    ↓
MessageService (Business Logic)
    ↓
JmsTemplate (Spring JMS)
    ↓
ConnectionFactory (ActiveMQ)
    ↓
AmazonMQ Broker (Message Queue)
    ↓
@JmsListener (Message Consumer)
    ↓
MessageService.processMessage()
```

## Các thành phần chính

### 1. JmsConfig - Cấu hình kết nối

**Vai trò**: Thiết lập kết nối với AmazonMQ broker và cấu hình các bean cần thiết.

**Các Bean được tạo**:

#### a) ConnectionFactory
```java
@Bean
public ConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(brokerUrl); // ssl://broker-id.mq.ap-southeast-1.amazonaws.com:61617
    connectionFactory.setUserName(username);
    connectionFactory.setPassword(password);
    return connectionFactory;
}
```

**Chức năng**:
- Tạo factory để quản lý kết nối với AmazonMQ broker
- Sử dụng ActiveMQ protocol (AmazonMQ hỗ trợ ActiveMQ)
- Xác thực bằng username/password
- Hỗ trợ SSL connection

#### b) MessageConverter
```java
@Bean
public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
}
```

**Chức năng**:
- Chuyển đổi Java objects thành JSON khi gửi
- Chuyển đổi JSON thành Java objects khi nhận
- Cho phép gửi/nhận các object phức tạp thay vì chỉ text

#### c) JmsTemplate
```java
@Bean
public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory);
    template.setMessageConverter(messageConverter);
    template.setDeliveryPersistent(true); // Đảm bảo message được lưu trữ
    return template;
}
```

**Chức năng**:
- Template để gửi messages một cách dễ dàng
- Tự động quản lý connection, session
- Hỗ trợ persistent delivery (message không bị mất khi broker restart)

#### d) JmsListenerContainerFactory
```java
@Bean
public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(...) {
    factory.setConcurrency("1-10"); // Cho phép 1-10 consumer đồng thời
    factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    return factory;
}
```

**Chức năng**:
- Tạo container để lắng nghe messages từ queue
- Hỗ trợ concurrent consumers (xử lý nhiều messages cùng lúc)
- Tự động acknowledge messages sau khi xử lý thành công

### 2. MessageService - Xử lý business logic

**Vai trò**: Cung cấp các method để gửi và nhận messages.

#### a) Gửi Messages (Producer)

**Method: `sendMessage(String message)`**
```java
public void sendMessage(String message) {
    jmsTemplate.convertAndSend(queueName, message);
}
```

**Luồng hoạt động**:
1. Nhận message từ controller
2. Sử dụng `JmsTemplate.convertAndSend()` để gửi
3. `JmsTemplate` tự động:
   - Lấy connection từ ConnectionFactory
   - Tạo session
   - Convert message thành JMS message (nếu cần)
   - Gửi đến queue trên AmazonMQ broker
   - Đóng connection

**Method: `sendObjectMessage(Object payload)`**
```java
public void sendObjectMessage(Object payload) {
    jmsTemplate.convertAndSend(queueName, payload);
}
```

**Khác biệt**:
- Gửi object thay vì String
- `MessageConverter` tự động serialize object thành JSON
- AmazonMQ lưu trữ dưới dạng JSON text

#### b) Nhận Messages (Consumer)

**Method: `receiveMessage(String message)`**
```java
@JmsListener(destination = "${amazonmq.queue.name:jobhunter-queue}")
public void receiveMessage(String message) {
    logger.info("Received message: {}", message);
    processMessage(message);
}
```

**Cơ chế hoạt động**:
1. `@JmsListener` annotation đánh dấu method là message listener
2. Spring tự động tạo listener container khi ứng dụng khởi động
3. Listener container kết nối với AmazonMQ broker
4. Khi có message mới trong queue:
   - Container nhận message từ broker
   - Deserialize message (nếu cần)
   - Gọi method `receiveMessage()` với message content
   - Method xử lý message
   - Container tự động acknowledge message

**Lưu ý quan trọng**:
- Method này được gọi **tự động** khi có message mới
- Không cần gọi trực tiếp từ code
- Có thể có nhiều consumer cùng lắng nghe một queue (load balancing)

### 3. MessageController - REST API Endpoint

**Vai trò**: Cung cấp HTTP endpoints để client gửi messages.

#### Endpoint 1: POST /api/v1/messages/send

**Luồng hoạt động**:

```
1. Client gửi HTTP POST request:
   POST /api/v1/messages/send
   Body: {"message": "Hello AmazonMQ"}

2. MessageController.sendMessage() nhận request:
   - Validate message không rỗng
   - Gọi messageService.sendMessage(message)

3. MessageService.sendMessage():
   - Sử dụng jmsTemplate.convertAndSend()
   - Message được gửi đến queue "jobhunter-queue" trên AmazonMQ

4. Controller trả về response:
   {
     "success": true,
     "message": "Message sent successfully",
     "content": "Hello AmazonMQ"
   }
```

**Điểm quan trọng**:
- Request/Response là **synchronous** (client chờ response)
- Message gửi đến queue là **asynchronous** (không chờ xử lý)
- Controller chỉ xác nhận message đã được gửi vào queue, không đợi message được xử lý

#### Endpoint 2: POST /api/v1/messages/send-object

**Luồng hoạt động tương tự**, nhưng:
- Nhận object phức tạp (Map, JSON)
- Object được serialize thành JSON trước khi gửi
- Có thể gửi bất kỳ object nào (DTO, Entity, Map, etc.)

#### Endpoint 3: GET /api/v1/messages/health

**Chức năng**: Kiểm tra service có hoạt động không (không thực sự test kết nối AmazonMQ)

## Luồng dữ liệu hoàn chỉnh

### Scenario: Client gửi message và message được xử lý

```
┌─────────┐
│ Client  │
└────┬────┘
     │ 1. HTTP POST /api/v1/messages/send
     │    {"message": "Process order 123"}
     ↓
┌─────────────────────┐
│ MessageController   │
│ .sendMessage()      │
└────┬────────────────┘
     │ 2. messageService.sendMessage("Process order 123")
     ↓
┌─────────────────────┐
│ MessageService      │
│ .sendMessage()      │
└────┬────────────────┘
     │ 3. jmsTemplate.convertAndSend("jobhunter-queue", message)
     ↓
┌─────────────────────┐
│ JmsTemplate         │
│ - Get connection    │
│ - Create session   │
│ - Convert message  │
│ - Send to queue    │
└────┬────────────────┘
     │ 4. JMS Message
     ↓
┌─────────────────────┐
│ AmazonMQ Broker     │
│ Queue: jobhunter-  │
│ queue              │
│ [Message stored]   │
└────┬────────────────┘
     │ 5. Message available in queue
     ↓
┌─────────────────────┐
│ @JmsListener        │
│ Container           │
│ - Poll queue       │
│ - Receive message  │
└────┬────────────────┘
     │ 6. Invoke receiveMessage()
     ↓
┌─────────────────────┐
│ MessageService      │
│ .receiveMessage()   │
│ .processMessage()   │
└─────────────────────┘
     │ 7. Business logic executed
     │ (Send email, update DB, etc.)
```

## Các khái niệm quan trọng

### 1. Queue vs Topic

**Queue (Point-to-Point)**:
- Mỗi message chỉ được một consumer nhận
- Nếu có nhiều consumer, messages được phân phối (load balancing)
- Sử dụng trong code hiện tại: `jobhunter-queue`

**Topic (Pub/Sub)**:
- Mỗi message được gửi đến TẤT CẢ subscribers
- Nhiều consumers nhận cùng một message
- Không sử dụng trong code hiện tại

### 2. Message Persistence

**Persistent Messages**:
- Được lưu trữ trên disk
- Không bị mất khi broker restart
- Code hiện tại: `setDeliveryPersistent(true)`

**Non-Persistent Messages**:
- Chỉ lưu trong memory
- Mất khi broker restart
- Nhanh hơn nhưng không đảm bảo

### 3. Message Acknowledgment

**AUTO_ACKNOWLEDGE** (code hiện tại):
- Tự động acknowledge sau khi method listener hoàn thành
- Nếu method throw exception, message có thể bị mất

**CLIENT_ACKNOWLEDGE**:
- Phải manually acknowledge
- Có thể retry nếu xử lý lỗi

### 4. Concurrent Consumers

**Cấu hình**: `factory.setConcurrency("1-10")`

**Ý nghĩa**:
- Tối thiểu 1 consumer
- Tối đa 10 consumers
- Spring tự động scale dựa trên số lượng messages trong queue
- Xử lý nhiều messages song song

## Ví dụ thực tế

### Ví dụ 1: Gửi notification email

```java
// Controller nhận request
POST /api/v1/messages/send-object
{
  "type": "email",
  "to": "user@example.com",
  "subject": "Order confirmed",
  "orderId": "12345"
}

// Message được gửi vào queue
// Consumer tự động nhận và xử lý
@JmsListener(destination = "jobhunter-queue")
public void receiveObjectMessage(Object payload) {
    Map<String, Object> data = (Map<String, Object>) payload;
    if ("email".equals(data.get("type"))) {
        emailService.sendEmail(...);
    }
}
```

### Ví dụ 2: Xử lý order asynchronously

```java
// User tạo order
POST /api/v1/orders
{
  "items": [...],
  "total": 100.00
}

// Controller tạo order trong DB
Order order = orderService.createOrder(orderDTO);

// Gửi message để xử lý background tasks
messageService.sendObjectMessage(Map.of(
    "action", "process-order",
    "orderId", order.getId(),
    "tasks", ["update-inventory", "send-confirmation", "calculate-commission"]
));

// Trả về response ngay lập tức
return ResponseEntity.ok(order);

// Background: Consumer xử lý các tasks
@JmsListener(destination = "jobhunter-queue")
public void receiveObjectMessage(Object payload) {
    // Process order tasks asynchronously
}
```

## Lợi ích của kiến trúc này

1. **Decoupling**: Controller không cần biết cách xử lý message
2. **Scalability**: Có thể scale consumers độc lập
3. **Reliability**: Messages được lưu trữ, không bị mất
4. **Performance**: Xử lý asynchronous, không block HTTP request
5. **Flexibility**: Dễ dàng thêm/xóa consumers

## Troubleshooting

### Message không được gửi
- Kiểm tra kết nối với AmazonMQ broker
- Kiểm tra queue name có đúng không
- Kiểm tra credentials (username/password)

### Message không được nhận
- Kiểm tra `@JmsListener` annotation
- Kiểm tra queue name trong listener có khớp không
- Kiểm tra logs để xem có lỗi gì không

### Message bị duplicate
- Có thể do nhiều consumers cùng nhận
- Hoặc do retry mechanism
- Sử dụng idempotent processing

## Kết luận

MessageController với AmazonMQ tạo ra một kiến trúc **asynchronous messaging** mạnh mẽ:
- **Producer** (Controller → Service → JmsTemplate) gửi messages
- **Consumer** (@JmsListener) nhận và xử lý messages tự động
- **Broker** (AmazonMQ) quản lý queue và đảm bảo delivery

Kiến trúc này phù hợp cho các tác vụ:
- Background processing
- Event-driven architecture
- Microservices communication
- Task queuing

