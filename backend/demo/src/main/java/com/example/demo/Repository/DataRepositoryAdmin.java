package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.DTO.DTODecentralization;
import com.example.demo.model.DBAdmin;
import com.example.demo.model.DBDecentralization;
import com.example.demo.model.DBStatus;

public interface DataRepositoryAdmin extends JpaRepository<DBAdmin, Long> {
  @Query(value = "SELECT a FROM DBAdmin a WHERE a.email = :account")
  DBAdmin getIdAdmin(@Param("account") String account);

  @Query(value = "SELECT a.accountid FROM DBDecentralization a WHERE a.accountid = :id")
  Integer getIdAdminn(@Param("id")Long id);

  @Query(value = "SELECT a FROM DBAdmin a WHERE a.accountid = :account")
  DBAdmin getCallAdmin(@Param("account") Long account);

  @Query(value = "SELECT a.account, p.can_add AS canAdd, p.can_edit AS canEdit, p.can_delete AS canDelete, p.accountid " +
      "FROM Permissions p JOIN Admin a ON a.accountid = p.accountid  WHERE p.accountid = :id", nativeQuery = true)
  DTODecentralization getAdmin(@Param("id") Long id);
}
