package com.example.demo.model;

import java.time.LocalDateTime;

import com.cloudinary.provisioning.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "user_session")
public class DBUserSession {
    @Id
    private Long accountid; // Liên kết với bảng Account
    private String token; // Lưu JWT token
    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo session
    public DBUserSession() {
    }
    public DBUserSession(Long accountid, String token, LocalDateTime createdAt) {
        this.accountid = accountid;
        this.token = token;
        this.createdAt = createdAt;
    }
    public Long getAccountid() {
        return accountid;
    }
    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
   
}
