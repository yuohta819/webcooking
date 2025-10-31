package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.DataRepositoryNumberTable;
import com.example.demo.Repository.DataRepositoryOrders;
import com.example.demo.Repository.DataRepositoryStatus;
import com.example.demo.model.DBNumber;
import com.example.demo.model.DBOrder;
import com.example.demo.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/number")
public class DBConnectNumberTable {
    @Autowired
    private DataRepositoryNumberTable number;
    @Autowired
    private DataRepositoryOrders orderRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/call")
    public List<DBNumber> getMethodName() {
        return number.findAll();
    }

    @PostMapping("/payment/zalopay")
    public String postMethodName(@RequestBody Map<String, Object> entity) {
        String account = (String) entity.get("account");
        Integer accountid = Integer.parseInt(entity.get("accountid").toString());
        String fullname = (String) entity.get("fullname");
        String phone = (String) entity.get("phone");
        Integer people = Integer.parseInt(entity.get("people").toString());
        String payment = (String) entity.get("payment");
        Double total = Double.parseDouble(entity.get("total").toString());
        // ✅ Ép kiểu danh sách sản phẩm
        List<Map<String, Object>> products = (List<Map<String, Object>>) entity.get("products");

        DBOrder order = new DBOrder(
                accountid,
                fullname,
                phone,
                people,
                payment,
                total,
                LocalDateTime.now(),
                false);
        orderRepository.save(order);
        Integer result = orderRepository.getNumber(accountid);
        
        if (result == null) {
            System.out.println(result + "test");
            orderRepository.assignTableToCustomer(accountid, people);
        } 

        // --- Gọi gửi mail ---
        orderService.createOrder(fullname, account, total.intValue(), products);
        return "OK";
    }

    @GetMapping("/delete/{id}")
    public String getMethodName(@PathVariable("id") String param) {
        number.updateDeleted(Integer.parseInt(param));
        return new String();
    }

    @PostMapping("/update")
    public String postMethodNamee(@RequestBody Map<String, Integer> entity) {
        number.updateSeats(entity.get("id"), entity.get("accountid"));

        return new String();
    }

    @PostMapping("/add")
    public String postMethodName(@RequestBody DBNumber entity) {
        number.save(entity);
        return "";
    }

}
