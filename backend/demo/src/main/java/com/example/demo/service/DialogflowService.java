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
    private String credentialsJson; // Biến môi trường (Railway)

    @Value("${dialogflow.key.path:}")
    private String keyPath; // Đường dẫn file local

    @Value("${dialogflow.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        System.out.println("✅ Project ID: " + projectId);
        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            System.out.println("✅ Đang dùng credentials từ biến môi trường (Railway).");
        } else {
            System.out.println("✅ Đang dùng credentials từ file local: " + keyPath);
        }
    }

    public String detectIntent(String text) {
        try {
            GoogleCredentials credentials;

            // 🔹 Nếu chạy Railway → đọc từ biến môi trường
            if (credentialsJson != null && !credentialsJson.isEmpty()) {
                credentials = GoogleCredentials.fromStream(
                        new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8))
                );
            } 
            // 🔹 Nếu chạy local → đọc file thật
            else if (keyPath != null && !keyPath.isEmpty()) {
                credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
            } 
            else {
                throw new IllegalStateException("Không tìm thấy credentials (biến môi trường hoặc file).");
            }

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
}
