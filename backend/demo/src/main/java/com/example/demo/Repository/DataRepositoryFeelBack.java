package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBFeedback;

import jakarta.transaction.Transactional;

public interface DataRepositoryFeelBack extends JpaRepository<DBFeedback, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE DBFeedback d SET d.isDeleted = TRUE WHERE d.idfeedback = :id ")
    void getUpdate(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE DBFeedback d SET d.admin_note = :note WHERE d.idfeedback = :id")
    void getUpdateNote(@Param("note") String note, @Param("id") Integer id);
    @Query("SELECT d FROM DBFeedback d WHERE d.accountid = :id ")
    List<DBFeedback> getFeed(@Param("id") Integer id);
}
