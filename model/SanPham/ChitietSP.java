package com.example.appbanhangnew.model.SanPham;

import java.io.Serializable;

public class ChitietSP implements Serializable {

    int idsp;
    int idChiTietSP;
    String tenChiTietsp;
    String hinhanhChiTietsp;
    int gia;
    public ChitietSP(){}

    public ChitietSP(int idsp, int idChiTietSP, String tenChiTietsp, String hinhanhChiTietsp, int gia) {
        this.idsp = idsp;
        this.idChiTietSP = idChiTietSP;
        this.tenChiTietsp = tenChiTietsp;
        this.hinhanhChiTietsp = hinhanhChiTietsp;
        this.gia = gia;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getGia() {
        return gia;
    }

    public int getIdChiTietSP() {
        return idChiTietSP;
    }

    public String getHinhanhChiTietsp() {
        return hinhanhChiTietsp;
    }

    public String getTenChiTietsp() {
        return tenChiTietsp;
    }

    public void setHinhanhChiTietsp(String hinhanhChiTietsp) {
        this.hinhanhChiTietsp = hinhanhChiTietsp;
    }

    public void setIdChiTietSP(int idChiTietSP) {
        this.idChiTietSP = idChiTietSP;
    }

    public void setTenChiTietsp(String tenChiTietsp) {
        this.tenChiTietsp = tenChiTietsp;
    }
}
