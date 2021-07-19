package com.example.appbanhangnew.controller.User_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.appbanhangnew.action.User_Admin.Account.AccountActivity;
import com.example.appbanhangnew.until.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DoiMatKhauController {
    Context context;
    public DoiMatKhauController(Context context) {
        this.context=context;
    }
    public void EditPassword(int iduser,String newpassword){
        if (newpassword.length()>0) {
            //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT);
            new PostToserver(iduser,newpassword,context).execute(Server.duongdanEditPassword);



        } else {
            Toast.makeText(context, "Thông tin chưa nhập đủ", Toast.LENGTH_SHORT);
        }
    }
    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        String password;
        int iduser;
        Context context;

        public PostToserver(int iduser,String password,Context context) {

            this.iduser = iduser;
            this.context=context;
            this.password=password;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser", String.valueOf(iduser))
                    .addFormDataPart("password", password).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanEditPassword).post(requestBody).build();
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
                SharedPreferences sharedPreferences;
                SharedPreferences.Editor editor;
                sharedPreferences=context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent= new Intent(context, AccountActivity.class);
                context.startActivity(intent);
                Activity activity=(Activity) context;
                activity.finish();
                //setSharedPreferences(editor,iduser,sodienthoai,password,hoten,ngaysinh,diachi,email,image);

            } else {

            }

            super.onPostExecute(s);
        }
    }

}
