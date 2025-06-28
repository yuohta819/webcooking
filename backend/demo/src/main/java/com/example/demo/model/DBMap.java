package com.example.demo.model;

public class DBMap {
    private DBMenu menu;
    private int total;

    public DBMap() {
    }

    public DBMap(DBMenu menu, int total) {
        this.menu = menu;
        this.total = total;
    }

    public DBMenu getMenu() {
        return menu;
    }

    public void setMenu(DBMenu menu) {
        this.menu = menu;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
