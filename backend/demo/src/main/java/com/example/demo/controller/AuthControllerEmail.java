package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.service.PasswordResetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthControllerEmail {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordResetTokenRepository reset;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> req) {
        String result = passwordResetService.sendResetLink(req.get("email"));
        if (result.equals("null")) {
            return result;
        }
        return "";
    }

    @GetMapping("/check-token")
    public String getMethodName(@RequestParam String param) {
        return reset.checkToken(param);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        passwordResetService.resetPassword(req.get("token"), req.get("newPassword"));
        return ResponseEntity.ok("Password updated");
    }
}
