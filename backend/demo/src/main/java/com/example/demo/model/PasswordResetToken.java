package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reset")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    private int accountid;
    private String token;
    private LocalDateTime expiry_date;
    private Date create_at;
    private String email;

    public PasswordResetToken() {
    }

    public PasswordResetToken(Long token_id, int accountid, String token, LocalDateTime expiry_date, Date create_at,
            String email) {
        this.token_id = token_id;
        this.accountid = accountid;
        this.token = token;
        this.expiry_date = expiry_date;
        this.create_at = create_at;
        this.email = email;
    }

    public Long getId_id() {
        return token_id;
    }

    public void setId_id(Long token_id) {
        this.token_id = token_id;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDateTime expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
