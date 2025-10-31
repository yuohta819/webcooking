package com.example.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Value("${APP_NAME}")
    private String appName;

    @Value("${EMAIL_SENDER}")
    private String emailSender;
    @Value("${TAX}")
    private Double tax;

    public void sendInvoiceEmail(String toEmail, String customerName, String orderId, double total1,
            List<Map<String, Object>> items)
            throws MessagingException {

        try {
            // 1️⃣ Tính toán subtotal và thuế
            double total = total1;
            double taxRate = tax;
            double subtotal = total / (tax + 1);
            double tax = subtotal * taxRate;
            // 2️⃣ Định dạng theo chuẩn Việt Nam
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(0);

            String subtotalStr = nf.format(subtotal);
            String taxStr = nf.format(tax);
            String totalStr = nf.format(total);

            // 3️⃣ Tạo context Thymeleaf
            Context context = new Context();
            context.setVariable("name", customerName);
            context.setVariable("email", toEmail);
            context.setVariable("orderId", orderId);
            context.setVariable("items", items);
            context.setVariable("date", java.time.LocalDate.now().toString());
            // ✅ Gửi dữ liệu hiển thị đã format
            context.setVariable("subtotalStr", subtotalStr);
            context.setVariable("taxStr", taxStr);
            context.setVariable("totalStr", totalStr);
            context.setVariable("appName", appName);
            // 4️⃣ Render template HTML
            String htmlContent = templateEngine.process("invoice-email", context);

            // 5️⃣ Gửi mail
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(emailSender);
            helper.setTo(toEmail);
            helper.setSubject("Hóa đơn đơn hàng #" + orderId);
            helper.setText(htmlContent, true); // true = HTML
            mailSender.send(message);
            System.out.println("✅ Mail sent successfully!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
