package com.example.appbanhangnew.until;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Khởi tạo và cấu hình cho retrofit
    private static Retrofit retrofit = null;
    public static  Retrofit getClient(String baseurl){
        OkHttpClient builder= new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true).build();
        Gson gson= new GsonBuilder().setLenient().create();
        retrofit= new Retrofit.Builder()
                //khởi tạo nhờ url
                .baseUrl(baseurl)
                //kiểm soát những cài đặt trên OkHttp
                .client(builder)
                //Chuyển từ Json về biến java qua Gson, hỗ trợ convert cho tốt hơn
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;
    }
}
