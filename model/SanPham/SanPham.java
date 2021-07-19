package com.example.appbanhangnew.model.SanPham;

import java.io.Serializable;

public class SanPham implements Serializable {

    int idsp;
    String tensp;
    String hinhanhsp;
    String mota;
    int idloaisp;

    public SanPham(int idsp, String tensp, String hinhanhsp, String mota, int idloaisp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
        this.mota = mota;
        this.idloaisp = idloaisp;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getMota() {
        return mota;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getTensp() {
        return tensp;
    }
}