package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBInfor;
import com.example.demo.model.DBMenu;

import jakarta.transaction.Transactional;

public interface DataRepositoryInfor extends JpaRepository<DBInfor, Long> {
    @Query("SELECT a FROM DBInfor a WHERE a.billid = :id")
    List<DBInfor> findAllInfor(@Param("id") Long id);

    @Query("SELECT a FROM DBInfor a JOIN a.menu WHERE a.account=:account")
    List<DBInfor> findUsersId(@Param("account") String account);

    @Modifying
    @Transactional
    @Query("DELETE FROM DBInfor d WHERE d.idproduct = :id ")
    void deletedid(@Param("id") Long id);
}