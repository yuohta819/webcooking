package com.example.demo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.DataRepositoryInfor;
import com.example.demo.Repository.DataRepositoryMenu;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBBill;
import com.example.demo.model.DBMap;
import com.example.demo.model.DBMenu;
import com.example.demo.model.DBStatus;

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
    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "products"));
            String imageUrl = (String) uploadResult.get("secure_url");
            return ResponseEntity.ok(Map.of("url", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Upload failed"));
        }
    }

    @GetMapping("/menu")
    public List<DBMenu> getListMenu() {
        List<DBMenu> listmenu = menu.findAllIs();
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
            DBBill bill = new DBBill();
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
        List<DBBill> list = infor.findUsersId(account);
        Map<DBMenu, Integer> map = new LinkedHashMap<>();
        for (DBBill x : list) {
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
        m.setIsDeleted(false);
        m.setIsTime(new Date());
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

    @GetMapping("/deleted")
    public List<DBMenu> getMethodName() {
        List<DBMenu> listmenudeleted = menu.findAllIsDeleted();
        return listmenudeleted;
    }

    @PostMapping("/restore/{id}")
    public String postMethodName(@PathVariable int id) {
        menu.updateDelete(id);
        return "";
    }
    @GetMapping("/suggest")
    public List<DBMenu> getMethodNamee(@RequestParam String id) {
        return menu.findSuggest(Integer.parseInt(id));
    }
    

}
