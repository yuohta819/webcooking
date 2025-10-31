package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.example.demo.DTO.DTODecentralization;
import com.example.demo.model.DBDecentralization;

import jakarta.transaction.Transactional;

public interface DataRepositoryDecentralization extends JpaRepository<DBDecentralization, Long> {
      @Query(value = "SELECT a.account, p.can_add, p.can_edit, p.can_delete,a.accountid " +
                  "FROM Permissions p JOIN Admin a ON a.accountid = p.accountid", nativeQuery = true)
      List<DTODecentralization> getAllDecentralizations();

      @Query("SELECT d FROM DBDecentralization d WHERE d.accountid = :id")
      DBDecentralization getRoles(@Param("id") Integer id);

      // Cập nhật quyền "Thêm"
      @Transactional
      @Modifying
      @Query(value = "UPDATE p SET p.can_add = :statusType " +
                  "FROM Permissions p " +
                  "JOIN Admin a ON a.accountid = p.accountid  " +
                  "WHERE a.accountid = :id", nativeQuery = true)
      void updateCanAdd(@Param("statusType") boolean statusType,
                  @Param("id") int id);

      // Cập nhật quyền "Sửa"
      @Transactional
      @Modifying
      @Query(value = "UPDATE p SET p.can_edit = :statusType " +
                  "FROM Permissions p " +
                  "JOIN Admin a ON a.accountid = p.accountid  " +
                  "WHERE a.accountid = :id", nativeQuery = true)
      void updateCanEdit(@Param("statusType") boolean statusType,
                  @Param("id") int id);

      // Cập nhật quyền "Xoá"
      @Transactional
      @Modifying
      @Query(value = "UPDATE p SET p.can_delete = :statusType " +
                  "FROM Permissions p " +
                  "JOIN Admin a ON a.accountid = p.accountid " +
                  "WHERE a.accountid = :id", nativeQuery = true)
      void updateCanDelete(@Param("statusType") boolean statusType,
                  @Param("id") int id);

}
