package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DialogflowService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final DialogflowService dialogflowService;

    public ChatController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        System.out.println("🔹 Received request body: " + request);

        String userMessage = request.get("message");
        if (userMessage == null) {
            System.out.println("⚠️ userMessage is NULL!");
            return Map.of("reply", "Không nhận được tin nhắn hợp lệ!");
        }
        String reply = dialogflowService.detectIntent(userMessage);
        return Map.of("reply", reply);
    }
}
