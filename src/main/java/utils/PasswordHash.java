package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
    // cost/work factor: 10..12 là hợp lý cho dev; 12-14 cho production mạnh hơn
    private static final int WORK_FACTOR = 12;

    // Tạo hash từ mật khẩu
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(WORK_FACTOR));
    }

    // Kiểm tra mật khẩu nhập vào có khớp với hash lưu trong DB không
    public static boolean checkPassword(String password, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) return false;
        return BCrypt.checkpw(password, hashedPassword);
    }

}
