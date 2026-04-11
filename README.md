# Chat App Backend

**Một nền tảng chat realtime hiện đại được xây dựng bằng Spring Boot, WebSocket, và MySQL.**

Ứng dụng này cung cấp một backend hoàn chỉnh cho một hệ thống chat với hỗ trợ tin nhắn realtime, quản lý người dùng, upload media, và nhiều tính năng nâng cao khác.

---

## 📋 Mục lục

- [Tổng quan](#tổng-quan)
- [Tính năng](#tính-năng)
- [Tech Stack](#tech-stack)
- [Kiến trúc](#kiến-trúc)
- [Yêu cầu](#yêu-cầu)
- [Cài đặt & Cấu hình](#cài-đặt--cấu-hình)
- [Chạy ứng dụng](#chạy-ứng-dụng)
- [API Endpoints](#api-endpoints)
- [WebSocket](#websocket)
- [Bảo mật](#bảo-mật)
- [Deployment](#deployment)
- [Testing](#testing)
- [Cấu trúc Thư mục](#cấu-trúc-thư-mục)
- [Đóng góp](#đóng-góp)
- [Liên hệ](#liên-hệ)

---

## 🎯 Tổng quan

**Chat App Backend** là một dự án backend hàng đầu cho ứng dụng chat realtime, được thiết kế để:

- Cung cấp một API RESTful mạnh mẽ và dễ mở rộng
- Hỗ trợ messaging realtime qua WebSocket (STOMP)
- Quản lý người dùng, cuộc trò chuyện, và tin nhắn hiệu quả
- Tích hợp với các dịch vụ cloud (Cloudinary, Gmail SMTP)
- Sử dụng JWT để xác thực an toàn

**Repository:** [tranthinh222/chat_app_backend](https://github.com/tranthinh222/chat_app_backend)

---

## ✨ Tính năng

### Đã triển khai

- ✅ **Xác thực & Phân quyền**
  - Đăng ký / Đăng nhập với JWT
  - Refresh token mechanism
  - Role-based access control (RBAC)

- ✅ **Quản lý Người dùng**
  - Thông tin hồ sơ người dùng
  - Avatar upload via Cloudinary
  - Trạng thái online/offline
  - Tìm kiếm và danh sách người dùng

- ✅ **Quản lý Conversation**
  - Tạo 1-1 hoặc group conversation
  - Quản lý thành viên trong group
  - Xóa conversation
  - Lấy danh sách conversation

- ✅ **Messaging**
  - Gửi/nhận tin nhắn (REST API + WebSocket)
  - Hỗ trợ text và media messages
  - Xóa tin nhắn
  - Lịch sử tin nhắn với pagination

- ✅ **Message Reactions**
  - Thumbs up / custom reactions
  - Thêm/xóa reactions

- ✅ **Media Upload**
  - Upload image, video via Cloudinary
  - Tối ưu hóa media

- ✅ **Email Notifications** (SMTP)
  - Hỗ trợ gửi email thông báo qua Gmail

- ✅ **WebSocket Realtime**
  - STOMP protocol hỗ trợ
  - Message broadcast
  - Xác thực WebSocket connection

---

## 🛠 Tech Stack

| Lớp                | Công nghệ            | Phiên bản       |
| ------------------ | -------------------- | --------------- |
| **Backend**        | Spring Boot          | 3.x             |
| **Language**       | Java                 | 11+             |
| **Build Tool**     | Gradle               | 7.x+            |
| **Database**       | MySQL                | 8.0+            |
| **Cache**          | Redis                | 6.0+ (tùy chọn) |
| **ORM**            | JPA/Hibernate        | -               |
| **Authentication** | JWT (JSON Web Token) | -               |
| **Messaging**      | STOMP over WebSocket | -               |
| **Media Storage**  | Cloudinary           | -               |
| **Email**          | JavaMail (SMTP)      | -               |

---

## 🏗 Kiến trúc

### Cấu trúc Layer

```
┌─────────────────────────────────────────────────┐
│        Controller Layer (REST + WebSocket)       │
│  AuthController, UserController, MessageController... │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│         Service Layer (Business Logic)          │
│  AuthService, UserService, MessageService...    │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│        Repository Layer (Data Access)           │
│  UserRepository, MessageRepository...           │
└──────────────────┬──────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────┐
│    Entity / Domain Models (Database)             │
│  User, Conversation, Message, MessageReaction   │
└─────────────────────────────────────────────────┘
```

### Tệp cấu hình chính

- `WebSocketConfig` — Cấu hình WebSocket và STOMP broker
- `SecurityConfiguration` — Spring Security + JWT
- `CorsConfig` — CORS configuration
- `CloudinaryConfig` — Cloudinary integration
- `RedisConfig` — Redis cache (tùy chọn)

---

## 💻 Yêu cầu

- **Java**: 11 trở lên
- **MySQL**: 8.0 trở lên
- **Git**: 2.25+
- **Gradle**: 7.0+ (hoặc dùng gradle wrapper)
- **(Tùy chọn) Redis**: 6.0+

### Tài khoản/Dịch vụ bên ngoài

- **Cloudinary**: Để upload media (có tài khoản miễn phí)
- **Gmail**: Để gửi email SMTP

---

## 🚀 Cài đặt & Cấu hình

### 1. Clone Repository

```bash
git clone https://github.com/tranthinh222/Chat-App.git
cd Chat-App
```

### 2. Cài đặt MySQL

Tạo database `chatapp`:

```sql
CREATE DATABASE chatapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Tạo user (nếu cần):

```sql
CREATE USER 'chatapp_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON chatapp.* TO 'chatapp_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Cấu hình Environment Variables

Tạo file `.env` hoặc export các biến sau (để trong `application.properties` hoặc environment):

```bash
# Database
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/chatapp
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=123456

# JWT
export THINHTRAN_JWT_BASE64_SECRET=D3/DgwKfJWOHX8a28lcSLuWZQbV9xj2gd83G/akyeqpzJeN39SDFopi7BAsuWqB2Fv3yO/XEDeDXhHyn4AAVLw==
export THINHTRAN_JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS=86400
export THINHTRAN_JWT_REFRESH_TOKEN_VALIDITY_IN_SECONDS=8640000

# Cloudinary
export CLOUDINARY_URL=cloudinary://your_api_key:your_api_secret@your_cloud_name

# Gmail SMTP
export SPRING_MAIL_USERNAME=your-email@gmail.com
export SPRING_MAIL_PASSWORD=your_app_password

# Redis (optional)
export SPRING_REDIS_HOST=localhost
export SPRING_REDIS_PORT=6379
```

### 4. Cập nhật application.properties

File `src/main/resources/application.properties` đã chứa cấu hình mẫu. Chỉnh sửa theo cần thiết hoặc dùng environment variables để ghi đè.

---

## ▶️ Chạy ứng dụng

### Phát triển (Development)

```bash
./gradlew bootRun
```

Server khởi động trên `http://localhost:8080`

### Build JAR

```bash
./gradlew bootJar
```

Chạy JAR:

```bash
java -jar build/libs/chatapp-*.jar
```

### Chạy với Docker (tuỳ chọn)

Bạn có thể build Docker image:

```bash
docker build -t chat-app-backend .
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/chatapp chat-app-backend
```

---

## 📡 API Endpoints

Base URL: `http://localhost:8080/api`

### Authentication

| Method | Endpoint              | Mô tả                    |
| ------ | --------------------- | ------------------------ |
| POST   | `/auth/register`      | Đăng ký tài khoản mới    |
| POST   | `/auth/login`         | Đăng nhập, lấy JWT token |
| POST   | `/auth/refresh-token` | Làm mới access token     |

### Users

| Method | Endpoint                    | Mô tả                                 |
| ------ | --------------------------- | ------------------------------------- |
| GET    | `/users/{id}`               | Lấy thông tin người dùng              |
| PUT    | `/users/{id}`               | Cập nhật hồ sơ người dùng             |
| GET    | `/users`                    | Danh sách người dùng (với search)     |
| GET    | `/users/{id}/conversations` | Danh sách conversation của người dùng |

### Conversations

| Method | Endpoint                                 | Mô tả                                 |
| ------ | ---------------------------------------- | ------------------------------------- |
| POST   | `/conversations`                         | Tạo conversation mới                  |
| GET    | `/conversations`                         | Danh sách conversation của người dùng |
| GET    | `/conversations/{id}`                    | Chi tiết conversation                 |
| PUT    | `/conversations/{id}`                    | Cập nhật conversation                 |
| DELETE | `/conversations/{id}`                    | Xóa conversation                      |
| POST   | `/conversations/{id}/members`            | Thêm thành viên                       |
| DELETE | `/conversations/{id}/members/{memberId}` | Xóa thành viên                        |

### Messages

| Method | Endpoint                       | Mô tả                                        |
| ------ | ------------------------------ | -------------------------------------------- |
| POST   | `/messages`                    | Gửi tin nhắn                                 |
| GET    | `/conversations/{id}/messages` | Lấy tin nhắn trong conversation (pagination) |
| PUT    | `/messages/{id}`               | Chỉnh sửa tin nhắn                           |
| DELETE | `/messages/{id}`               | Xóa tin nhắn                                 |

### Message Reactions

| Method | Endpoint                                | Mô tả         |
| ------ | --------------------------------------- | ------------- |
| POST   | `/messages/{id}/reactions`              | Thêm reaction |
| DELETE | `/messages/{id}/reactions/{reactionId}` | Xóa reaction  |

### Upload

| Method | Endpoint  | Mô tả                      |
| ------ | --------- | -------------------------- |
| POST   | `/upload` | Upload media (image/video) |

---

## 📚 API Examples

### Cập nhật trạng thái online/offline qua WebSocket

**STOMP Destination (Client → Server):**

```
/app/user.status
```

**Request Message:**

```json
{
  "userId": 1,
  "isOnline": true
}
```

**Broadcast Destination (Server → All Clients):**

```
/topic/users
```

**Broadcast Message:**

```json
{
  "userId": 1,
  "isOnline": true
}
```

**JavaScript Client Example:**

```javascript
// Send user status update
stompClient.publish({
  destination: "/app/user.status",
  body: JSON.stringify({
    userId: 1,
    isOnline: true,
  }),
});

// Subscribe to user status updates
stompClient.subscribe("/topic/users", (message) => {
  const statusUpdate = JSON.parse(message.body);
  console.log(
    `User ${statusUpdate.userId} is now ${statusUpdate.isOnline ? "online" : "offline"}`,
  );
});
```

---

## 🔄 WebSocket

### Kết nối

Kết nối WebSocket qua: `ws://localhost:8080/ws`

### STOMP Destinations

Gửi tin nhắn realtime:

```
/app/chat.sendMessage
```

Cập nhật trạng thái online/offline:

```
/app/user.status
```

Nhận tin nhắn từ conversation:

```
/topic/conversation/{conversationId}
```

Nhận thông báo người dùng online/offline:

```
/topic/users
```

### Ví dụ (JavaScript Client)

```javascript
const stompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8080/ws",
  connectHeaders: {
    login: "user",
    passcode: "password",
    Authorization: `Bearer ${jwt_token}`,
  },
});

stompClient.onConnect = () => {
  // Subscribe to conversation messages
  stompClient.subscribe("/topic/conversation/1", (message) => {
    console.log("Received:", JSON.parse(message.body));
  });

  // Send message
  stompClient.publish({
    destination: "/app/chat.sendMessage",
    body: JSON.stringify({
      conversationId: 1,
      content: "Hello!",
      senderName: "Thinh",
    }),
  });
};

stompClient.activate();
```

---

## 🔐 Bảo mật

### JWT Authentication

- Mọi endpoint (trừ `/auth/register` và `/auth/login`) yêu cầu header `Authorization: Bearer <token>`
- Access token hết hạn sau 1 ngày (cấu hình trong `application.properties`)
- Refresh token hết hạn sau 100 ngày

### CORS

- CORS đã được cấu hình trong `CorsConfig`
- Cho phép frontend từ các domain được phép

### WebSocket Authentication

- Kết nối WebSocket yêu cầu JWT token hợp lệ
- Được xác thực trong `WebSocketAuthInterceptorConfig`

### Best Practices

- ⚠️ **Không commit credentials** vào repository
- Sử dụng environment variables cho secrets
- Luôn HTTPS cho production
- Validate input trên server
- SQL Injection prevention: Sử dụng parameterized queries (JPA)

---

## 🌐 Deployment

### Heroku

1. Đăng ký tài khoản Heroku
2. Cài đặt Heroku CLI
3. Tạo `Procfile`:
   ```
   web: java -jar build/libs/chatapp-*.jar
   ```
4. Deploy:
   ```bash
   heroku login
   heroku create your-app-name
   git push heroku main
   ```

### AWS (Elastic Beanstalk)

1. Tạo `.ebextensions/java.config`
2. Deploy bằng Elastic Beanstalk CLI

### Docker + Docker Compose

`docker-compose.yml`:

```yaml
version: "3.8"
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: chatapp
      MYSQL_ROOT_PASSWORD: 123456
    ports:
      - "3306:3306"

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/chatapp
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 123456
```

Chạy:

```bash
docker-compose up
```

---

## 🧪 Testing

### Chạy Unit Tests

```bash
./gradlew test
```

### Chạy Integration Tests

```bash
./gradlew integrationTest
```

### Coverage Report

```bash
./gradlew test jacocoTestReport
```

---

## 📁 Cấu trúc Thư mục

```
chat_app_backend/
├── src/
│   ├── main/
│   │   ├── java/com/thinhtran/chatapp/
│   │   │   ├── config/              # Spring Configuration
│   │   │   │   ├── WebSocketConfig.java
│   │   │   │   ├── SecurityConfiguration.java
│   │   │   │   ├── CorsConfig.java
│   │   │   │   └── ...
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── UserController.java
│   │   │   │   ├── MessageController.java
│   │   │   │   └── ...
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── impl/
│   │   │   │   └── ...
│   │   │   ├── repository/          # Data Access
│   │   │   ├── domain/              # Entities & DTOs
│   │   │   │   ├── request/
│   │   │   │   ├── response/
│   │   │   │   └── ...
│   │   │   ├── util/                # Utilities
│   │   │   │   ├── annotation/
│   │   │   │   ├── constant/
│   │   │   │   └── error/
│   │   │   └── ChatappApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/                        # Unit Tests
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── Dockerfile
├── docker-compose.yml
├── README.md
└── HELP.md
```

---

## 🤝 Đóng góp

Chúng tôi rất hoan nghênh các đóng góp! Để đóng góp:

1. **Fork** repository
2. **Tạo branch** cho feature của bạn:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Commit changes**:
   ```bash
   git commit -m "Add: your feature description"
   ```
4. **Push to branch**:
   ```bash
   git push origin feature/your-feature-name
   ```
5. **Tạo Pull Request** với mô tả chi tiết

### Hướng dẫn Commit

- Sử dụng format: `[Type]: Description`
- Types: `Add`, `Fix`, `Refactor`, `Docs`, `Test`, etc.
- Ví dụ: `Add: WebSocket support for real-time messaging`

---

## 📞 Liên hệ

- **Author**: Trần Thịnh
- **Email**: tranthinh220205@gmail.com
- **GitHub**: [@tranthinh222](https://github.com/tranthinh222)
- **Repository**: [chat_app_backend](https://github.com/tranthinh222/chat_app_backend)

Nếu có câu hỏi hoặc gặp vấn đề, hãy mở một **Issue** hoặc liên hệ trực tiếp.

---

## 📄 License

Project này được cấp phép dưới MIT License - xem file [LICENSE](LICENSE) để chi tiết.

---

## 🙏 Cảm ơn

Cảm ơn tất cả những người đóng góp và sử dụng project này!

**Có gì cần giúp? Mở một Issue hoặc PR ngay hôm nay! 🚀**
