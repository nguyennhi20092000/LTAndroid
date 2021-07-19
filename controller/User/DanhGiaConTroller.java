package com.example.appbanhangnew.controller.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddSanPhamActivity;
import com.example.appbanhangnew.action.User.DonHang.DanhGiaActivity;
import com.example.appbanhangnew.action.User.DonHang.DonMuaActivity;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.adapter.User.LoaispAdapter;
import com.example.appbanhangnew.controller.Admin.Add.AddLoaiSPController;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;
import com.google.gson.JsonObject;

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

public class DanhGiaConTroller {
    Context context;

    public DanhGiaConTroller(Context context) {
        this.context = context;
    }
    public void DanhGia(ChiTietDonHang chiTietDonHang, int iddonhang, int idsp){
        Log.d("DanhGia1111111",iddonhang+" "+chiTietDonHang.getIdsp()+"");

        new CountDanhGia(iddonhang,chiTietDonHang,idsp).execute(Server.duongdanCoutDanhGiaofSP);
    }
    public void ThemRatting(int idsp, int iduser,float ratting, int iddonhang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetCountDanhGia, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("iddanhgia"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        new AddDanhGia( idsp,1, iduser,iddonhang, ratting).execute(Server.duongdanaddDanhGia);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j)== true) {
                                new AddDanhGia(idsp, j, iduser,iddonhang,ratting).execute(Server.duongdanaddDanhGia);
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


    class AddDanhGia extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
       int idsp, idddanhgia,iduser, iddonhang;
       float danhgia;

        public AddDanhGia(int idsp, int idddanhgia, int iduser, int iddonhang, float danhgia) {
            this.idsp = idsp;
            this.idddanhgia = idddanhgia;
            this.iduser = iduser;
            this.iddonhang = iddonhang;
            this.danhgia = danhgia;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iddanhgia",String.valueOf(idddanhgia))
                    .addFormDataPart("idsp",String.valueOf(idsp))
                    .addFormDataPart("iduser",String.valueOf(iduser))
                    .addFormDataPart("danhgia",String.valueOf(danhgia))
                    .addFormDataPart("iddonhang",String.valueOf(iddonhang)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanaddDanhGia).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response=okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
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

            try {
                JSONObject jsonObject= new JSONObject(s);
                success=jsonObject.getInt("success");
                message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if(success==1){
                Toast.makeText(context,"Đánh giá hoàn tất",Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(context, DonMuaActivity.class);
                //context.startActivity(intent);

            }
            else {

            }
            super.onPostExecute(s);
        }
    }

    class CountDanhGia extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int  iddonhang, idsp;

        ChiTietDonHang chiTietDonHang;

        public CountDanhGia(int iddonhang,  ChiTietDonHang chiTietDonHang, int idsp) {
            this.iddonhang = iddonhang;

            this.chiTietDonHang=chiTietDonHang;
            this.idsp=idsp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idsp",String.valueOf(idsp))
                    .addFormDataPart("iddonhang",String.valueOf(iddonhang)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanCoutDanhGiaofSP).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response=okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
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
            Log.d("DanhGia1111111",s);

            try {
                JSONArray jsonArray= new JSONArray(s);
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                success=jsonObject.getInt("countdanhgia");


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if(success>0){
               Toast.makeText(context,"Bạn đã đánh giá rồi!!",Toast.LENGTH_LONG).show();

            }
            else {
                Intent intent= new Intent(context, DanhGiaActivity.class);
                intent.putExtra("idsp", chiTietDonHang.getIdsp());
                intent.putExtra("gia",chiTietDonHang.getGia());
                intent.putExtra("hinhanhsp",chiTietDonHang.getHinhanhsp());
                intent.putExtra("iddonhang",chiTietDonHang.getIddonhang());
                intent.putExtra("tensp","["+chiTietDonHang.getTenchitietsp()+"] "+ chiTietDonHang.getTensp());
                context.startActivity(intent);
                //Activity activity= (Activity) context;
                //activity.finish();

            }
            super.onPostExecute(s);
        }
    }
}
