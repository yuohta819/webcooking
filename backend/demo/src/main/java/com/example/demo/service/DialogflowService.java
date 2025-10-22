package com.example.demo.service;

import com.google.cloud.dialogflow.v2.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
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

    // 📦 Khi deploy Render, ta lưu JSON thẳng vào biến môi trường
    @Value("${dialogflow.credentials.json:}")
    private String credentialsJson;

    // 💻 Khi chạy local, ta dùng file trong ổ D:
    @Value("${dialogflow.key.path:}")
    private String keyPath;

    @Value("${dialogflow.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        System.out.println("✅ Project ID: " + projectId);

        if (System.getenv("GOOGLE_APPLICATION_CREDENTIALS") != null) {
            System.out.println("🌍 Dùng GOOGLE_APPLICATION_CREDENTIALS trong hệ thống (Render/Docker).");
        } else if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("☁️ Dùng credentials JSON từ biến môi trường (Render).");
        } else if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("💻 Dùng file local từ đường dẫn: " + keyPath);
        } else {
            System.out.println("⚠️ Không tìm thấy credentials nào!");
        }
    }

    public String detectIntent(String text) {
        try {
            GoogleCredentials credentials = loadCredentials();

            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
                SessionName session = SessionName.of(projectId, UUID.randomUUID().toString());

                TextInput.Builder textInput = TextInput.newBuilder()
                        .setText(text)
                        .setLanguageCode("vi");

                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                DetectIntentRequest request = DetectIntentRequest.newBuilder()
                        .setSession(session.toString())
                        .setQueryInput(queryInput)
                        .build();

                DetectIntentResponse response = sessionsClient.detectIntent(request);
                String reply = response.getQueryResult().getFulfillmentText();

                System.out.println("💬 Dialogflow Response: " + reply);
                return reply;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return "Xin lỗi, hệ thống đang bận 😅";
        }
    }

    // -------------------------------
    // 🔹 Hàm tự động load credentials
    // -------------------------------
    private GoogleCredentials loadCredentials() throws IOException {
        // 1️⃣ Nếu có GOOGLE_APPLICATION_CREDENTIALS (Docker hoặc Render set sẵn)
        String envPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (envPath != null && !envPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ GOOGLE_APPLICATION_CREDENTIALS: " + envPath);
            return GoogleCredentials.fromStream(new FileInputStream(envPath));
        }

        // 2️⃣ Nếu có JSON trong biến môi trường (Render)
        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("🔹 Load credentials từ biến môi trường JSON (Render).");
            // Ghi tạm JSON ra file để SDK đọc được
            Path temp = Files.createTempFile("dialogflow-", ".json");
            Files.write(temp, credentialsJson.getBytes(StandardCharsets.UTF_8));
            System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", temp.toString());
            return GoogleCredentials.fromStream(Files.newInputStream(temp));
        }

        // 3️⃣ Nếu đang chạy local, đọc file cục bộ
        if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ file local: " + keyPath);
            return GoogleCredentials.fromStream(new FileInputStream(keyPath));
        }

        throw new IllegalStateException("❌ Không tìm thấy credentials hợp lệ (Env, JSON hoặc Local File).");
    }
}
