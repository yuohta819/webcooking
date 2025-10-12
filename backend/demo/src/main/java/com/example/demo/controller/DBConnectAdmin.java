package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryAdmin;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin")
public class DBConnectAdmin {
    @Autowired
    private DataRepositoryAdmin admin;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/save")
    public String getMethodName(@RequestParam String name, @RequestParam String password) {
        DBAdmin a = new DBAdmin();
        String decoded = passwordEncoder.encode(password);
        a.setAccount(name);
        a.setPassword(decoded);
        admin.save(a);
        return new String();
    }
    @GetMapping("/check")
    public List<DBAdmin> getMethodCheck(@RequestParam String name, @RequestParam String password) {
        List<DBAdmin> list = admin.findAll();
        List<DBAdmin> listtmp = new ArrayList<>();
        for (DBAdmin x : list) {
            if (name.equals(x.getAccount()) && passwordEncoder.matches(password, x.getPassword())) {
                listtmp.add(x);
                return listtmp;
            }
        }
        return null;
    }
    

}
