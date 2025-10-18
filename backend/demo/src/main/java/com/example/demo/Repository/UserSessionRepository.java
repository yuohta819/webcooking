package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBAccount;
import com.example.demo.model.DBUserSession;

public interface UserSessionRepository extends JpaRepository<DBUserSession, Integer> {
    Optional<DBUserSession> findByToken(String token);
    Optional<DBUserSession> findByAccountid(Long accountid);
    @Query("SELECT d.accountid FROM DBAccount d WHERE d.username = :email")
    Integer getIdAccount(@Param("email") String email);
}
