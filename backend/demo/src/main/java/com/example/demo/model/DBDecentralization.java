package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Permissions")
public class DBDecentralization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_roles;

    private Long accountid;  // ID tài khoản được phân quyền
    private boolean can_add = false;
    private boolean can_edit = false;
    private boolean can_delete = false;
    public DBDecentralization() {
    }
    
    public DBDecentralization(Long id_roles, Long accountid, boolean can_add, boolean can_edit, boolean can_delete) {
        this.id_roles = id_roles;
        this.accountid = accountid;
        this.can_add = can_add;
        this.can_edit = can_edit;
        this.can_delete = can_delete;
    }

    public Long getId_roles() {
        return id_roles;
    }
    public void setId_roles(Long id_roles) {
        this.id_roles = id_roles;
    }
    public Long getAccountid() {
        return accountid;
    }
    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }
    public boolean isCan_add() {
        return can_add;
    }
    public void setCan_add(boolean can_add) {
        this.can_add = can_add;
    }
    public boolean isCan_edit() {
        return can_edit;
    }
    public void setCan_edit(boolean can_edit) {
        this.can_edit = can_edit;
    }
    public boolean isCan_delete() {
        return can_delete;
    }
    public void setCan_delete(boolean can_delete) {
        this.can_delete = can_delete;
    }
    
    
   
}
