package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTOHistory;
import com.example.demo.DTO.DTONumberOfTimes;
import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.model.DBCart;
import com.example.demo.until.JwtUntil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/cart")
public class DBConnectCart {
    @Autowired
    private DataRepositoryInfor cart;
    @Autowired
    private DataRepositoryAccount account;
    @Autowired
    private JwtUntil jwtUtil;

    @GetMapping("/account")
    public List<DBCart> getMethodName() {
        return cart.findAllBill();

    }

    @GetMapping("/dashboardproduct")
    public List<DTONumberOfTimes> getNumberOfTimes() {
        return cart.getNumberOfTimes();
    }

    @GetMapping("/allprice")
    public List<Integer> getMethodNameAllPrice() {
        List<Integer> list = new ArrayList<>();
        list.add(cart.getAllPrice());
        list.add(cart.getCountOrder());
        list.add(cart.getUserTotal());
        return list;
    }

    @GetMapping("/history")
    public List<DTOHistory> getMethodName(HttpServletRequest request) {
        Claims claim = decodedToken(request);
        Integer accountid = claim.get("accountid", Integer.class);
        return cart.getHistory(accountid);
    }

    @GetMapping("/total")
    public ResponseEntity<?> getTotal(HttpServletRequest request) {
        try {
            // 🔍 Giải mã JWT token để lấy claim
            Claims claim = decodedToken(request);
            Integer accountid = claim.get("accountid", Integer.class);
            String provider = claim.get("provider", String.class);
            // 🛒 Tính tổng giỏ hàng
            Integer total = cart.getTotal(accountid);
            if (total == null)
                total = 0;

            // 🧑 Lấy tên người dùng
            String name = account.getName(accountid);

            // ✅ Trả về JSON gọn gàng
            Map<String, Object> response = new HashMap<>();
            response.put("total", total);
            response.put("name", name);
            response.put("provider",  provider);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // ⚠️ Trường hợp lỗi (token sai, user không tồn tại,...)
            Map<String, String> error = new HashMap<>();
            error.put("error", "Token không hợp lệ hoặc người dùng không tồn tại.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
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

}
