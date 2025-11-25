
# 🎓 OnlineJudgeCourse  
### Hệ thống Quản lý Học tập & Chấm code Tự động

**OnlineJudgeCourse** là ứng dụng desktop viết bằng **Java Swing**, hỗ trợ sinh viên học lập trình, làm bài tập, biên dịch & chấm bài tự động thông qua **API Judge0**.

---

## 🚀 Công nghệ sử dụng

- **Ngôn ngữ:** Java (JDK 17+)  
- **Giao diện:** Java Swing + FlatLaf (UI hiện đại)  
- **CSDL:** MySQL  
- **Kết nối:** JDBC  
- **API Chấm bài:** Judge0 API (RapidAPI)  
- **Bảo mật:** Biến môi trường (.env)  
- **Thư viện:** Gson (JSON), Dotenv (env loader)

---

## 🛠 Hướng dẫn Cài đặt & Chạy ứng dụng

Làm theo các bước sau để chạy thành công dự án.

---

### **Bước 1: Clone dự án**

```bash
git clone https://github.com/duckziec/OnlineJudgeCourse
```

---

### **Bước 2: Cấu hình Cơ sở dữ liệu (MySQL)**

1. Mở MySQL Workbench / HeidiSQL / PHPMyAdmin.  
2. Tạo một database mới, ví dụ:

```
online_judge_db
```

3. Import file `database.sql` trong thư mục gốc dự án.

---

### **Bước 3: Cấu hình Biến môi trường (.env)**

Dự án dùng `.env` để giấu thông tin nhạy cảm. Repo chỉ bao gồm file mẫu `.env.example`.

1. Tại **thư mục gốc**, tạo file:

```
.env
```

2. Sao chép nội dung từ `.env.example`, sau đó điền thông tin máy bạn:

```
API_URL=https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&wait=true
API_KEY=YOUR_RAPIDAPI_KEY_HERE
API_HOST=judge0-ce.p.rapidapi.com

DB_URL=jdbc:mysql://YOUR_DB_HOST:YOUR_DB_PORT/YOUR_DB_NAME?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh&sslMode=VERIFY_IDENTITY&enabledTLSProtocols=TLSv1.2
DB_USER=YOUR_DB_USERNAME_HERE
DB_PASS=YOUR_DB_PASSWORD_HERE

EMAIL_ADDRESS=YOUR_EMAIL_ADDRESS_HERE
EMAIL_PASSWORD=btsr cpcg wgft ooki
```

---

### **Bước 4: Chạy ứng dụng**

#### **Cách 1 – Chạy bằng IntelliJ IDEA / Eclipse**

1. Mở dự án.  
2. Chờ IDE load thư viện trong thư mục `lib/`.  
3. Mở file:

```
src/app/Main.java
```

4. Chuột phải → **Run 'Main'**

#### **Cách 2 – Chạy bằng file JAR**

```bash
java -jar OnlineJudgeApp.jar
```

---

## 📂 Cấu trúc Dự án

```
OnlineJudgeCourse/
├── src/
│   ├── app/        # Main App & cấu hình chung
│   ├── dao/        # Tầng truy vấn DB (JDBC)
│   ├── model/      # Các entity (User, Course, Lesson...)
│   ├── service/    # Xử lý nghiệp vụ, gọi API Judge0
│   ├── ui/         # Giao diện Swing
│   └── utils/      # Helper, mã hóa mật khẩu, validate...
│
├── lib/            # Thư viện .jar bên ngoài
├── resources/      # Icon, hình ảnh, file SQL
├── .env.example    # File mẫu biến môi trường
└── README.md       # File hướng dẫn
```

---

## ✨ Tính năng chính

### 🔐 Xác thực
- Đăng ký / đăng nhập  
- Mã hóa mật khẩu an toàn (SHA-256)

### 📘 Khóa học
- Xem danh sách khóa học  
- Xem nội dung bài học

### 💻 Trình luyện tập code
- Code Editor hỗ trợ hiển thị cú pháp  
- Gửi code lên Judge0 để:
  - Biên dịch  
  - Chạy  
  - So sánh output  
- Chấm điểm tự động theo Test Case

### 📊 Thống kê
- Lịch sử nộp bài (Submission History)

---

## ❤️ Lời cảm ơn

Xin cảm ơn Giảng viên đã dành thời gian xem xét dự án!
