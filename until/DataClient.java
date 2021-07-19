package com.example.appbanhangnew.until;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    //dạng file
    @Multipart
    //Gửi lên
    @POST("uploadImageToServer.php")
    //Dữa vào kiểu dữ liệu dc trả về
    Call<String> UploadImage(@Part MultipartBody.Part photo); // Phương thức cho việc lắng nghe và gửi dl.

    //Post dưới dạng chuổi
    @FormUrlEncoded
    @POST("setInformation.php")
    Call<String> InsertData(@Field("iduser") int iduser
                            ,@Field("Ten") String Ten
                            ,@Field("NgaySinh") String NgaySinh
                            ,@Field("Diachi") String Diachi
                            ,@Field("sodienthoai") String sodienthoai
                            ,@Field("Email") String Email
                            ,@Field("image") String image);



}
