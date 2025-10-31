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
import com.example.demo.until.JwtUntil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

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
    @Autowired
    private JwtUntil jwtUtil;

    @PostMapping("/save")
    public String postMethodName(@RequestBody Map<String, Object> map) {
        List<?> list = (List<?>) map.get("status");
        List<?> listmoney = (List<?>) map.get("money");

        // ✅ accountid có thể là String hoặc Number, nên xử lý mềm dẻo:
        Object accountidObj = map.get("accountid");
        long accountid;
        if (accountidObj instanceof Number) {
            accountid = ((Number) accountidObj).longValue();
        } else {
            accountid = Long.parseLong(accountidObj.toString());
        }

        // ✅ tương tự cho "people"
        Object peopleObj = map.get("people");
        int people = (peopleObj instanceof Number)
                ? ((Number) peopleObj).intValue()
                : Integer.parseInt(peopleObj.toString());

        SecureRandom random = new SecureRandom();
        String ma = "#";
        for (int i = 0; i < 6; i++) {
            ma += random.nextInt(10);
        }

        for (int i = 0; i < list.size(); i++) {
            DBStatus status = new DBStatus();
            DBAccount acc = new DBAccount();
            acc.setAccountid(accountid);
            status.setAccount(acc);

            DBMenu menu = new DBMenu();
            Object value = list.get(i);

            if (value instanceof Number) {
                menu.setId(((Number) value).longValue());
                infor.deletedid(((Number) value).longValue());
            } else {
                menu.setId(Long.parseLong(value.toString()));
            }

            // Ép kiểu cẩn thận khi lấy listmoney
            Object moneyVal = listmoney.get(i);
            int money = (moneyVal instanceof Number)
                    ? ((Number) moneyVal).intValue()
                    : Integer.parseInt(moneyVal.toString());

            status.setMenu(menu);
            status.setMoney(money);
            status.setStatus1(false);
            status.setStatus2(false);
            status.setStatus3(false);
            status.setBillid(ma);
            status.setThoi_gian_don_hang(new Date());
            status.setSo_ban(connect.getNameTable(accountid).replaceAll("\\D+", ""));
            connect.deleteCart(menu.getId());
            connect.save(status);
        }
        
        return "OK";
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
                bill.save(new DBBills(name, idproduct, account, id));
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
        DBAccount list = account.findId(username.getUsername());
        List<DBStatus> list1 = connect.findByAccountid(list.getAccountid());
        return list1;
    }

    @GetMapping("/client/callstatus")
    public List<DBStatus> getMethodName(HttpServletRequest request) {
        Claims claim = decodedToken(request);
        Integer accountid = claim.get("accountid", Integer.class);
        List<DBStatus> db = connect.getCallStatus(accountid);
        return db;
    }

    public Claims decodedToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("authorization");
            String token = authHeader.substring(7); // bỏ chữ Bearer + khoảng trắng
            if (!jwtUtil.validateToken(token)) {
                return null;
            }
            Claims claims = jwtUtil.extractAllClaims(token);
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

}
