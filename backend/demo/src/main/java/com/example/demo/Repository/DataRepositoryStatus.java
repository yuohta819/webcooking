package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.DTOHistory;
import com.example.demo.DTO.DTOStatus;
import com.example.demo.model.DBStatus;
import java.util.List;

public interface DataRepositoryStatus extends JpaRepository<DBStatus, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE DBStatus d SET d.status1 = :check WHERE d.statusid = :id")
    void updateStatus1(@Param("id") Long id, @Param("check") Boolean check);

    @Modifying
    @Transactional
    @Query("UPDATE DBStatus d SET d.status2 = :check WHERE d.statusid = :id")
    void updateStatus2(@Param("id") Long id, @Param("check") Boolean check);

    @Modifying
    @Transactional
    @Query("UPDATE DBStatus d SET d.status3 = :check WHERE d.statusid = :id")
    void updateStatus3(@Param("id") Long id, @Param("check") Boolean check);

    @Modifying
    @Transactional
    @Query("UPDATE DBStatistic d SET d.tongSoDon = (SELECT COUNT(o) FROM DBStatus o WHERE o.accountid = d.accountid AND o.status3 = TRUE)")

    void updateBill();

    @Transactional
    @Modifying
    @Query("UPDATE DBStatistic d SET d.tongTien = (SELECT COALESCE(SUM(m.price), 0) FROM DBStatus o JOIN DBMenu m ON o.menuid = m.id WHERE o.accountid = d.accountid AND o.status3 = TRUE)")
    void updateTongTien();

    @Modifying
    @Transactional
    @Query("UPDATE DBStatus d SET d.is_deleted_status = :check WHERE d.statusid = :id")
    void updateDelete(@Param("id") Long id, @Param("check") Boolean check);

    List<DBStatus> findByAccountid(Long accountid);

    @Query("SELECT new com.example.demo.DTO.DTOStatus(d1.statusid,d1.money, d1.status1, d1.status2, d1.status3, d2.name, d1.billid,d1.is_deleted_status,d1.so_ban,d2.accountid,d1.menuid) "
            +
            "FROM DBStatus d1 JOIN DBAccount d2 ON d1.accountid = d2.accountid")
    List<DTOStatus> getAllStatus();

    @Query("SELECT d FROM DBStatus d WHERE d.accountid = :id AND d.is_deleted_status = False")
    List<DBStatus> getCallStatus(@Param("id") int id);

    @Query("SELECT SUM(d.money) FROM DBStatus d WHERE d.accountid = :id AND d.is_deleted_status = False")
    Integer getTotal(@Param("id") int id);
    @Query("SELECT d.table_name FROM DBNumber d WHERE d.accountid = :id")
    String getNameTable(@Param("id") Long id);
    @Transactional
    @Modifying
    @Query("DELETE FROM DBCart WHERE idproduct = :id")
    void deleteCart(@Param("id") Long id);
   
}
