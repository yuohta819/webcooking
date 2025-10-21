package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTOBill;
import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryBill;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.model.DBBills;
import com.example.demo.model.DBCart;

@RestController

@RequestMapping("bill")
public class DBConnectBill {
    @Autowired
    private DataRepositoryBill bill;
    @Autowired
    private DataRepositoryAccount account;
    @GetMapping("/call")
    
    public List<DTOBill> getMethodName() {
        return bill.getAll();
  
    }
}
