package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBInfor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/account")
public class DBConnectAccount {
    @Autowired
    private DataRepositoryAccount connect;
    @Autowired
    private DataRepositoryInfor connectinfor;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/check")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<DBAccount> getMethodName(@RequestParam String account, @RequestParam String password) {
        List<DBAccount> list = connect.findAll();
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
    public boolean getSaveAccount(@RequestParam String name, @RequestParam String account,
            @RequestParam String password) {
        DBAccount acc = new DBAccount();
        boolean check = checkAccount(account);
        if (!check) {
            if (password.equalsIgnoreCase("google")) {
                acc.setUsername(account);
                acc.setName(name);
                acc.setPassword("Google(Not Password)");
                connect.save(acc);
                return true;
            } else {
                String decoded = passwordEncoder.encode(password);
                acc.setUsername(account);
                acc.setName(name);
                acc.setPassword(decoded);
                connect.save(acc);
                return true;
            }
        }
        return false;
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
