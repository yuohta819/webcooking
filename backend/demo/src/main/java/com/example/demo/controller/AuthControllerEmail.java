package com.example.demo.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.Repository.UserSessionRepository;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBUserSession;
import com.example.demo.service.PasswordResetService;
import com.example.demo.until.JwtUntil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthControllerEmail {
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private DataRepositoryAccount connect;
    @Autowired
    private PasswordResetTokenRepository reset;
    @Autowired
    private JwtUntil jwtService;
    @Autowired
    private DataRepositoryAccount account;
    @Value("${google.client.id}")
    private String googleClientId;
   

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
