package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.DBAdmin;

public interface DataRepositoryAdmin extends JpaRepository<DBAdmin, Long> {
    
}
