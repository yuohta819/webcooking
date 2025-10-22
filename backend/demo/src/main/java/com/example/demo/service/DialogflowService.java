package com.example.demo.service;

import com.google.cloud.dialogflow.v2.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class DialogflowService {

    @Value("${dialogflow.credentials.json:}")
    private String credentialsJson; // Dùng khi deploy (Render)

    @Value("${dialogflow.key.path:}")
    private String keyPath; // Dùng khi chạy local

    @Value("${dialogflow.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        System.out.println("✅ Project ID: " + projectId);

        String envPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (envPath != null && !envPath.isEmpty()) {
            System.out.println("🌍 Dùng GOOGLE_APPLICATION_CREDENTIALS: " + envPath);
        } else if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("☁️ Dùng credentials từ biến môi trường JSON (Render).");
        } else {
            System.out.println("💻 Dùng file local: " + keyPath);
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
            return "Xin lỗi, hệ thống đang bận 😅";
        }
    }

    // -------------------------------
    // 🔹 Hàm tự động load credentials
    // -------------------------------
    private GoogleCredentials loadCredentials() throws IOException {
        // 1️⃣ Ưu tiên biến môi trường GOOGLE_APPLICATION_CREDENTIALS (Render/Linux)
        String envPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (envPath != null && !envPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ GOOGLE_APPLICATION_CREDENTIALS: " + envPath);
            return GoogleCredentials.fromStream(new FileInputStream(envPath));
        }

        // 2️⃣ Nếu có credentials JSON trong biến môi trường (Render)
        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("🔹 Load credentials từ JSON trong biến môi trường.");
            return GoogleCredentials.fromStream(new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8)));
        }

        // 3️⃣ Mặc định: dùng file cục bộ (Local)
        if (keyPath != null && !keyPath.isEmpty()) {
            System.out.println("🔹 Load credentials từ file local: " + keyPath);
            return GoogleCredentials.fromStream(new FileInputStream(keyPath));
        }

        throw new IllegalStateException("❌ Không tìm thấy credentials hợp lệ (GOOGLE_APPLICATION_CREDENTIALS, JSON, hoặc file local).");
    }
}
