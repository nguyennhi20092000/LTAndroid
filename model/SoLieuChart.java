package com.example.appbanhangnew.model;

import java.time.LocalDate;
import java.util.Date;

public class SoLieuChart {
    int tongtien;
    LocalDate ngaythang;

    public SoLieuChart(int tongtien, LocalDate ngaythang) {
        this.tongtien = tongtien;
        this.ngaythang = ngaythang;
    }

    public int getTongtien() {
        return tongtien;
    }

    public LocalDate getNgaythang() {
        return ngaythang;
    }
}
