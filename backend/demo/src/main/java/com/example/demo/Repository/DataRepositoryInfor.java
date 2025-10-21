package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.DTO.DTOHistory;
import com.example.demo.DTO.DTONumberOfTimes;
import com.example.demo.model.DBCart;
import com.example.demo.model.DBMenu;

import jakarta.transaction.Transactional;

public interface DataRepositoryInfor extends JpaRepository<DBCart, Long> {
    @Query("SELECT a FROM DBCart a WHERE a.cartid = :id")
    List<DBCart> findAllBill(@Param("id") Long id);

    @Query("SELECT a FROM DBCart a JOIN a.menu WHERE a.account=:account")
    List<DBCart> findUsersId(@Param("account") String account);

    @Modifying
    @Transactional
    @Query("UPDATE DBMenu m SET m.isDeleted = True WHERE m.id = :id ")
    void deletedid(@Param("id") Long id);

    @Query("SELECT d FROM DBCart d")
    List<DBCart> findAllBill(); // tên gì cũng được nếu có @Query

    DTONumberOfTimes number = new DTONumberOfTimes();

    @Query("SELECT new com.example.demo.DTO.DTONumberOfTimes(m.name, CAST(COUNT(b) AS long)) " +
            "FROM DBBills b JOIN DBMenu m ON  b.idproduct = m.id GROUP BY m.name")

    List<DTONumberOfTimes> getNumberOfTimes();

    @Query("SELECT SUM(m.price) FROM DBCart b JOIN DBMenu m ON b.idproduct = m.id ")
    int getAllPrice();

    @Query("SELECT COUNT(*) FROM DBCart b ")
    int getCountOrder();

    @Query(value = """
                SELECT
                       COUNT(name) AS total
                FROM (
                    SELECT b.name, COUNT(*) AS SoLanTrungLap
                    FROM Bills b
                    GROUP BY b.name
                ) AS temp
            """, nativeQuery = true)
    int getUserTotal();

    @Query("SELECT new com.example.demo.DTO.DTOHistory(d1.statusid,d1.status3,m.price,d1.thoi_gian_don_hang,m.img,m.name) FROM DBStatus d1 JOIN DBMenu m ON d1.accountid = :id AND d1.menuid = m.id WHERE d1.status3 = True")
    List<DTOHistory> getHistory(@Param("id") int id);
    @Query("SELECT SUM(s.money) FROM DBStatus s WHERE s.accountid = :id AND s.status3 = True")
    Integer getTotal(@Param("id") int id);

}