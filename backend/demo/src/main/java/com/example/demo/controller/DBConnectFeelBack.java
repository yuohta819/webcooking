package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryFeelBack;
import com.example.demo.model.DBFeedback;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/feedback")

public class DBConnectFeelBack {
    @Autowired
    private DataRepositoryFeelBack feelBack;

    @PostMapping("/save")
    public String postMethodName(@RequestBody Map<String, String> entity) {
        String name = (String) entity.get("name");
        String email = (String) entity.get("email");
        Integer accountid = Integer.parseInt(entity.get("accountid"));
        String subject = (String) entity.get("subject");
        String message = (String) entity.get("message");
        DBFeedback dFellback = new DBFeedback(name, email, subject, message, accountid,null);
        feelBack.save(dFellback);

        return "";
    }

    @GetMapping("/call")
    public List<DBFeedback> getMethodName() {
        return feelBack.findAll();
    }

    @PostMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable("id") Integer id) {
        feelBack.getUpdate(id);;
        return "";
    }
    @PostMapping("/update")
    public String postMethodNamee(@RequestBody Map<String,String> entity) {
        feelBack.getUpdateNote((String) entity.get("adminNote"), Integer.parseInt(entity.get("id")));
        
        return "";
    }
    @GetMapping("/client/{id}")
    public List<DBFeedback> getMethodName(@PathVariable("id") Integer id) {
        return feelBack.getFeed(id);
    }
    

}
