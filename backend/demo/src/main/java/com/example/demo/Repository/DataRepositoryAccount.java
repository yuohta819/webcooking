package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.DBAccount;

public interface DataRepositoryAccount extends JpaRepository<DBAccount, Long> {

    @Query("SELECT a FROM DBAccount a WHERE a.id = :id")
    List<DBAccount> findAllInfor(@Param("id") Long id);

    @Query("SELECT a FROM DBAccount a WHERE a.username = :username")
    DBAccount findId(@Param("username") String username);

    @Query("SELECT a.name FROM DBAccount a WHERE a.accountid = :id")
    String getName(@Param("id") int id);

    @Query("SELECT a.password FROM DBAccount a WHERE a.accountid = :id")
    String getOldPassword(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE DBAccount d SET d.password = :password, d.is_time = :time WHERE d.accountid = :id")
    void updatePassword(@Param("password") String password,
            @Param("time") LocalDateTime time,
            @Param("id") int id);

    @Query("SELECT d FROM DBAccount d WHERE d.username = :email AND d.password != 'Google'")
    Optional<DBAccount> findEmail(@Param("email") String email);

    @Query("SELECT d FROM DBAccount d WHERE d.username = :email ")
    List<DBAccount> findEmaill(@Param("email") String email);

    Optional<DBAccount> findByUsername(String username); // username là trường bạn dùng để đăng nhập

    @Query("SELECT d.accountid FROM DBAccount d WHERE d.username = :email")
    Integer getIdAccountGoogle(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE DBAdmin\r\n" + //
            "   SET account = :account\r\n" + //
            "      ,email = :email\r\n" + //
            " WHERE accountid = :id")
    void updateAdmin(@Param("account") String account, @Param("email") String email, @Param("id") Integer id);

    @Query("SELECT d.accountid FROM DBAdmin d WHERE d.email=:email")
    Integer checkEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM DBUserSession WHERE token = :token")
    void deleteToken(@Param("token") String token);
}
