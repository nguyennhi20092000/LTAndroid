package com.example.appbanhangnew.controller.Admin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.appbanhangnew.adapter.Admin.LoadChoXacNhanAdminAdapter;
import com.example.appbanhangnew.adapter.User.LoadDonHangAdapter;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;
import com.example.appbanhangnew.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoadDonhangAdminController {
    Context context;

    public LoadDonhangAdminController(Context context) {
        this.context = context;
    }
    public void getDonhang(ArrayList<DonHang> donHangs, LoadChoXacNhanAdminAdapter choXacNhanAdapter, int trangthai){
        new getDonHangs(trangthai,donHangs,choXacNhanAdapter).execute(Server.duongdangetDonhangAdmin);
    }



    class getDonHangs extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();


        int trangthai;
        ArrayList<DonHang> donHangs;
        LoadChoXacNhanAdminAdapter choXacNhanAdapter;

        public getDonHangs( int trangthai, ArrayList<DonHang> donHangs, LoadChoXacNhanAdminAdapter choXacNhanAdapter) {

            this.trangthai = trangthai;
            this.donHangs = donHangs;
            this.choXacNhanAdapter = choXacNhanAdapter;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("trangthai",String.valueOf(trangthai))
                    .setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetDonhangAdmin).post(requestBody).build();
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

            int iduser,iddonhang,tongtien,trangthai;
            String ngaythang,sdt,diachi,email;
            ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
            try {
                JSONArray json = new JSONArray(s);
                //GioHangActivity.textviewThongBao.setText("");
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        iduser=jsonObject.getInt("iduser");
                        iddonhang=jsonObject.getInt("iddonhang");
                        tongtien=jsonObject.getInt("tongtien");
                        trangthai=jsonObject.getInt("trangthai");
                        ngaythang=jsonObject.getString("ngaythang");
                        sdt=jsonObject.getString("sodienthoai");
                        diachi=jsonObject.getString("diachi");
                        email=jsonObject.getString("email");
                        chiTietDonHangArrayList=new ArrayList<>();

                        donHangs.add(new DonHang(iduser,iddonhang,ngaythang,sdt,diachi,email,tongtien,trangthai,chiTietDonHangArrayList));
                        new getChiTietDonHangs(iddonhang,chiTietDonHangArrayList,donHangs.get(i),choXacNhanAdapter).execute(Server.duongdangetChiTietDonhang);


                        //choXacNhanAdapter.notifyDataSetChanged();
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

    class getChiTietDonHangs extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password



        int iddonhang;
        ArrayList<ChiTietDonHang> chiTietDonHangs;
        DonHang donHangs;
        LoadChoXacNhanAdminAdapter choXacNhanAdapter;

        public getChiTietDonHangs(int iddonhang, ArrayList<ChiTietDonHang> chiTietDonHangs, DonHang donHangs, LoadChoXacNhanAdminAdapter choXacNhanAdapter) {
            this.iddonhang = iddonhang;
            this.chiTietDonHangs = chiTietDonHangs;
            this.donHangs = donHangs;
            this.choXacNhanAdapter = choXacNhanAdapter;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iddonhang",String.valueOf(iddonhang))
                    .setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetChiTietDonhang).post(requestBody).build();
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

            int  idchitietdonhang,_iddonhang,idsp,idchitietsp,soluong,gia;
            String tensp,tenchitietsp,hinhanhsp, ngaythang;
            ArrayList<ChiTietDonHang> chiTietDonHangArrayList= new ArrayList<>();
            try {
                JSONArray json = new JSONArray(s);
                //Toast.makeText(context, iddonhang+"", Toast.LENGTH_SHORT).show();
                //GioHangActivity.textviewThongBao.setText("");
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        idchitietdonhang=jsonObject.getInt("idchitietdonhang");
                        _iddonhang=jsonObject.getInt("iddonhang");
                        idsp=jsonObject.getInt("idsp");
                        idchitietsp=jsonObject.getInt("idchitietsp");
                        soluong=jsonObject.getInt("soluong");
                        gia=jsonObject.getInt("gia");
                        tensp=jsonObject.getString("tensp");
                        tenchitietsp=jsonObject.getString("tenchitietsp");
                        hinhanhsp=jsonObject.getString("hinhanhsp");

                        chiTietDonHangArrayList.add(new ChiTietDonHang(idchitietdonhang,_iddonhang,idsp,idchitietsp,soluong,gia,tensp,tenchitietsp,hinhanhsp,""));
                        //
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("ListDonHangController",e.toString());
                    }

                }

                donHangs.setArrayListchitietdonhang(chiTietDonHangArrayList);
                choXacNhanAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }

}

