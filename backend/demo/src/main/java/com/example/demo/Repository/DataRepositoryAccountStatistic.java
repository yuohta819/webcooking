package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.DTOStatistic;
import com.example.demo.model.DBStatistic;

public interface DataRepositoryAccountStatistic extends JpaRepository<DBStatistic, Long> {
    @Query("SELECT new com.example.demo.DTO.DTOStatistic(a.name,d.tongSoDon,d.tongTien,d.is_delete,d.thoiGianTao,a.username) FROM DBStatistic d JOIN DBAccount a ON a.accountid = d.accountid WHERE d.is_delete = False")
    List<DTOStatistic> getAll();
    @Transactional
    @Modifying
    @Query("INSERT INTO DBStatistic(accountid, username, tongSoDon, tongTien, is_delete, thoiGianTao) SELECT a.accountid,a.name,0,0,False, CURRENT_TIMESTAMP FROM DBAccount a")
    void insertData();
}
