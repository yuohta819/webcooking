
package com.example.demo.Repository;

import java.beans.Transient;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.DBMenu;
import com.example.demo.model.DBNumber;

public interface DataRepositoryMenu extends JpaRepository<DBMenu, Long> {
    @Query("SELECT m FROM DBMenu m WHERE m.isDeleted = False")
    List<DBMenu> findAllIs();

    @Query("SELECT m FROM DBMenu m WHERE m.isDeleted = True")
    List<DBMenu> findAllIsDeleted();

    @Query("SELECT m FROM DBMenu m WHERE m.id = :id")
    List<DBMenu> findId(@Param("id") Long id);

    List<DBMenu> findById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE DBMenu d SET d.isDeleted = False WHERE id = :id")
    void updateDelete(@Param("id") int id);

    @Query(value = """
                SELECT TOP 3 m.*
                FROM Menu m
                JOIN Status d ON d.menuid = m.id
                WHERE d.status3 = 1 AND d.accountid = :id
                GROUP BY m.id, m.name, m.price, m.describe, m.img, m.is_time, m.is_deleted
                ORDER BY COUNT(d.menuid) DESC
            """, nativeQuery = true)
    List<DBMenu> findSuggest(@Param("id") Integer id);

}
