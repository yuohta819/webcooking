package com.example.demo.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class DTONumberOfTimes {
    private String name;
    private Long numberOffTime;
    public DTONumberOfTimes() {
    }

    public DTONumberOfTimes(String name, Long numberOffTime) {
        this.name = name;
        this.numberOffTime = numberOffTime;
    }

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    public Long getNumberOffTime() {
        return numberOffTime;
    }
    public void setNumberOffTime(Long numberOffTime) {
        this.numberOffTime = numberOffTime;
    }
    
}
