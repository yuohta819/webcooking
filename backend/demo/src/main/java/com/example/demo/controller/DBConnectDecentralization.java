package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTODecentralization;
import com.example.demo.Repository.DataRepositoryDecentralization;
import com.example.demo.model.DBDecentralization;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/account")
public class DBConnectDecentralization {
    @Autowired
    private DataRepositoryDecentralization rep;
    @GetMapping("/decentralization")
    public List<DTODecentralization> requestMethodName() {
        return rep.getAllDecentralizations();
    }

    @PostMapping("/per")
    public String postMethodName(@RequestBody Map<String, String> data) {
        String type = (String) data.get("type");
        boolean statusType = Boolean.parseBoolean(data.get("statusType"));
        int id = Integer.parseInt( data.get("accountid"));
        switch (type) {
            case "canAdd":
                rep.updateCanAdd(statusType, id);
                break;
            case "canEdit":
                rep.updateCanEdit(statusType, id);
                break;
            case "canDelete":
                rep.updateCanDelete(statusType, id);
                break;
            default:
                break;
        }

        return "";
    }

}
