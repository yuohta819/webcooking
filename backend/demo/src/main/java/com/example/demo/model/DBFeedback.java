package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "Feedback")
public class DBFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idfeedback;
    private String name;
    private String email;
    private String subject;
    private String message;
    private Boolean isDeleted = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Integer accountid;
    private String admin_note;
    public DBFeedback() {
    }
    public DBFeedback(String name, String email, String subject, String message, Integer accountid, String admin_note) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.accountid = accountid;
        this.admin_note = admin_note;
    }
    public Integer getIdfeedback() {
        return idfeedback;
    }
    public void setIdfeedback(Integer idfeedback) {
        this.idfeedback = idfeedback;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Boolean getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getAccountid() {
        return accountid;
    }
    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }
    public String getAdmin_note() {
        return admin_note;
    }
    public void setAdmin_note(String admin_note) {
        this.admin_note = admin_note;
    }
    
}
