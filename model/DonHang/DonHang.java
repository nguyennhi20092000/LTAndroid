package com.example.appbanhangnew.model.DonHang;

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;

import java.util.ArrayList;

public class DonHang {
    int iduser;
    int iddonhang;
    String ngaythang;
    String sodienthoai;
    String diachi;
    String email;
    int tongtien;
    int trangthai;
    ArrayList<ChiTietDonHang> arrayListchitietdonhang;

    // 1: đặt hàng
    // 2: xác nhận
    // 3: nhận hàng.
    //4: hủy hàng.


    public DonHang(int iduser, int iddonhang, String ngaythang, String sodienthoai, String diachi, String email, int tongtien, int trangthai, ArrayList<ChiTietDonHang> arrayListchitietdonhang) {
        this.iduser = iduser;
        this.iddonhang = iddonhang;
        this.ngaythang = ngaythang;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.email = email;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
        this.arrayListchitietdonhang = arrayListchitietdonhang;
    }

    public int getIduser() {
        return iduser;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public String getDiachi() {
        return diachi;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public int getTongtien() {
        return tongtien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public ArrayList<ChiTietDonHang> getArrayListchitietdonhang() {
        return arrayListchitietdonhang;
    }
    public void AddChiTietDonHang(ChiTietDonHang chiTietDonHang){
        arrayListchitietdonhang.add(chiTietDonHang);
    }

    public void setArrayListchitietdonhang(ArrayList<ChiTietDonHang> arrayListchitietdonhang) {
        this.arrayListchitietdonhang = arrayListchitietdonhang;
    }
}