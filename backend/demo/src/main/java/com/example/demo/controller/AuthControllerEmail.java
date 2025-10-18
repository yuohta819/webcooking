package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PasswordResetService;
@RestController
@RequestMapping("/auth")
public class AuthControllerEmail {
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> req) {
        String result = passwordResetService.sendResetLink(req.get("email"));
        if (result.equals("null")) {
            return result;
        }
        return "";
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        passwordResetService.resetPassword(req.get("token"), req.get("newPassword"));
        return ResponseEntity.ok("Password updated");
    }
}
