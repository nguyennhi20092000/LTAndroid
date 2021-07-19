 package com.example.appbanhangnew.controller.User_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.action.User_Admin.Account.UserActivity;

import com.example.appbanhangnew.controller.Admin.Add.AddChiTietSPController;
import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DangKyController {
    public DangKyController(){

    }
    public void SetTIL(TextInputEditText txtsodienthoai, TextInputEditText txtpassword, TextInputEditText txtnhaplaipassword,
                       TextInputLayout TILsodienthoai, TextInputLayout TILpassword, TextInputLayout TILnhaplaipassword){
        txtsodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() ==0) {
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
        txtnhaplaipassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() ==0) {
                    TILnhaplaipassword.setError("Bạn bắt buộc phải nhập trường này");
                } else {
                    TILnhaplaipassword.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String SetDangKy( int id_per,String sdt,String pass,String nhaplaipw,Context context,
                          TextInputLayout TILsodienthoai,TextInputLayout TILpassword, TextInputLayout TILnhaplaipassword, TextInputEditText txtsodienthoai,
                            TextInputEditText txtpassword){
        String _sodienthoai="";
        if(sdt.equals("")||pass.equals("")||nhaplaipw.equals("")){
            if(sdt.equals("")){
                TILsodienthoai.setError("Không được bỏ trống trường này");
            }
            if(pass.equals("")){
                TILpassword.setError("Không được bỏ trống trường này");
            }
            if(nhaplaipw.equals("")){
                TILnhaplaipassword.setError("Không được bỏ trống trường này");
            }

        }else {
            _sodienthoai=txtsodienthoai.getText().toString();
            if(pass.equals(nhaplaipw)){
                /*Intent intent= new Intent(context, CheckPhoneActivity.class);
                intent.putExtra("sdt",sdt);
                intent.putExtra("password",pass);
                intent.putExtra("id",ID);
                intent.putExtra("count_re",count_re);
                intent.putExtra("id_per",id_per);
                context.startActivity(intent);*/
                AddUser(id_per,sdt,pass,context);

            }else{
                TILnhaplaipassword.setError("Password nhập lại chưa trùng khớp");
            }
        }
        return _sodienthoai;

    }

    public void AddUser( int id_per, String sdt, String password,Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetcountuserSP, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("iduser"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        Log.d("ChiTietsP","yyyyyy");
                        new PostToserver(sdt,password,1,id_per,1,context).execute(Server.duongdaninsertuser);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j)== true) {
                                Log.d("ChiTietsP","gghghjhjhj");
                                //chitietSP.setIdChiTietSP(j);
                                new PostToserver(sdt,password,j,id_per,j,context).execute(Server.duongdaninsertuser);
                                break;
                            }
                        }
                    }
                }

            }
        } , new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context,error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);

    }
    public boolean CheckId(ArrayList<Integer> checkid, int check){
        for(int i=0; i< checkid.size(); i++) {
            if(check== checkid.get(i)) {
                return false;
            }
        }
        return true;
    }

    class PostToserver extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        String sdt,password;

        int count_re,id_per,id;

        Context context;

        public PostToserver(String sdt, String password, int count_re, int id_per, int id, Context context) {
            this.sdt = sdt;
            this.password = password;
            this.count_re = count_re;
            this.id_per = id_per;
            this.id = id;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("id_re",String.valueOf(count_re))
                    .addFormDataPart("id_per",String.valueOf(id_per))
                    .addFormDataPart("iduser",String.valueOf(id))
                    .addFormDataPart("sodienthoai",sdt)
                    .addFormDataPart("password",password).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdaninsertuser).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response=okHttpClient.newCall(request).execute();
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
            //Log.d("AAA",s);
            int success=0;
            String message="";
            Log.d("DangKi123",s);
            try {
                JSONObject jsonObject= new JSONObject(s);
                success=jsonObject.getInt("success");
                message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if(success==1){
                Intent intent= new Intent(context, UserActivity.class);
                intent.putExtra("ID_USER",id);
                context.startActivity(intent);
                Activity activity= (Activity) context;
                activity.finish();
                //Toast.makeText(context.getApplicationContext(), message,Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(context.getApplicationContext(), message,Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

}
