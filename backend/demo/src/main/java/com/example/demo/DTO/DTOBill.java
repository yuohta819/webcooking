package com.example.demo.DTO;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.model.DBAccount;
import com.example.demo.model.DBMenu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "Bills")
public class DTOBill {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billid;

    private String name;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime time;

    // 🔹 Quan hệ tới bảng Menu
    @ManyToOne
    @JoinColumn(name = "idproduct", nullable = false)
    private DBMenu menu;

    // 🔹 Quan hệ tới bảng Account (để lấy email, username, v.v.)
    @ManyToOne
    @JoinColumn(name = "accountid", nullable = false)
    private DBAccount account;

    public DTOBill() {}

    public DTOBill(Long billid, String name, LocalDateTime time, DBMenu menu, DBAccount account) {
        this.billid = billid;
        this.name = name;
        this.time = time;
        this.menu = menu;
        this.account = account;
    }

    public Long getBillid() {
        return billid;
    }

    public void setBillid(Long billid) {
        this.billid = billid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public DBMenu getMenu() {
        return menu;
    }

    public void setMenu(DBMenu menu) {
        this.menu = menu;
    }

    public DBAccount getAccount() {
        return account;
    }

    public void setAccount(DBAccount account) {
        this.account = account;
    }
}
