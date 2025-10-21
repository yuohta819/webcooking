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
public class DBBills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billid;
    private String name;
    private int idproduct;
    private LocalDateTime time = LocalDateTime.now();
    private Integer accountid;
    private Long statusid;
    public DBBills() {
    }
    public DBBills(String name, int idproduct, Integer accountid, Long statusid) {
        this.name = name;
        this.idproduct = idproduct;
        this.accountid = accountid;
        this.statusid = statusid;
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
    public Integer getAccountid() {
        return accountid;
    }
    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }
    public Long getStatusid() {
        return statusid;
    }
    public void setStatusid(Long statusid) {
        this.statusid = statusid;
    }
    
}
