package com.example.demo.model;

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
    private String money;
    private Boolean status1;
    private Boolean status2;
    private Boolean status3;
    @Column(insertable = false, updatable = false)
    private Long accountid;
    @Column(insertable = false, updatable = false)
    private int menuid;
    private String billid;
    @ManyToOne
    @JoinColumn(name = "menuid")
    private DBMenu menu;
    @ManyToOne
    @JoinColumn(name = "accountid")
    private DBAccount account;

    public DBStatus() {
    }

    public DBStatus(Long statusid, String money, Boolean status1, Boolean status2, Boolean status3, Long accountid,
            int menuid, String billid, DBMenu menu, DBAccount account) {
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
    }

    public Long getStatusid() {
        return statusid;
    }

    public void setStatusid(Long statusid) {
        this.statusid = statusid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
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

}
