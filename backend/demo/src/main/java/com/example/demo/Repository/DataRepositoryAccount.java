package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBAccount;
import com.example.demo.model.DBInfor;

public interface DataRepositoryAccount extends JpaRepository<DBAccount, Long> {
    List<DBAccount> findAll();
    @Query("SELECT a FROM DBAccount a WHERE a.id = :id")
    List<DBAccount> findAllInfor(@Param("id")  Long id);
}
