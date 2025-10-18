package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Status")
public class DBStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusid;
    private Integer money;
    private Boolean status1;
    private Boolean status2;
    private Boolean status3;
    @Column(insertable = false, updatable = false)
    private int accountid;
    @Column(insertable = false, updatable = false)
    private int menuid;
    private String billid;
    @ManyToOne
    @JoinColumn(name = "menuid")
    private DBMenu menu;
    @ManyToOne
    @JoinColumn(name = "accountid")
    private DBAccount account;
    private Date thoi_gian_don_hang;
    private boolean is_deleted_status;
    private String so_ban;
    public DBStatus() {
    }
    public DBStatus(Long statusid, Integer money, Boolean status1, Boolean status2, Boolean status3, int accountid,
            int menuid, String billid, DBMenu menu, DBAccount account, Date thoi_gian_don_hang,
            boolean is_deleted_status, String so_ban) {
        this.statusid = statusid;
        this.money = money;
        this.status1 = status1;
        this.status2 = status2;
        this.status3 = status3;
        this.accountid = accountid;
        this.menuid = menuid;
        this.billid = billid;
        this.menu = menu;
        this.account = account;
        this.thoi_gian_don_hang = thoi_gian_don_hang;
        this.is_deleted_status = is_deleted_status;
        this.so_ban = so_ban;
    }
    public Long getStatusid() {
        return statusid;
    }
    public void setStatusid(Long statusid) {
        this.statusid = statusid;
    }
    public Integer getMoney() {
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    public Boolean getStatus1() {
        return status1;
    }
    public void setStatus1(Boolean status1) {
        this.status1 = status1;
    }
    public Boolean getStatus2() {
        return status2;
    }
    public void setStatus2(Boolean status2) {
        this.status2 = status2;
    }
    public Boolean getStatus3() {
        return status3;
    }
    public void setStatus3(Boolean status3) {
        this.status3 = status3;
    }
    public int getAccountid() {
        return accountid;
    }
    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }
    public int getMenuid() {
        return menuid;
    }
    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }
    public String getBillid() {
        return billid;
    }
    public void setBillid(String billid) {
        this.billid = billid;
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
    public Date getThoi_gian_don_hang() {
        return thoi_gian_don_hang;
    }
    public void setThoi_gian_don_hang(Date thoi_gian_don_hang) {
        this.thoi_gian_don_hang = thoi_gian_don_hang;
    }
    public boolean isIs_deleted_status() {
        return is_deleted_status;
    }
    public void setIs_deleted_status(boolean is_deleted_status) {
        this.is_deleted_status = is_deleted_status;
    }
    public String getSo_ban() {
        return so_ban;
    }
    public void setSo_ban(String so_ban) {
        this.so_ban = so_ban;
    }
    
    
}
