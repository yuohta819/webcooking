package com.example.demo.DTO;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class DTOStatistic {
    private String username;
    private Integer tongSoDon;
    private Double tongTien;
    private Boolean is_delete;
    private LocalDateTime thoiGianTao;
    private String accountUsername;

    // ✅ Constructor khớp với query
    public DTOStatistic(String username, Integer tongSoDon, Double tongTien,
                        Boolean is_delete, LocalDateTime thoiGianTao, String accountUsername) {
        this.username = username;
        this.tongSoDon = tongSoDon;
        this.tongTien = tongTien;
        this.is_delete = is_delete;
        this.thoiGianTao = thoiGianTao;
        this.accountUsername = accountUsername;
    }

    // Getter / Setter (không bắt buộc, nhưng nên có)
    public String getUsername() { return username; }
    public Integer getTongSoDon() { return tongSoDon; }
    public Double getTongTien() { return tongTien; }
    public Boolean getIs_delete() { return is_delete; }
    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public String getAccountUsername() { return accountUsername; }
}
