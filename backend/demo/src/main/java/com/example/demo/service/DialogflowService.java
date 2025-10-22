package com.example.demo.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

@Service
public class DialogflowService {

    // 🧩 Biến môi trường chứa Base64 JSON (Render)
    @Value("${dialogflow.credentials.base64:}")
    private String credentialsBase64;

    // 💻 File key local khi chạy dev
    @Value("${dialogflow.key.path:}")
    private String keyPath;

    // 🌍 Project ID của Dialogflow
    @Value("${dialogflow.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        System.out.println("✅ Project ID: " + projectId);

        if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
            System.out.println("🌍 Dùng GOOGLE_APPLICATION_CREDENTIALS (đã đặt sẵn file).");
        } else if (credentialsBase64 != null && !credentialsBase64.isEmpty()) {
            System.out.println("☁️ Dùng biến môi trường Base64 (Render).");
        } else if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("💻 Dùng file local: " + keyPath);
        } else {
            System.out.println("⚠️ Không tìm thấy credentials nào!");
        }
    }

    // ----------------------------------
    // 🎯 Gửi tin nhắn đến Dialogflow
    // ----------------------------------
    public String detectIntent(String text) {
        try {
            GoogleCredentials credentials = loadCredentials();

            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
                SessionName session = SessionName.of(projectId, UUID.randomUUID().toString());

                TextInput textInput = TextInput.newBuilder()
                        .setText(text)
                        .setLanguageCode("vi")
                        .build();

                QueryInput queryInput = QueryInput.newBuilder()
                        .setText(textInput)
                        .build();

                DetectIntentRequest request = DetectIntentRequest.newBuilder()
                        .setSession(session.toString())
                        .setQueryInput(queryInput)
                        .build();

                DetectIntentResponse response = sessionsClient.detectIntent(request);
                String reply = response.getQueryResult().getFulfillmentText();

                System.out.println("💬 Dialogflow Response: " + reply);
                return reply != null && !reply.isEmpty() ? reply : "Xin lỗi, tôi chưa hiểu câu này 😅";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ Xin lỗi, hệ thống đang gặp sự cố.";
        }
    }

    // ----------------------------------
    // 🔐 Load credentials (Render hoặc Local)
    // ----------------------------------
    private GoogleCredentials loadCredentials() throws IOException {
        // 1️⃣ Có file từ GOOGLE_APPLICATION_CREDENTIALS
        String envPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (envPath != null && !envPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ GOOGLE_APPLICATION_CREDENTIALS: " + envPath);
            return GoogleCredentials.fromStream(new FileInputStream(envPath));
        }

        // 2️⃣ Có chuỗi Base64 từ Render
        if (credentialsBase64 != null && !credentialsBase64.isEmpty()) {
            System.out.println("🔹 Giải mã Base64 credentials từ Render...");
            byte[] decoded = Base64.getDecoder().decode(credentialsBase64);

            // Tạo file tạm trong /tmp
            Path tempFile = Files.createTempFile("dialogflow-", ".json");
            Files.write(tempFile, decoded);

            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", tempFile.toString());
            System.out.println("✅ Đã ghi credentials tạm tại: " + tempFile);

            return GoogleCredentials.fromStream(new FileInputStream(tempFile.toFile()));
        }

        // 3️⃣ Dùng file local (dev)
        if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ file local: " + keyPath);
            return GoogleCredentials.fromStream(new FileInputStream(keyPath));
        }

        throw new IllegalStateException("❌ Không tìm thấy credentials hợp lệ (GOOGLE_APPLICATION_CREDENTIALS, Base64, hoặc file local).");
    }
}
