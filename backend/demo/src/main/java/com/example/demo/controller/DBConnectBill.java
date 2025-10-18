package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTOHistory;
import com.example.demo.DTO.DTONumberOfTimes;
import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.model.DBBill;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/bill")
public class DBConnectBill {
    @Autowired
    private DataRepositoryInfor bill;
    @Autowired
    private DataRepositoryAccount account;
    @GetMapping("/account")
    
    public List<DBBill> getMethodName() {
        return bill.findAllBill();
  
    }
    @GetMapping("/dashboardproduct")
    public List<DTONumberOfTimes> getNumberOfTimes() {
        return bill.getNumberOfTimes();
    }
    @GetMapping("/allprice")
    public List<Integer>  getMethodNameAllPrice() {
        List<Integer> list = new ArrayList<>();
        list.add(bill.getAllPrice());
        list.add(bill.getCountOrder());
        list.add(bill.getUserTotal());
        return list;
    }
    @GetMapping("/history")
    public List<DTOHistory> getMethodName(@RequestParam String param) {
        return bill.getHistory(Integer.parseInt(param));
    }
    @GetMapping("/total")
    public List<String> getMethodName1(@RequestParam String param) {
        List<String> list = new ArrayList<>();
        Integer total = bill.getTotal(Integer.parseInt(param));
        if (total == null) {
            total = 0;
        }
        list.add(total + "");
        list.add(account.getName(Integer.parseInt(param)));
        return list;
    }
    
    
    
}
