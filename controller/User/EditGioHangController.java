package com.example.appbanhangnew.controller.User;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.appbanhangnew.until.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditGioHangController {
    Context context;

    public EditGioHangController(Context context) {
        this.context = context;
    }
    public void EditDioHang(int idgiohang, int soluong, String ngaythang){
        new EdittoServer(idgiohang,soluong,ngaythang).execute(Server.duongdanEditGiohang);

    }
    class EdittoServer extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int idgiohang, soluong;
        String ngaythang;

        public EdittoServer( int idgiohang, int soluong, String ngaythang) {

            this.idgiohang = idgiohang;
            this.soluong = soluong;
            this.ngaythang = ngaythang;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idgiohang", String.valueOf(idgiohang))
                    .addFormDataPart("soluong", String.valueOf(soluong))
                    .addFormDataPart("ngaythang", ngaythang).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanEditGiohang).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            int success = 0;
            //String message="";

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                //message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            if (success == 1) {
                Toast.makeText(context, "Sản phẩm đã sửa trong giỏ hàng", Toast.LENGTH_LONG).show();
            } else {

            }
            super.onPostExecute(s);
        }
    }
}
