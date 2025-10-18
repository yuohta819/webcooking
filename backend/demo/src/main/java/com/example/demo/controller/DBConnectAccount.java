package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryAccountStatistic;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBBill;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/account")
public class DBConnectAccount {
    @Autowired
    private DataRepositoryAccount connect;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataRepositoryAccountStatistic sta;

    @GetMapping("/check")
    public List<DBAccount> getMethodName(@RequestParam String account, @RequestParam String password) {
        List<DBAccount> list = connect.findEmaill(account);
        List<DBAccount> listtmp = new ArrayList<>();
        for (DBAccount x : list) {
            if (account.equals(x.getUsername()) && passwordEncoder.matches(password, x.getPassword())) {
                listtmp.add(x);
                return listtmp;
            }
        }
        return null;
    }

    @GetMapping("/save")
    public Integer getSaveAccount(@RequestParam String name, @RequestParam String account,
            @RequestParam String password, @RequestParam String phone) {
        DBAccount acc = new DBAccount();
        boolean check = checkAccount(account);
        if (!check) {
            if (password.equalsIgnoreCase("google")) {
                System.out.println("test");
                acc.setUsername(account);
                acc.setName(name);
                acc.setPassword("Google(Not Password)");
                acc.setPhone("Google(Not Phone)");
                acc.setIs_time(new Date());
                connect.save(acc);
                sta.insertData();
                
                return connect.getIdAccountGoogle(account);
            } else {
                String decoded = passwordEncoder.encode(password);
                acc.setUsername(account);
                acc.setName(name);
                acc.setPassword(decoded);
                acc.setPhone(phone);
                acc.setIs_time(new Date());
                connect.save(acc);
                sta.insertData();
                return 0;
            }
        } else {
            System.out.println(connect.getIdAccountGoogle(account));
            return connect.getIdAccountGoogle(account);
        }
    }

    @PostMapping("/change")
    public String getMethodName(@RequestBody Map<String, String> data) {
        String oldPassword = (String) data.get("oldPassword");
        String newPassword = (String) data.get("newPassword");
        int accountid = Integer.parseInt(data.get("accountid"));
        String passwordDB = connect.getOldPassword(accountid);
        if (!passwordEncoder.matches(oldPassword, passwordDB)) {
            return "Error password";
        }
        connect.updatePassword(passwordEncoder.encode(newPassword), LocalDateTime.now(), accountid);
        return "success";
    }

    public String findName(Long id) {
        List<DBAccount> list = connect.findAllInfor(id);
        for (DBAccount x : list) {
            return x.getName();
        }
        return "no";
    }

    public boolean checkAccount(String account) {
        List<DBAccount> list = connect.findAll();
        for (DBAccount x : list) {
            if (x.getUsername().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public Long getId(String account) {
        List<DBAccount> list = connect.findAll();
        for (DBAccount x : list) {
            if (x.getUsername().equals(account)) {
                return x.getAccountid();
            }
        }
        return null;
    }
}
