package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.DTOStatistic;
import com.example.demo.Repository.DataRepositoryAccountStatistic;
import com.example.demo.model.DBStatistic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/statistic")
public class DBConnectAccountStatistic {
    @Autowired
    private DataRepositoryAccountStatistic sta;
    @GetMapping("/call")
    public List<DTOStatistic>  getMethodName() {
        List<DTOStatistic> list = sta.getAll();
        return list;
    }
    
}
