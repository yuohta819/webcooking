package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.DBStatus;
import java.util.List;


public interface DataRepositoryStatus extends JpaRepository<DBStatus,Long> {
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
    List<DBStatus> findByAccountid(Long accountid);
}
