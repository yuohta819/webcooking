package com.example.demo.until;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.UserSessionRepository;
import com.example.demo.model.DBUserSession;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUntil jwtUtil;
    @Autowired
    private DataRepositoryAccount accountRepo;
    @Autowired
    private UserSessionRepository sessionRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        try { // 🚫 Bỏ qua kiểm tra token cho các endpoint công khai
            if (path.startsWith("/api/") || path.startsWith("/category/") ||
                    path.startsWith("/account/") || path.startsWith("/status/") || path.startsWith("/cart/")
                    || path.startsWith("/token/only") || path.startsWith("/login/oauth2/")
                    || path.startsWith("/admin/check")|| path.startsWith("/number/") 
                    || path.startsWith("/bill/") || path.startsWith("/statistic/")
                     || path.startsWith("/feedback/")) {
                        
                chain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("authorization");
            String username1 = request.getParameter("account");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.getWriter().write("{\"error\": \"Token invalid or expired\"}");
                return;
            }

            final String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 🔹 1. báo lỗi
                response.setContentType("application/json;charset=UTF-8"); // 🔹 2. kiểu JSON + encoding
                response.getWriter().write("{\"error\": \"Token invalid or expired\"}"); // 🔹 3. nội dung JSON
                response.getWriter().flush(); // 🔹 4. đảm bảo dữ liệu thực sự gửi đi
                response.getWriter().close(); // kết thúc luồng xuất
                return; // 🔹 5. dừng filter
            }
            if (!jwtUtil.validateToken(token, username1) && 
            !path.startsWith("/admin/token") && !path.startsWith("/admin/auth/check-permission") &&
            !path.startsWith("/admin/infor" )) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 🔹 1. báo lỗi
                response.setContentType("application/json;charset=UTF-8"); // 🔹 2. kiểu JSON + encoding
                response.getWriter().write("{\"error\": \"Token invalid or expired\"}"); // 🔹 3. nội dung JSON
                response.getWriter().flush(); // 🔹 4. đảm bảo dữ liệu thực sự gửi đi
                response.getWriter().close(); // kết thúc luồng xuất
                return; // 🔹 5. dừng filter
            }
            if (path.startsWith("/check/token")) {
                return;
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
