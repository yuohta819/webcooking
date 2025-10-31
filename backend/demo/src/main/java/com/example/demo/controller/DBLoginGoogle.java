package com.example.demo.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class DBLoginGoogle {
    @GetMapping("/success")
    public Map<String, Object> getMethodName(@AuthenticationPrincipal OAuth2User principal) {
        try {
            return principal.getAttributes();
        } catch (Exception e) {
            return null;
        }
    }

}
