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
   @Query(value = "SELECT a.account, p.canAdd, p.canEdit, p.canDelete " +
         "FROM Permissions p JOIN Admin a ON a.id = p.id", nativeQuery = true)
   List<DTODecentralization> getAllDecentralizations();

   // Cập nhật quyền "Thêm"
   @Transactional
   @Modifying
   @Query(value = "UPDATE p SET p.canAdd = :statusType " +
         "FROM Permissions p " +
         "JOIN Admin a ON a.id = p.id " +
         "WHERE a.account = :name", nativeQuery = true)
   void updateCanAdd(@Param("statusType") boolean statusType,
         @Param("name") String name);

   // Cập nhật quyền "Sửa"
   @Transactional
   @Modifying
   @Query(value = "UPDATE p SET p.canEdit = :statusType " +
         "FROM Permissions p " +
         "JOIN Admin a ON a.id = p.id " +
         "WHERE a.account = :name", nativeQuery = true)
   void updateCanEdit(@Param("statusType") boolean statusType,
         @Param("name") String name);

   // Cập nhật quyền "Xoá"
   @Transactional
   @Modifying
   @Query(value = "UPDATE p SET p.canDelete = :statusType " +
         "FROM Permissions p " +
         "JOIN Admin a ON a.id = p.id " +
         "WHERE a.account = :name", nativeQuery = true)
   void updateCanDelete(@Param("statusType") boolean statusType,
         @Param("name") String name);

}
