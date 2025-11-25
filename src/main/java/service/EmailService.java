package service;

import app.AppConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {
    private static final String EMAIL_ADDRESS = AppConfig.getEmailAddress();
    private static final String EMAIL_PASSWORD = AppConfig.getEmailPassword();

    // Tái sử dụng Session để tăng tốc
    private static final Session session;

    // Thread pool cho gửi email bất đồng bộ
    private static final ExecutorService emailExecutor = Executors.newFixedThreadPool(3);

    static {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tối ưu timeout
        props.put("mail.smtp.connectiontimeout", "5000"); // 5 giây
        props.put("mail.smtp.timeout", "5000");           // 5 giây
        props.put("mail.smtp.writetimeout", "5000");      // 5 giây

        // Connection pooling
        props.put("mail.smtp.connectionpool", "true");
        props.put("mail.smtp.connectionpoolsize", "5");

        // Tạo Session một lần duy nhất
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ADDRESS, EMAIL_PASSWORD);
            }
        });
    }

    /**
     * Gửi email đồng bộ (blocking) - Dùng khi cần chắc chắn email được gửi ngay
     */
    public static void sendEmail(String to, String subject, String body) throws MessagingException {
        try {
            Message message = createMessage(to, subject, body);
            Transport.send(message);
            System.out.println("✓ Email đã được gửi thành công đến: " + to);
        } catch (MessagingException e) {
            System.err.println("✗ Lỗi gửi email: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Gửi email bất đồng bộ (async) - Không block UI, tăng tốc độ
     * Dùng cho hầu hết các trường hợp
     */
    public static CompletableFuture<Void> sendEmailAsync(String to, String subject, String body) {
        return CompletableFuture.runAsync(() -> {
            try {
                Message message = createMessage(to, subject, body);
                Transport.send(message);
                System.out.println("✓ Email async đã được gửi thành công đến: " + to);
            } catch (MessagingException e) {
                System.err.println("✗ Lỗi gửi email async: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }, emailExecutor);
    }

    /**
     * Gửi email với callback - Xử lý kết quả sau khi gửi
     */
    public static void sendEmailWithCallback(String to, String subject, String body,
                                             Runnable onSuccess, Runnable onError) {
        sendEmailAsync(to, subject, body)
                .thenRun(() -> {
                    if (onSuccess != null) onSuccess.run();
                })
                .exceptionally(e -> {
                    if (onError != null) onError.run();
                    return null;
                });
    }

    /**
     * Tạo Message - dùng chung cho cả sync và async
     */
    private static Message createMessage(String to, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_ADDRESS));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");
        return message;
    }

    /**
     * Đóng executor khi shutdown app
     */
    public static void shutdown() {
        emailExecutor.shutdown();
    }
}