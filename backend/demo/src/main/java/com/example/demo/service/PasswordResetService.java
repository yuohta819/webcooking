package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.model.DBAccount;
import com.example.demo.model.PasswordResetToken;

@Service
public class PasswordResetService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private DataRepositoryAccount userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${frontend_url}")
    private String frontend;

    /**
     * Gửi email chứa link reset mật khẩu
     */
    @Transactional
    public String sendResetLink(String email) {
        // Kiểm tra người dùng có tồn tại không
        Optional<DBAccount> userOpt = userRepo.findEmail(email);
        if (userOpt.isEmpty()) {
            return "null";
        }

        DBAccount user = userOpt.get();

        // Xóa token cũ (nếu có)
        tokenRepo.deleteByEmail(email);

        // Tạo token mới
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setAccountid(user.getAccountid().intValue());
        resetToken.setCreate_at(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        resetToken.setExpiry_date(LocalDateTime.now().plusMinutes(15));
        tokenRepo.save(resetToken);

        // Tạo link gửi qua email (frontend nhận token này)
        String link = frontend + "/forgot/reset?token=" + token;
        // Gửi mail
        sendMail(
                email,
                "Password Reset Request",
                "Hello " + user.getUsername() + ",\n\n" +
                        "Click the link below to reset your password:\n" + link +
                        "\n\nThis link will expire in 15 minutes.\n\n" +
                        "If you didn’t request a password reset, please ignore this email.");

        return "success";
    }
    private void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * Đặt lại mật khẩu mới bằng token
     */
    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepo.findByToken(token);

        if (resetToken == null) {
            return "Invalid token";
        }

        if (resetToken.getExpiry_date().isBefore(LocalDateTime.now())) {
            tokenRepo.delete(resetToken);
            return "Token expired";
        }

        // Cập nhật mật khẩu mới
        DBAccount user = userRepo.findById(((Integer) resetToken.getAccountid()).longValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // Xóa token sau khi dùng
        tokenRepo.delete(resetToken);

        return "Password updated successfully";
    }
}
