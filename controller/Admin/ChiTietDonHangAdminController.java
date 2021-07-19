package com.example.appbanhangnew.controller.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.appbanhangnew.action.Admin.DonHang.XacNhanDonHangActivity;
import com.example.appbanhangnew.action.User.DonHang.DonMuaActivity;
import com.example.appbanhangnew.controller.User.ChiTietDonHangController;
import com.example.appbanhangnew.until.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ChiTietDonHangAdminController {
    Context context;

    public ChiTietDonHangAdminController(Context context) {
        this.context = context;
    }

    public void EditInformation(int iddonhang, int trangthai){
        new HuyDonHang(iddonhang, trangthai).execute(Server.duongdaneditHuyDonHang);

    }

    class HuyDonHang extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password

        int iddonhang;
        int trangthai;

        public HuyDonHang(int iddonhang, int trangthai) {
            this.iddonhang = iddonhang;
            this.trangthai = trangthai;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iddonhang", String.valueOf(iddonhang))
                    .addFormDataPart("trangthai", String.valueOf(trangthai)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdaneditHuyDonHang).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                okhttp3.Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //nhận dữ liệu;
            //gán layout;

            Log.d("AAA", s);

            int success = 0;
            String message = "";

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            if (success == 1) {
                if(trangthai==2){
                    Toast.makeText(context,"Đã xác nhận đơn hàng!!",Toast.LENGTH_LONG).show();

                    XacNhanDonHangActivity.tabLayout.getTabAt(3).select();
                }else {
                    XacNhanDonHangActivity.tabLayout.getTabAt(0).select();
                    Toast.makeText(context,"Đã giao thành công!!",Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(context,"Đơn hàng đã hủy!!",Toast.LENGTH_LONG).show();
               // DonMuaActivity.tabLayout.getTabAt(3).select();


            } else {

            }

            super.onPostExecute(s);
        }
    }
}
