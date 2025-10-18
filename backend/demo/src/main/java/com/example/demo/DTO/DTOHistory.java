package com.example.demo.DTO;

import java.util.Date;

public class DTOHistory {
    private Long statusid;
    private boolean status3;
    private float price;
    private Date time;
    private String img;
    private String name;
    public DTOHistory(Long statusid, boolean status3, float price, Date time, String img, String name) {
        this.statusid = statusid;
        this.status3 = status3;
        this.price = price;
        this.time = time;
        this.img = img;
        this.name = name;
    }
    public Long getStatusid() {
        return statusid;
    }
    public void setStatusid(Long statusid) {
        this.statusid = statusid;
    }
    public boolean isStatus3() {
        return status3;
    }
    public void setStatus3(boolean status3) {
        this.status3 = status3;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
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
    
    
}
