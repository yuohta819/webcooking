package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.DBInfor;

public interface DataRepositoryBills extends JpaRepository<DBInfor,Long> {
    @Query("SELECT d FROM DBInfor d")
List<DBInfor> findAllBill(); // tên gì cũng được nếu có @Query
}
