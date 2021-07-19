package com.example.appbanhangnew.controller.Singleton;

import com.example.appbanhangnew.model.User;
import android.content.SharedPreferences;


public class InitializedSingleton {

    private static InitializedSingleton instance;
   // SharedPreferences sharedPreferences;

    private InitializedSingleton(){}

    public static InitializedSingleton getInstance(){
        if(instance == null){
            instance = new InitializedSingleton();
        }
        return instance;
    }

    public User getUser(SharedPreferences sharedPreferences) {

        User user = new User(sharedPreferences.getInt("iduser", 0), sharedPreferences.getString("sodienthoai", ""), sharedPreferences.getString("password", ""),
                sharedPreferences.getString("ten", ""), sharedPreferences.getString("ngaysinh", ""), sharedPreferences.getString("diachi", ""),
                sharedPreferences.getString("email", ""), sharedPreferences.getString("image", ""));
        return user;
    }
}