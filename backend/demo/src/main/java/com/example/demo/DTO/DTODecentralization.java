package com.example.demo.DTO;

public class DTODecentralization {
    private String account;
    private Boolean canAdd;
    private Boolean canEdit;
    private Boolean canDelete;
    public DTODecentralization(String account, Boolean canAdd, Boolean canEdit, Boolean canDelete) {
        this.account = account;
        this.canAdd = canAdd;
        this.canEdit = canEdit;
        this.canDelete = canDelete;
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
    
   
}
