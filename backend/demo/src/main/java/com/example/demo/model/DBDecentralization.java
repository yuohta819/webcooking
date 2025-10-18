package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="Permissions")
public class DBDecentralization {
    @Id
    private Long iddecentralization;
    private int id;
    private boolean canAdd;
    private boolean canEdit;
    private boolean canDelete;
    public DBDecentralization() {
    }
    public Long getIddecentralization() {
        return iddecentralization;
    }
    public void setIddecentralization(Long iddecentralization) {
        this.iddecentralization = iddecentralization;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isCanAdd() {
        return canAdd;
    }
    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }
    public boolean isCanEdit() {
        return canEdit;
    }
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    public boolean isCanDelete() {
        return canDelete;
    }
    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
    
}
