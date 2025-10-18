package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private EmailService emailService;

    public void createOrder(String name, String email, Integer total, List<Map<String, Object>> items) {
        try {
            // Giả lập lưu vào DB
            String orderId = UUID.randomUUID().toString().substring(0, 8);

            // Gửi email hóa đơn HTML
            emailService.sendInvoiceEmail(email, name, orderId, total, items);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
