package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryBills;
import com.example.demo.model.DBInfor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/bill")
public class DBConnectBill {
    @Autowired
    private DataRepositoryBills bill;
    @GetMapping("/account")
    @CrossOrigin(origins = "http://localhost:5173") // Hoặc "*"
    public List<DBInfor> getMethodName() {
        List<DBInfor> infor = bill.findAllBill();
        return infor;
    }
    
}
