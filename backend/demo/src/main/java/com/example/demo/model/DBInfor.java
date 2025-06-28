package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
public class DBInfor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billid;
    private String name;
    @Column(insertable=false, updatable=false)
    private int idproduct;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime time;
    private String account;
    @ManyToOne
    @JoinColumn(name="idproduct")
    private DBMenu menu;
    public DBMenu getMenu() {
        return menu;
    }

    public void setMenu(DBMenu menu) {
        this.menu = menu;
    }

    public DBInfor() {
    }

    public DBInfor(Long billid, String name, int idproduct, LocalDateTime time, String account) {
        this.billid = billid;
        this.name = name;
        this.idproduct = idproduct;
        this.time = time;
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

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
