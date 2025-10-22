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
import java.util.UUID;

@Service
public class DialogflowService {

    // 🧩 JSON credentials dạng chuỗi (Render: lưu trong Environment Variable)
    @Value("${dialogflow.credentials.json:}")
    private String credentialsJson;

    // 💻 File key local khi chạy trên máy (chỉ dùng khi dev)
    @Value("${dialogflow.key.path:}")
    private String keyPath;

    // 🌍 Project ID trong Dialogflow
    @Value("${dialogflow.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        System.out.println("✅ Project ID: " + projectId);

        // Ghi rõ hệ thống đang dùng loại credentials nào
        if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
            System.out.println("🌍 Đang dùng GOOGLE_APPLICATION_CREDENTIALS (file trong container).");
        } else if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("☁️ Đang dùng JSON từ biến môi trường (Render).");
        } else if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("💻 Đang dùng file local: " + keyPath);
        } else {
            System.out.println("⚠️ Không tìm thấy thông tin xác thực nào (credentials).");
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
    // 🔐 Tự động chọn cách load credentials
    // ----------------------------------
    private GoogleCredentials loadCredentials() throws IOException {
        // 1️⃣ Có file từ GOOGLE_APPLICATION_CREDENTIALS (Docker / Render)
        String envPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (envPath != null && !envPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ GOOGLE_APPLICATION_CREDENTIALS: " + envPath);
            return GoogleCredentials.fromStream(new FileInputStream(envPath));
        }

        // 2️⃣ Có JSON lưu trong biến môi trường (Render)
        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("🔹 Load credentials từ biến môi trường JSON.");
            Path temp = Files.createTempFile("dialogflow-", ".json");
            Files.writeString(temp, credentialsJson, StandardCharsets.UTF_8);
            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", temp.toString());
            return GoogleCredentials.fromStream(Files.newInputStream(temp));
        }

        // 3️⃣ Có file local khi chạy dev
        if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ file local: " + keyPath);
            return GoogleCredentials.fromStream(new FileInputStream(keyPath));
        }

        throw new IllegalStateException("❌ Không tìm thấy credentials hợp lệ (Env, JSON hoặc File).");
    }
}
