package com.example.appbanhangnew.until;

public class APIUtils {
    public static final String Base_Url="http://192.168.56.1/server/";
    public  static DataClient getData(){

        //Dùng để gửi và nhận dữ liệu
        return  RetrofitClient.getClient(Server.duongdanUploadImageToServer).create(DataClient.class);
    }
}
