package com.example.demo.service;

import com.google.cloud.dialogflow.v2.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.UUID;

@Service
public class DialogflowService {

    @Value("${dialogflow.key.path}")
    private String keyPath;

    @Value("${dialogflow.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        // Ghi log để kiểm tra
        System.out.println("✅ Dialogflow key path loaded from properties: " + keyPath);
        System.out.println("✅ Project ID: " + projectId);
    }

    public String detectIntent(String text) {
        try {
            // Đọc file credentials
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
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
