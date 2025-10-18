package com.example.demo.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Menu]")

public class DBMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;
    private String name;
    private String describe;
    private float price;
    @Column(name = "isDeleted")
    private Boolean isDeleted;
    @Column(name = "isTime")
    private Date isTime;

    public DBMenu() {
    }

    public DBMenu(Long id, String img, String name, String describe, float price, boolean isDeleted,
            Date isTime) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.describe = describe;
        this.price = price;
        this.isDeleted = isDeleted;
        this.isTime = isTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getIsTime() {
        return isTime;
    }

    public void setIsTime(Date isTime) {
        this.isTime = isTime;
    }

   
}
