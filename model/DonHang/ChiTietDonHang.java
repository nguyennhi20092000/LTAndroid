package com.example.appbanhangnew.model.DonHang;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.SanPham;

public class ChiTietDonHang {

    int idchitietdonhang, iddonhang,idsp,idchitietsp,soluong,gia;
    String tensp,tenchitietsp,hinhanhsp, ngaythang;

    public ChiTietDonHang(int idchitietdonhang, int iddonhang, int idsp, int idchitietsp, int soluong, int gia, String tensp, String tenchitietsp, String hinhanhsp, String ngaythang) {
        this.idchitietdonhang = idchitietdonhang;
        this.iddonhang = iddonhang;
        this.idsp = idsp;
        this.idchitietsp = idchitietsp;
        this.soluong = soluong;
        this.gia = gia;
        this.tensp = tensp;
        this.tenchitietsp = tenchitietsp;
        this.hinhanhsp = hinhanhsp;
        this.ngaythang = ngaythang;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public int getIdchitietsp() {
        return idchitietsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public int getGia() {
        return gia;
    }

    public String getTenchitietsp() {
        return tenchitietsp;
    }

    public String getTensp() {
        return tensp;
    }

    public int getIdchitietdonhang() {
        return idchitietdonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public void setIdchitietdonhang(int idchitietdonhang) {
        this.idchitietdonhang = idchitietdonhang;
    }
}
