package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_number")
public class DBNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long table_id;
    private String table_name;
    private int seats;
    private boolean status;
    private Integer accountid;
    private Date created_at = new Date();

    public DBNumber() {
    }

    public DBNumber(String table_name, int seats, boolean status, Integer accountid, Date created_at) {
        this.table_name = table_name;
        this.seats = seats;
        this.status = status;
        this.accountid = accountid;
        this.created_at = created_at;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Long getTable_id() {
        return table_id;
    }

    public void setTable_id(Long table_id) {
        this.table_id = table_id;
    }


}
