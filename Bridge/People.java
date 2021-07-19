package com.example.appbanhangnew.Bridge;

import android.content.Context;

public class People extends Account{
    String sdt, password;
    Context context;

    public People(String sdt, String password, Context context, DangNhapAPI dangNhapAPI) {
        super(dangNhapAPI);
        this.sdt = sdt;
        this.password = password;
        this.context = context;

    }

    @Override
    public void dangnhap() {
        dangNhapAPI.Dangnhap(sdt,password,context);
    }
}
