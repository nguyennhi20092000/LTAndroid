package com.example.appbanhangnew.Bridge;

public abstract class Account {
    DangNhapAPI dangNhapAPI;

    public Account(DangNhapAPI dangNhapAPI) {
        this.dangNhapAPI = dangNhapAPI;
    }
    public abstract void dangnhap();
}
