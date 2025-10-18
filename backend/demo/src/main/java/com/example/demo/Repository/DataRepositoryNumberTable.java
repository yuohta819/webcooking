package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBNumber;

import jakarta.transaction.Transactional;

public interface DataRepositoryNumberTable extends JpaRepository<DBNumber, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE DBNumber d SET d.accountid = NULL, d.status = false WHERE d.table_id = :id")
    void updateDeleted(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE DBNumber d SET d.accountid = :accountid, d.status = TRUE WHERE d.table_id = :id")
    void updateSeats(@Param("id") int id, @Param("accountid") int account);
}
