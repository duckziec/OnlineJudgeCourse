package app;

import model.User;
import ui.auth.Login;
import ui.courses.CourseData;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    public boolean isLoggedIn() {
        if (currentUser == null) {
            return false;
        }
        return true;
    }

    public String getCurrentUserName() {
        return currentUser.getUserName();
    }

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
        // ===== LOAD DỮ LIỆU KHOÁ HỌC MÀ USER ĐÃ ĐĂNG KÝ =====
        CourseData.reloadUserCourses();
    }

    public void logout() {
        this.currentUser = null;
        // === START: Remember Me Logic ===
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(Login.class);
        prefs.remove("username");
        prefs.remove("rememberMe");
        // === END: Remember Me Logic ===

        // ==== XOÁ TOÀN BỘ DỮ LIỆU NGƯỜI DÙNG CŨ ====
        CourseData.removeAllUserCourse();
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
