package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTODecentralization;
import com.example.demo.Repository.DataRepositoryAdmin;
import com.example.demo.Repository.DataRepositoryDecentralization;
import com.example.demo.Repository.UserSessionRepository;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBAdmin;
import com.example.demo.model.DBDecentralization;
import com.example.demo.model.DBStatus;
import com.example.demo.model.DBUserSession;
import com.example.demo.until.JwtUntil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin")
public class DBConnectAdmin {
    @Autowired
    private DataRepositoryAdmin admin;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataRepositoryDecentralization de;
    @Autowired
    private JwtUntil jwtUtil;
    @Autowired
    private UserSessionRepository sesion;

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

    @GetMapping("/save")
    public String getMethodName(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password) {

        String encoded = passwordEncoder.encode(password);
        DBAdmin result = admin.getIdAdmin(email);
        if (result == null) {
            DBAdmin newAdmin = new DBAdmin();
            newAdmin.setAccount(name);
            newAdmin.setPassword(encoded);
            newAdmin.setEmail(email);
            newAdmin.setPhone(phone);
            newAdmin.setRole("Admin");

            // Lưu admin và nhận lại entity đã có id
            DBAdmin savedAdmin = admin.save(newAdmin);

            DBDecentralization db = new DBDecentralization();
            db.setAccountid(savedAdmin.getAccountid());// dùng id vừa lưu
            de.save(db);

        } else {
            if (admin.getIdAdminn(result.getAccountid()) == null) {
                DBDecentralization db = new DBDecentralization();
                db.setAccountid(result.getAccountid());
                de.save(db);
            }
        }

        return "OK";
    }

    @GetMapping("/call")
    public List<DBAdmin> getMethodName() {
        return admin.findAll();
    }

    @GetMapping("/calll/{id}")
    public DBAdmin getMethodNamee(@PathVariable("id") Long id) {
        return admin.getCallAdmin(id);
    }

    @GetMapping("/check")
    public ResponseEntity<?> getMethodCheck(@RequestParam String name, @RequestParam String password,
            HttpServletRequest request) {
        DBAdmin infor = admin.getIdAdmin(name);
        if (infor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("account");
        }
        if (!passwordEncoder.matches(password, infor.getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password");
        }
        DTODecentralization roles = admin.getAdmin(infor.getAccountid());
        // 🔹 Sinh JWT token mới
        String token = jwtUtil.generateTokenAdmin(
                infor.getEmail(),
                infor.getAccountid(),
                infor.getAccount(),
                roles.getCanAdd(),
                roles.getCanDelete(),
                roles.getCanEdit());

        // // 🔹 Lấy IP address của client
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        DBUserSession sessionn = new DBUserSession();
        sessionn.setToken(token);
        sessionn.setAccountid(infor.getAccountid());
        sesion.save(sessionn);
        // // 🔹 Lấy thông tin thiết bị từ User-Agent header
        String deviceInfo = request.getHeader("User-Agent");

        // // 🔹 Trả về token + thông tin tài khoản
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("accountid", infor.getAccountid());
        response.put("account", infor.getAccount());
        response.put("ipAddress", ipAddress);
        response.put("deviceInfo", deviceInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token")
    public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String token) {
        // Bỏ prefix "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Kiểm tra token hợp lệ trong DB hoặc validate JWT
        DBUserSession valid = sesion.checkToken(token); // <-- tự bạn viết logic

        if (valid == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid");
        }

        return ResponseEntity.ok("valid");
    }

    @GetMapping("/auth/check-permission")
    public DBDecentralization getMethodName(HttpServletRequest request) {
        Claims decoded = decodedToken(request);
        Integer id = decoded.get("accountid", Integer.class);
        return de.getRoles(id);
    }

}
