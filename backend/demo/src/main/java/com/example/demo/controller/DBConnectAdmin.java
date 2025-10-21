package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTODecentralization;
import com.example.demo.Repository.DataRepositoryAdmin;
import com.example.demo.Repository.DataRepositoryDecentralization;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBAdmin;
import com.example.demo.model.DBDecentralization;
import com.example.demo.model.DBStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin")
public class DBConnectAdmin {
    @Autowired
    private DataRepositoryAdmin admin;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataRepositoryDecentralization de;

    @GetMapping("/save")
    public String getMethodName(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password) {

        String encoded = passwordEncoder.encode(password);
        Integer result = admin.getIdAdmin(email);
        if (result == null) {
            DBAdmin newAdmin = new DBAdmin();
            newAdmin.setAccount(name);
            newAdmin.setPassword(encoded);
            newAdmin.setEmail(email);
            newAdmin.setPhone(phone);
            newAdmin.setRole("Admin");

            // Lưu admin và nhận lại entity đã có id
            DBAdmin savedAdmin = admin.save(newAdmin);

            DBDecentralization db = new DBDecentralization();
            db.setAccountid(savedAdmin.getAccountid());// dùng id vừa lưu
            de.save(db);

        } else {
            if (admin.getIdAdminn(result) == null) {
                DBDecentralization db = new DBDecentralization();
                db.setAccountid(result.longValue());
                de.save(db);
            }
        }

        return "OK";
    }

    @GetMapping("/call")
    public List<DBAdmin> getMethodName() {
        return admin.findAll();
    }

    @GetMapping("/calll/{id}")
    public DBAdmin getMethodNamee(@PathVariable("id") Long id) {
        return admin.getCallAdmin(id);
    }

    @GetMapping("/check")
    public DTODecentralization getMethodCheck(@RequestParam String name, @RequestParam String password) {
        List<DBAdmin> list = admin.findAll();
        for (DBAdmin x : list) {
            if (name.equals(x.getAccount()) && passwordEncoder.matches(password, x.getPassword())) {
                System.out.println(x.getAccountid());
                return admin.getAdmin(x.getAccountid());
            }
        }
        return null;
    }

}
