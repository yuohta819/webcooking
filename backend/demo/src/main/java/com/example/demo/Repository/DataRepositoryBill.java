package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.DTO.DTOBill;
import com.example.demo.model.DBBills;
import com.example.demo.model.DBCart;

import jakarta.transaction.Transactional;

public interface DataRepositoryBill extends JpaRepository<DBBills, Long> {
    @Query("SELECT d FROM DBBills d")
    List<DBBills> findAllBill(); // tên gì cũng được nếu có @Query

    @Transactional
    @Modifying
    @Query("DELETE FROM DBBills d WHERE d.statusid = :id")
    void deleteBill(@Param("id") Long id);

    // ✅ Lấy tất cả hóa đơn cùng thông tin menu
    @Query("SELECT b,a.name FROM DTOBill b JOIN b.account a")

    List<DTOBill> getAll();
}
