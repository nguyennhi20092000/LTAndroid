package com.example.appbanhangnew.Bridge;

import android.content.Context;

import com.example.appbanhangnew.controller.User_Admin.AccountController;

public class Nguoimua implements DangNhapAPI{

    @Override
    public void Dangnhap(String sdt, String password, Context context) {
        AccountController controller=new AccountController(context);
        controller.Account_Controller(2,sdt,password,context);
    }
}
