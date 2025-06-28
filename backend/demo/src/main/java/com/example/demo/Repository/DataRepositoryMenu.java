
package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBMenu;


public interface DataRepositoryMenu extends JpaRepository<DBMenu,Long> {
    List<DBMenu> findAll();
    @Query("SELECT m FROM DBMenu m WHERE m.id = :id")
    List<DBMenu> findId(@Param("id") Long id);
    List<DBMenu> findById(int id);
    
}


