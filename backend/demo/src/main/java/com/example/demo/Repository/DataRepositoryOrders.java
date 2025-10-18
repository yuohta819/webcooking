package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DBOrder;

import jakarta.transaction.Transactional;

public interface DataRepositoryOrders extends JpaRepository<DBOrder, Long> {
    @Modifying
    @Transactional
    @Query(value = """
            WITH TopTable AS (
                SELECT TOP 1 table_id
                FROM table_number
                WHERE status = 0
                ORDER BY
                    CASE WHEN seats >= :people THEN 0 ELSE 1 END,
                    ABS(seats - :people)
            )
            UPDATE table_number
            SET accountid = :accountid,
                status = 1
            WHERE table_id IN (SELECT table_id FROM TopTable)
            """, nativeQuery = true)

    int assignTableToCustomer(@Param("accountid") int accountid, @Param("people") int people);
    @Query("SELECT d.accountid FROM DBNumber d WHERE d.accountid = :id")
    Integer getNumber(@Param("id") Integer id);
}
