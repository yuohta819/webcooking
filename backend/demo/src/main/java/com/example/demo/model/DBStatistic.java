package com.example.demo.model;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Statistic")
public class DBStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountid;

    private String username;

    private Integer tongSoDon;

    private Double tongTien;

    private Boolean is_delete;

    private LocalDateTime thoiGianTao;

    // ====== Constructors ======
    public DBStatistic() {}

    public DBStatistic(Long accountid, String username, Integer tongSoDon, Double tongTien, Boolean is_delete, LocalDateTime thoiGianTao) {
        this.accountid = accountid;
        this.username = username;
        this.tongSoDon = tongSoDon;
        this.tongTien = tongTien;
        this.is_delete = is_delete;
        this.thoiGianTao = thoiGianTao;
    }

    // ====== Getters & Setters ======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getTongSoDon() {
        return tongSoDon;
    }

    public void setTongSoDon(Integer tongSoDon) {
        this.tongSoDon = tongSoDon;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public Boolean getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Boolean is_delete) {
        this.is_delete = is_delete;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }
}
