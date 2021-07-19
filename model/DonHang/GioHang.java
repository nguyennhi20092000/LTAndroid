package com.example.appbanhangnew.model.DonHang;

import java.io.Serializable;

public class GioHang  implements Serializable{
    int iduser, idgiohang, idsp,idchitietsp,soluong,gia;
    String tensp,tenchitietsp,hinhanhsp, ngaythang;
    boolean check=false;

    public GioHang(int iduser, int idgiohang, int idsp, int idchitietsp, int soluong, String tensp, String tenchitietsp, String hinhanhsp, String ngaythang,int gia) {
        this.iduser = iduser;
        this.idgiohang = idgiohang;
        this.idsp = idsp;
        this.idchitietsp = idchitietsp;
        this.soluong = soluong;
        this.tensp = tensp;
        this.tenchitietsp = tenchitietsp;
        this.hinhanhsp = hinhanhsp;
        this.ngaythang = ngaythang;
        this.gia=gia;
    }

    public String getTensp() {
        return tensp;
    }

    public String getTenchitietsp() {
        return tenchitietsp;
    }

    public int getGia() {
        return gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
    public boolean getCheck(){
        return check;
    }

    public int getIdsp() {
        return idsp;
    }

    public int getIdchitietsp() {
        return idchitietsp;
    }

    public int getIdgiohang() {
        return idgiohang;
    }
}
