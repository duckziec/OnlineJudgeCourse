package service;

import dao.UserDAO;
import exception.ValidationException;
import model.User;
import utils.PasswordHash;
import utils.StringUtil;
import utils.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * User service with business logic and validation
 * Uses Dependency Injection for loose coupling
 */
public class UserService {

    private final UserDAO userDAO;
    private static final int OTP_EXPIRY_MINUTES = 10;

    /**
     * Constructor Injection
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Register a new user with validation
     */
    public boolean registerUser(String fullname, String username, String email, String password) {
        // Step 1: Validate input
        fullname = StringUtil.normalizeFullName(fullname);
        Validator.validateName("Full name", fullname);
        Validator.validateUsername(username);
        Validator.validateEmail(email);
        Validator.validatePassword(password);

        // Step 2: Check duplicate
        if (userDAO.existByUserNameOrEmail(username, email)) {
            throw new ValidationException("User with this username or email already exists");
        }

        // Step 3: Hash password
        String hashedPassword = PasswordHash.hashPassword(password);

        // Step 4: Create a user
        User user = new User();
        user.setFullName(fullname);
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        return userDAO.addUser(user);
    }

    /**
     * Login with username/email and password
     */
    public User login(String identifier, String password) {
        // Validate input
        Validator.validateRequired("Username/Email", identifier);
        Validator.validateRequired("Password", password);

        // Find user
        User user = userDAO.findByUserOrEmail(identifier);
        if (user == null) {
            throw new ValidationException("User not found");
        }

        // Check password
        if (!PasswordHash.checkPassword(password, user.getPassword())) {
            throw new ValidationException("Incorrect password");
        }

        return user;
    }

    /**
     * Get all users (admin function)
     */
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    /**
     * Update user profile
     */
    public boolean updateUser(User user) {
        Validator.validateRequired("Full name", user.getFullName());
        Validator.validateEmail(user.getEmail());

        return userDAO.updateUserById(user);
    }

    /**
     * Delete user
     */
    public boolean deleteUser(int userId) {
        Validator.validatePositiveInteger("User ID", userId);
        return userDAO.deleteUser(userId);
    }

    /**
     * Get user by ID
     */
    public User getUserById(int userId) {
        Validator.validatePositiveInteger("User ID", userId);
        User user = userDAO.findById(userId);

        if (user == null) {
            throw new ValidationException("User not found with ID: " + userId);
        }

        return user;
    }

    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        Validator.validateRequired("Username", username);
        User user = userDAO.findByUserName(username);

        if (user == null) {
            throw new ValidationException("User not found: " + username);
        }

        return user;
    }

    /**
     * Get a user by username or email
     */
    public User getUserByUsernameOrEmail(String identifier) {
        User user = userDAO.findByUserOrEmail(identifier);
        if (user == null) {
            throw new ValidationException("User not found");
        }

        return user;
    }

    /**
     * Lấy danh người dùng đã đăng ký khoá học đó
     */
    public List<User> getUserByCourseId(int courseId) {
        return userDAO.getUsersByCourse(courseId);
    }


    // Hàm tạo mã OTP 6 số
    private String generateOtp() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000); // Tạo số từ 100000 đến 999999
        return String.valueOf(number);
    }

    /**
     * Gửi mã OTP đặt lại mật khẩu qua email.
     */
    public boolean sendPasswordResetCode(String email) throws Exception {
        // Kiểm tra email hợp lệ
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new ValidationException("Email không tồn tại trong hệ thống.");
        }

        try {
            // Tạo mã OTP và thời gian hết hạn
            String code = generateOtp();
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);

            // Cập nhật vào DB
            userDAO.updateResetToken(user.getUserId(), code, expiryTime);

            // Tạo nội dung email (HTML)
            String emailContent =
                    "<html><body style='font-family: Arial, sans-serif; line-height:1.6;'>"
                            + "<p>Xin chào <b>" + user.getFullName() + "</b>,</p>"
                            + "<p>Mã xác thực để đặt lại mật khẩu của bạn là: "
                            + "<b style='color:#d9534f; font-size:16px;'>" + code + "</b></p>"
                            + "<p>Mã này sẽ hết hạn sau <b>" + OTP_EXPIRY_MINUTES + "</b> phút.</p>"
                            + "<br><p>Trân trọng,<br><i>Đội ngũ hỗ trợ</i></p>"
                            + "</body></html>";

            // Gửi email (HTML)
            EmailService.sendEmail(email, "Mã Xác Thực Đặt Lại Mật Khẩu", emailContent);

            return true;
        } catch (Exception e) {
            System.err.println("Lỗi gửi email đặt lại mật khẩu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Đặt lại mật khẩu bằng mã OTP.
     */
    public boolean resetPasswordWithCode(String email, String code, String newPassword) {
        Validator.validatePassword(newPassword);

        User user = userDAO.getUserByEmail(email);

        if (user == null || user.getResetToken() == null || !user.getResetToken().equals(code)) {
            throw new ValidationException("Email hoặc mã xác thực không hợp lệ.");
        }

        if (LocalDateTime.now().isAfter(user.getTokenExpiry())) {
            throw new ValidationException("Mã xác thực đã hết hạn. Vui lòng yêu cầu mã mới.");
        }

        String hashedPassword = PasswordHash.hashPassword(newPassword);

        // Note: These two database operations should be wrapped in a transaction
        // to ensure data consistency.
        userDAO.updateUserPassword(user.getUserId(), hashedPassword);
        userDAO.clearResetToken(user.getUserId());
        return true;
    }
}
