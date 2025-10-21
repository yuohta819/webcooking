package com.example.demo.DTO;

public class DTOStatus {
    private Long statusid;
    private Integer money;
    private boolean status1;
    private boolean status2;
    private boolean status3;
    private String name;
    private String billid;
    private boolean is_deleted_status;
    private String so_ban;
    private Long accountid;
    private int idproduct;
    public DTOStatus() {
    }
    public DTOStatus(Long statusid, Integer money, boolean status1, boolean status2, boolean status3, String name,
            String billid, boolean is_deleted_status, String so_ban, Long accountid, int idproduct) {
        this.statusid = statusid;
        this.money = money;
        this.status1 = status1;
        this.status2 = status2;
        this.status3 = status3;
        this.name = name;
        this.billid = billid;
        this.is_deleted_status = is_deleted_status;
        this.so_ban = so_ban;
        this.accountid = accountid;
        this.idproduct = idproduct;
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
    public boolean isStatus1() {
        return status1;
    }
    public void setStatus1(boolean status1) {
        this.status1 = status1;
    }
    public boolean isStatus2() {
        return status2;
    }
    public void setStatus2(boolean status2) {
        this.status2 = status2;
    }
    public boolean isStatus3() {
        return status3;
    }
    public void setStatus3(boolean status3) {
        this.status3 = status3;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBillid() {
        return billid;
    }
    public void setBillid(String billid) {
        this.billid = billid;
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
    public Long getAccountid() {
        return accountid;
    }
    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }
    public int getIdproduct() {
        return idproduct;
    }
    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }
    
}
