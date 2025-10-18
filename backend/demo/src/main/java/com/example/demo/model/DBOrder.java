package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 @Entity
 @Table(name = "Orders")
public class DBOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer order_id;
    private Integer accountid;
    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "people")
    private Integer people;

    @Column(name = "payment_method", length = 50)
    private String payment_method;
    private double total;

    @Column(name = "create_at")
    private LocalDateTime create_at;

    @Column(name = "is_delete")
    private Boolean is_delete = false;

    public DBOrder() {
    }

    public DBOrder(Integer accountid, String fullname, String phone, Integer people,
            String payment_method, double total, LocalDateTime create_at, Boolean is_delete) {
        this.accountid = accountid;
        this.fullname = fullname;
        this.phone = phone;
        this.people = people;
        this.payment_method = payment_method;
        this.total = total;
        this.create_at = create_at;
        this.is_delete = is_delete;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public Boolean getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Boolean is_delete) {
        this.is_delete = is_delete;
    }
    
}
