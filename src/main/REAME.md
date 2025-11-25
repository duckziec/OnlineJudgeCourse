OnlineJudgeCourse/
│
├── src/
│   ├── app/
│   │   ├── Main.java                         # Entry point (khởi chạy app)
│   │   └── AppConfig.java                    # Cấu hình app (URL, DB, API,...)
│   │
│   ├── model/                                # Các lớp đại diện cho bảng trong DB (Entity)
│   │   ├── User.java
│   │   ├── Course.java
│   │   ├── Lesson.java
│   │   ├── Enrollment.java
│   │   ├── CodingQuestion.java
│   │   ├── TestCase.java
│   │   └── Submission.java
│   │
│   ├── dao/                                  # Data Access Object (JDBC thao tác DB)
│   │   ├── DBConnection.java                 # Tạo kết nối DB
│   │   ├── UserDAO.java
│   │   ├── CourseDAO.java
│   │   ├── LessonDAO.java
│   │   ├── EnrollmentDAO.java
│   │   ├── CodingQuestionDAO.java
│   │   ├── TestCaseDAO.java
│   │   └── SubmissionDAO.java
│   │
│   ├── service/                              # Xử lý logic nghiệp vụ
│   │   ├── UserService.java
│   │   ├── CourseService.java
│   │   ├── LessonService.java
│   │   ├── SubmissionService.java
│   │   └── Judge0Service.java                # Gọi API Judge0 chấm code
│   │
│   ├── ui/                                   # Giao diện (Swing UI)
│   │   ├── common/                           # Các component tái sử dụng
│   │   │   ├── RoundedButton.java
│   │   │   └── CustomTableModel.java
│   │   ├── auth/                             # Màn hình đăng nhập, đăng ký
│   │   │   ├── LoginFrame.java
│   │   │   └── RegisterFrame.java
│   │   ├── dashboard/                        # Màn hình chính sau khi đăng nhập
│   │   │   ├── DashboardFrame.java
│   │   │   └── SideMenuPanel.java
│   │   ├── courses/
│   │   │   ├── CourseListPanel.java
│   │   │   └── CourseDetailPanel.java
│   │   ├── lessons/
│   │   │   ├── LessonListPanel.java
│   │   │   └── LessonContentPanel.java
│   │   ├── coding/
│   │   │   ├── CodeEditorPanel.java          # Khung nhập code
│   │   │   ├── TestCasePanel.java
│   │   │   └── SubmissionResultPanel.java
│   │   └── components/
│   │       └── AppDialog.java
│   │
│   ├── utils/                                # Tiện ích chung
│   │   ├── HttpClientUtil.java               # Gửi request đến Judge0
│   │   ├── JsonUtil.java                     # Dùng Gson/Jackson
│   │   ├── Validator.java
│   │   └── PasswordHash.java                 # Hash password bằng SHA-256/Bcrypt
│   │
│   └── exception/                            # Xử lý ngoại lệ riêng
│       ├── DatabaseException.java
│       ├── ApiException.java
│       └── ValidationException.java
│
├── lib/                                      # Thư viện ngoài (mysql-connector, gson,...)
│
├── resources/
│   ├── icons/                                # Icon giao diện
│   ├── css/                                  # Nếu dùng FlatLaf
│   └── config.properties                     # Thông tin cấu hình DB/API
│
├── .env                                      # (tuỳ chọn) lưu API key Judge0
├── README.md
└── pom.xml                                   # Nếu dùng Maven
