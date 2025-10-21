package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Admin")
public class DBAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountid;

    private String account;
    private String password;
    private String role;
    private String email;
    private String phone;
    private LocalDateTime createdAt = LocalDateTime.now();

    public DBAdmin() {
    }

    public DBAdmin(Long accountid, String account, String password, String role, String email, String phone,
            LocalDateTime createdAt) {
        this.accountid = accountid;
        this.account = account;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

   
}
