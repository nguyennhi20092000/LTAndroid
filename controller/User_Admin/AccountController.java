package com.example.appbanhangnew.controller.User_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.model.User;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

public class AccountController {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    User user;
    int ID_USER;
    Context context;

    public AccountController(Context context) {
       this.context=context;
    }
    public void setTIL(TextInputEditText txtsodienthoai, TextInputLayout TILsodienthoai,TextInputEditText txtpassword, TextInputLayout TILpassword){
        txtsodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    TILsodienthoai.setError("Bạn bắt buộc phải nhập trường này");
                } else {
                    TILsodienthoai.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() ==0) {
                    TILpassword.setError("Bạn bắt buộc phải nhập trường này");
                } else {
                    TILpassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void Account_Controller(int id_per,String _sodienthoai, String password, Context context) {
        new PostToserver(id_per,_sodienthoai,password,context).execute(Server.duongdangetDangNhap);
    }

    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        String _sodienthoai, password;

        Context context;
        int id_per;


        public PostToserver(int id_per,String _sodienthoai, String password, Context context) {
            this._sodienthoai = _sodienthoai;
            this.password = password;
            this.context = context;
            this.id_per=id_per;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("id_per",String.valueOf(id_per))
                    .addFormDataPart("sodienthoai", _sodienthoai)
                    .addFormDataPart("password", password)
                    .setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetDangNhap).post(requestBody).build();
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
            Log.d("AAA", _sodienthoai + " " + password+" "+id_per);
            Log.d("AAA", s);
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            int iduser,id_per;
            String Sodienthoai;
            String password;
            String Ten;
            String ngaysinh;
            String Diachi;
            String Email;
            String image;
            try {
                JSONArray json = new JSONArray(s);
                JSONObject jsonObject = json.getJSONObject(0);
                if (jsonObject.length() > 2) {
                    id_per=jsonObject.getInt("id_per");
                    iduser = jsonObject.getInt("iduser");
                    Sodienthoai = jsonObject.getString("sodienthoai");
                    password = jsonObject.getString("password");
                    Ten = jsonObject.getString("Ten");
                    ngaysinh = jsonObject.getString("Ngaysinh");
                    Diachi = jsonObject.getString("Diachi");
                    Email = jsonObject.getString("Email");
                    image = jsonObject.getString("image");
                    //Toast.makeText(context,id_per+"",Toast.LENGTH_LONG).show();
                    setSharedPreferences(true,id_per,iduser,Sodienthoai,password,Ten,ngaysinh,Diachi,Email,image);
                    //if(id_per==1)
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    Activity activity=(Activity) context;
                    activity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }
    public User getUser(){

        return user;
    }
    private void setSharedPreferences(boolean status,int id_per,int iduser, String sodienthoai, String password, String ten, String ngaysinh, String diachi,String email, String image){
        sharedPreferences=context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(status==false){
            editor.clear();
        }else {
            editor.clear();
            editor.putInt("id_per",id_per);
            editor.putInt("iduser",iduser);
            editor.putString("sodienthoai",sodienthoai);
            editor.putString("password",password);
            editor.putString("ten",ten);
            editor.putString("ngaysinh",ngaysinh);
            editor.putString("diachi",diachi);
            editor.putString("email",email);
            editor.putString("image",image);
        }
        editor.commit();
    }


}

