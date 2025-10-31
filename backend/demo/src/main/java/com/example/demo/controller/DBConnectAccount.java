package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.provisioning.Account;
import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryAccountStatistic;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.Repository.UserSessionRepository;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBAdmin;
import com.example.demo.model.DBCart;
import com.example.demo.model.DBUserSession;
import com.example.demo.until.JwtUntil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/account")
public class DBConnectAccount {
    @Autowired
    private DataRepositoryAccount connect;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataRepositoryAccountStatistic sta;
    @Value("${server.name}")
    private String name;
    @Autowired
    private JwtUntil jwtUtil;
    @Autowired
    private UserSessionRepository session;

    @GetMapping("/check")
    public ResponseEntity<?> getMethodName(@RequestParam String account, @RequestParam String password,
            HttpServletRequest request) {

        DBAccount infor = connect.findId(account);
        if (infor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account");
        }
        if (!passwordEncoder.matches(password, infor.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password");
        }
        // 🔹 Sinh JWT token mới
        String token = jwtUtil.generateToken(
                infor.getUsername(),
                infor.getAccountid(),
                infor.getName());

        // // 🔹 Lấy IP address của client
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        // // 🔹 Lấy thông tin thiết bị từ User-Agent header
        String deviceInfo = request.getHeader("User-Agent");

        // // 🔹 Trả về token + thông tin tài khoản
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("accountid", infor.getAccountid());
        response.put("account", infor.getUsername());
        response.put("name", infor.getName());
        response.put("ipAddress", ipAddress);
        response.put("deviceInfo", deviceInfo);
        DBUserSession se = new DBUserSession();
        se.setAccountid(infor.getAccountid());
        se.setToken(token);
        session.save(se);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/save")
    public Integer getSaveAccount(@RequestParam String name, @RequestParam String account,
            @RequestParam String password, @RequestParam String phone) {
        DBAccount acc = new DBAccount();
        boolean check = checkAccount(account);
        if (!check) {
            if (password.equalsIgnoreCase("google")) {
                acc.setUsername(account);
                acc.setName(name);
                acc.setPassword("Google(Not Password)");
                acc.setPhone("Google(Not Phone)");
                acc.setIs_time(new Date());
                connect.save(acc);
                sta.insertData();

                return connect.getIdAccountGoogle(account);
            } else {
                String decoded = passwordEncoder.encode(password);
                acc.setUsername(account);
                acc.setName(name);
                acc.setPassword(decoded);
                acc.setPhone(phone);
                acc.setIs_time(new Date());
                connect.save(acc);
                sta.insertData();
                return 0;
            }
        } else {
            return connect.getIdAccountGoogle(account);
        }
    }

    @PostMapping("/change")
    public String getMethodName(@RequestBody Map<String, String> data) {
        String oldPassword = (String) data.get("oldPassword");
        String newPassword = (String) data.get("newPassword");
        int accountid = Integer.parseInt(data.get("accountid"));
        String passwordDB = connect.getOldPassword(accountid);
        if (!passwordEncoder.matches(oldPassword, passwordDB)) {
            return "Error password";
        }
        connect.updatePassword(passwordEncoder.encode(newPassword), LocalDateTime.now(), accountid);
        return "success";
    }

    @PostMapping("admin/update/{id}")
    public String postMethodName(@RequestBody Map<String, String> entity, @PathVariable("id") Integer id) {
        String account = entity.get("account");
        String email = entity.get("email");
        if (connect.checkEmail(email) != null) {
            return "error";
        }
        connect.updateAdmin(account, email, id);

        return "";
    }

    @GetMapping("/delete/token")
    public void getMethodName(HttpServletRequest request) {
        String authHeader = request.getHeader("authorization");
        String token = authHeader.substring(7); // bỏ chữ Bearer + khoảng trắng
        connect.deleteToken(token);
    }

    public Claims decodedToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("authorization");
            String token = authHeader.substring(7); // bỏ chữ Bearer + khoảng trắng
            if (!jwtUtil.validateToken(token)) {
                return null;
            }
            Claims claims = jwtUtil.extractAllClaims(token);
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    public String findName(Long id) {
        List<DBAccount> list = connect.findAllInfor(id);
        for (DBAccount x : list) {
            return x.getName();
        }
        return "no";
    }

    public boolean checkAccount(String account) {
        List<DBAccount> list = connect.findAll();
        for (DBAccount x : list) {
            if (x.getUsername().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public Long getId(String account) {
        List<DBAccount> list = connect.findAll();
        for (DBAccount x : list) {
            if (x.getUsername().equals(account)) {
                return x.getAccountid();
            }
        }
        return null;
    }
}
