package com.example.appbanhangnew.controller.User;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.action.User.GioHang.GioHangActivity;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.action.User.SanPham.ChitietSanPhamActivity;
import com.example.appbanhangnew.action.User_Admin.DanhmucSPMainActivity;
import com.example.appbanhangnew.adapter.User.GioHangAdapter;
import com.example.appbanhangnew.adapter.User.LoaispAdapter;
import com.example.appbanhangnew.model.DonHang.GioHang;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GioHangController {
    Context context;

    public GioHangController(Context context) {
        this.context = context;
    }



    public void getGioHang(int iduser,ArrayList<GioHang> arrayGioHang, GioHangAdapter gioHangAdapter) {
        new PostToserver(context,iduser,arrayGioHang,gioHangAdapter).execute(Server.duongdangetGiohang);

    }
    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password


        Context context;
        int id_user;
        ArrayList<GioHang> arrayGioHang;
        GioHangAdapter gioHangAdapter;

        public PostToserver(Context context, int id_user, ArrayList<GioHang> arrayGioHang, GioHangAdapter gioHangAdapter) {
            this.context = context;
            this.id_user = id_user;
            this.arrayGioHang = arrayGioHang;
            this.gioHangAdapter = gioHangAdapter;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser",String.valueOf(id_user))
                    .setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetGiohang).post(requestBody).build();
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

            int iduser, idgiohang, idsp, idchitietsp, soluong, gia;
            String tensp, tenchitietsp, hinhanhsp, ngaythang;
            try {
                JSONArray json = new JSONArray(s);
                GioHangActivity.textviewThongBao.setText("");
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        iduser = jsonObject.getInt("iduser");
                        idgiohang = jsonObject.getInt("idgiohang");
                        idsp = jsonObject.getInt("idsp");
                        idchitietsp = jsonObject.getInt("idchitietsp");
                        tensp = jsonObject.getString("tensp");
                        tenchitietsp = jsonObject.getString("tenchitietsp");
                        hinhanhsp = jsonObject.getString("hinhanhsp");
                        soluong = jsonObject.getInt("soluong");
                        ngaythang = jsonObject.getString("ngaythang");
                        gia = jsonObject.getInt("gia");

                        arrayGioHang.add(new GioHang(iduser, idgiohang, idsp, idchitietsp, soluong, tensp, tenchitietsp, hinhanhsp, ngaythang, gia));
                        //Update lại bản vẽ..
                        gioHangAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }

}
