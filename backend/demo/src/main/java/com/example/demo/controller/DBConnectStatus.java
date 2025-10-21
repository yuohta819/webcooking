package com.example.demo.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTOStatus;
import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryBill;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.Repository.DataRepositoryMenu;
import com.example.demo.Repository.DataRepositoryStatus;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBBills;
import com.example.demo.model.DBMenu;
import com.example.demo.model.DBStatus;
import com.example.demo.service.OrderService;

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
    private DataRepositoryAccount account;
    @Autowired
    private DataRepositoryInfor infor;
    @Autowired
    private DataRepositoryBill bill;

    @PostMapping("/save")
    public String postMethodName(@RequestBody Map<String, Object> map) {
        String name = (String) map.get("name");
        String account = (String) map.get("account");
        List<String> list = (List<String>) map.get("status");
        List<Integer> listmoney = (List<Integer>) map.get("money");
        String accountid = (String) map.get("accountid");
        Integer people = (Integer) map.get("people");
        SecureRandom random = new SecureRandom();
        String ma = "#";
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(10);
            ma += number;
        }
        for (int i = 0; i < list.size(); i++) {
            DBStatus status = new DBStatus();
            DBAccount acc = new DBAccount();
            acc.setAccountid(Long.parseLong(accountid));
            status.setAccount(acc);

            DBMenu menu = new DBMenu();

            Object value = list.get(i);
            if (value instanceof Integer) {
                menu.setId(((Integer) value).longValue());
                infor.deletedid(((Integer) value).longValue());
            } else if (value instanceof String) {
                menu.setId(Long.parseLong((String) value));
            }
            status.setMenu(menu);
            status.setMoney(listmoney.get(i));
            status.setStatus1(false);
            status.setStatus2(false);
            status.setStatus3(false);
            status.setBillid(ma);
            status.setThoi_gian_don_hang(new Date());
            status.setSo_ban(connect.getNameTable(Integer.parseInt(accountid)).replaceAll("\\D+", "") + "");
            connect.save(status);
        }

        return "";
    }

    @GetMapping("/find")
    public List<DTOStatus> getMethodName() {
        List<DTOStatus> list = connect.getAllStatus();
        return list;
    }

    @PostMapping("/update")
    public String updateStatus(@RequestBody Map<String, Object> body) {
        Long id = Long.parseLong(body.get("id").toString());
        Boolean check = Boolean.parseBoolean(body.get("check").toString());
        String type = body.get("type").toString();
        int account = Integer.parseInt(body.get("account").toString());
        String name = body.get("name").toString();
        int idproduct = Integer.parseInt(body.get("idproduct").toString());

        if (type.equals("status1")) {
            connect.updateStatus1(id, check);
        } else if (type.equals("status2")) {
            connect.updateStatus2(id, check);
        } else if (type.equals("status3")) {
            connect.updateStatus3(id, check);
            connect.updateBill();
            connect.updateTongTien();
            if (check) {
                bill.save(new DBBills(name,idproduct,account,id));
            } else {
                bill.deleteBill(id);
            }
        } else if (type.equals("delete")) {
            connect.updateDelete(id, check);
        }

        return "OK";
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
        return list1;
    }

    @GetMapping("/client/callstatus")
    public List<DBStatus> getMethodName(@RequestParam String accountid) {
        List<DBStatus> db = connect.getCallStatus(Integer.parseInt(accountid));
        return db;
    }

}
