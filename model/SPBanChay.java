package com.example.appbanhangnew.model;

public class SPBanChay {
    int idsp, soluongban;
    String tensp, hinhanhsp;

    public SPBanChay(int idsp, int soluongban, String tensp, String hinhanhsp) {
        this.idsp = idsp;
        this.soluongban = soluongban;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
    }

    public String getTensp() {
        return tensp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public int getSoluongban() {
        return soluongban;
    }
}
