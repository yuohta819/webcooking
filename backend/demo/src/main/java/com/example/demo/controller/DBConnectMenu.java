package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.Repository.DataRepositoryMenu;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBInfor;
import com.example.demo.model.DBMap;
import com.example.demo.model.DBMenu;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class DBConnectMenu {
    @Autowired
    private DataRepositoryMenu menu;
    @Autowired
    private DataRepositoryAccount account;
    @Autowired
    private DataRepositoryInfor infor;

    @GetMapping("/menu")
    @CrossOrigin(origins = "http://localhost:5173")
    public List<DBMenu> getListMenu() {
        List<DBMenu> listmenu = menu.findAll();
        return listmenu;
    }

    @GetMapping("/detail/{id}")
    public List<DBMenu> getDetail(@PathVariable Long id) {
        List<DBMenu> listmenu = menu.findId(id);
        return listmenu;
    }

    @GetMapping("/cart")
    public String getCart(@RequestParam Long id, @RequestParam String name, @RequestParam int count,
            @RequestParam String account) {
        for (int i = 0; i < count; i++) {
            DBInfor bill = new DBInfor();
            DBMenu menu1 = menu.findById(id).orElseThrow();
            bill.setMenu(menu1);
            bill.setName(name);
            bill.setAccount(account);
            infor.save(bill);
        }
        return "no";
    }

    @GetMapping("/infor/{account}")
    public List<DBMap> getInfor(@PathVariable String account) {
        List<DBInfor> list = infor.findUsersId(account);
        Map<DBMenu, Integer> map = new LinkedHashMap<>();
        for (DBInfor x : list) {
            if (map.containsKey(x.getMenu())) {
                int cnt = map.get(x.getMenu());
                cnt = cnt + 1;
                map.put(x.getMenu(), cnt);
            } else {
                map.put(x.getMenu(), 1);
            }

        }
        List<DBMap> result = map.entrySet().stream().map(entry -> new DBMap(entry.getKey(), entry.getValue())).toList();
        return result;
    }

    @PostMapping("/create")
    public String createProduct(@RequestBody DBMenu menu) {
        DBMenu m = new DBMenu();
        m.setDescribe(menu.getDescribe());
        m.setImg(menu.getImg());
        m.setName(menu.getName());
        m.setPrice(menu.getPrice());
        this.menu.save(m);
        return "";
    }

    @PostMapping("/edit")
    public String postMethodName(@RequestBody DBMenu menuu) {
        return menu.findById(menuu.getId())
                .map(product -> {
                    product.setName(menuu.getName());
                    product.setImg(menuu.getImg());
                    product.setDescribe(menuu.getDescribe());
                    product.setPrice(menuu.getPrice());
                    menu.save(product);
                    return "yes";
                })
                .orElse("not!");
    }

    @GetMapping("/delete/{id}")
    public boolean getDelete(@PathVariable Long id) {
        infor.deletedid(id);
        return true;
    }

}
