package com.example.demo.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryMenu;
import com.example.demo.Repository.DataRepositoryStatus;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBMenu;
import com.example.demo.model.DBStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/status")
public class DBConnectStatus {
    @Autowired
    private DataRepositoryStatus connect;
    @Autowired
    private DBConnectAccount accountget;
    @Autowired
    private DataRepositoryAccount account;
    @Autowired
    private DataRepositoryMenu menu;

    @PostMapping("/save")
    @CrossOrigin(origins = "http://localhost:5173") // Hoặc "*"
    public String postMethodName(@RequestBody Map<String, Object> map) {
        String account = (String) map.get("account");
        List<Integer> list = (List<Integer>) map.get("status");
        List<Double> listmoney = (List<Double>) map.get("money");
        SecureRandom random = new SecureRandom();
        String ma = "#";
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(10);
            ma += number;
        }
        Long id = accountget.getId(account);
        for (int i = 0; i < list.size(); i++) {
            DBStatus status = new DBStatus();
            status.setAccountid(id);
            status.setMoney(listmoney.get(i) + "");
            status.setMenuid(list.get(i));
            status.setStatus1(false);
            status.setStatus2(false);
            status.setStatus3(false);
            status.setBillid(ma);
            connect.save(status);
        }
        return "";
    }

    @GetMapping("/find")
    public List<DBStatus> getMethodName() {
        List<DBStatus> list = connect.findAll();
        return list;
    }

    @PostMapping("/update")
    public String postMethodName(@RequestParam Long id, @RequestParam Boolean check, @RequestParam String type) {
        if (type.equals("status1")) {
            connect.updateStatus1(id, check);
        }
        if (type.equals("status2")) {
            connect.updateStatus2(id, check);
        }
        if (type.equals("status3")) {
            connect.updateStatus3(id, check);
        }
        return "";
    }
    @PostMapping("/follow")
    public List<DBStatus> getMethodName(@RequestBody DBAccount username) {
        Map<Integer, List<DBStatus>> map = new HashMap<>();
        List<DBAccount> list = account.findId(username.getUsername());
        Long id = null;
        for (DBAccount x : list) {
            id = x.getAccountid();
        }
        List<DBStatus> list1 = connect.findByAccountid(id);
        List<DBMenu> listmenu = menu.findAll();
        return list1;
    }
    

}
