package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Account")

public class DBAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private Long accountid;
    private String username;
    private String password;
    private String name;
    private Date is_time;
    private String phone;
    public DBAccount() {
    }
    public DBAccount(Long accountid, String username, String password, String name, Date is_time, String phone) {
        this.accountid = accountid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.is_time = is_time;
        this.phone = phone;
    }
    public Long getAccountid() {
        return accountid;
    }
    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getIs_time() {
        return is_time;
    }
    public void setIs_time(Date is_time) {
        this.is_time = is_time;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

}
