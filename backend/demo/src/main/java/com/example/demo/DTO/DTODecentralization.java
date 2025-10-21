package com.example.demo.DTO;

public class DTODecentralization {
    private String account;
    private Boolean canAdd;
    private Boolean canEdit;
    private Boolean canDelete;
    private Integer accountid;
    public DTODecentralization(String account, Boolean canAdd, Boolean canEdit, Boolean canDelete, Integer accountid) {
        this.account = account;
        this.canAdd = canAdd;
        this.canEdit = canEdit;
        this.canDelete = canDelete;
        this.accountid = accountid;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public Boolean getCanAdd() {
        return canAdd;
    }
    public void setCanAdd(Boolean canAdd) {
        this.canAdd = canAdd;
    }
    public Boolean getCanEdit() {
        return canEdit;
    }
    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
    public Boolean getCanDelete() {
        return canDelete;
    }
    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
    public Integer getAccountid() {
        return accountid;
    }
    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }
    
    
   
}
