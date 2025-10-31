package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryFeelBack;
import com.example.demo.model.DBFeedback;
import com.example.demo.until.JwtUntil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/feedback")

public class DBConnectFeelBack {
    @Autowired
    private DataRepositoryFeelBack feelBack;
    @Autowired
    private JwtUntil jwtUntil;

    @PostMapping("/save")
    public String postMethodName(@RequestBody Map<String, String> entity) {
        String name = (String) entity.get("name");
        String email = (String) entity.get("email");
        Integer accountid = Integer.parseInt(entity.get("accountid"));
        String subject = (String) entity.get("subject");
        String message = (String) entity.get("message");
        DBFeedback dFellback = new DBFeedback(name, email, subject, message, accountid, null);
        feelBack.save(dFellback);

        return "";
    }

    @GetMapping("/call")
    public List<DBFeedback> getMethodName() {
        return feelBack.findAll();
    }

    @PostMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable("id") Integer id) {
        feelBack.getUpdate(id);
        ;
        return "";
    }

    @PostMapping("/update")
    public String postMethodNamee(@RequestBody Map<String, String> entity) {
        feelBack.getUpdateNote((String) entity.get("adminNote"), Integer.parseInt(entity.get("id")));

        return "";
    }

    @GetMapping("/client")
    public List<DBFeedback> getMethodName(HttpServletRequest request) {
        Claims decoded = decodedToken(request);
        Integer id = decoded.get("accountid", Integer.class);
        return feelBack.getFeed(id);
    }

    public Claims decodedToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("authorization");
            String token = authHeader.substring(7); // bỏ chữ Bearer + khoảng trắng
            if (!jwtUntil.validateToken(token)) {
                return null;
            }
            Claims claims = jwtUntil.extractAllClaims(token);
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

}
