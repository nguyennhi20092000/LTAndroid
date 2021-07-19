package com.example.appbanhangnew.controller.User_Admin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.DanhGiaActivity;
import com.example.appbanhangnew.action.User.SanPham.PopChonSPActivity;
import com.example.appbanhangnew.action.User.SanPham.PopVanChuyenActivity;

import com.example.appbanhangnew.controller.User.DanhGiaConTroller;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
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
import okhttp3.Response;

public class ChiTietSPController {
    int SWIPE_THRESHOLD = 10;
    int SWIPE_VECLOCITY_THRESHOLD = 10;
    RecyclerView recyclerViewChonChiTietSP, recyclerViewSpTuongTu;
    ViewFlipper viewFlipper_ChitietSP;
    Context context;

    public ChiTietSPController(int SWIPE_THRESHOLD, int SWIPE_VECLOCITY_THRESHOLD, RecyclerView recyclerViewChonChiTietSP, RecyclerView recyclerViewSpTuongTu, ViewFlipper viewFlipper_ChitietSP, Context context) {
        this.SWIPE_THRESHOLD = SWIPE_THRESHOLD;
        this.SWIPE_VECLOCITY_THRESHOLD = SWIPE_VECLOCITY_THRESHOLD;
        this.recyclerViewChonChiTietSP = recyclerViewChonChiTietSP;
        this.recyclerViewSpTuongTu = recyclerViewSpTuongTu;
        this.viewFlipper_ChitietSP = viewFlipper_ChitietSP;
        this.context = context;
    }
    //class dùng lắng nghe cử chỉ


    public void ActionBar(Toolbar toolbarVanChuyen, Toolbar toolbarChonChiTiet, SanPham getSP, ChitietSPs getChitietSP, String gia){
        toolbarVanChuyen.inflateMenu(R.menu.menu_toolbar);
        toolbarVanChuyen.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Log.d("123","123");
                switch (item.getItemId()){
                    case R.id.menu_start:

                        Intent intent= new Intent(context, PopVanChuyenActivity.class);
                        intent.putExtra("thongtinsp",getSP);
                        intent.putExtra("ThongtinChiTietSP",getChitietSP);
                        intent.putExtra("Gianhonhat",gia);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                }
                return false;
            }
        });

        toolbarChonChiTiet.inflateMenu(R.menu.menu_toolbar);
        toolbarChonChiTiet.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Log.d("123","123");
                switch (item.getItemId()){
                    case R.id.menu_start:
                        Intent intent= new Intent(context, PopChonSPActivity.class);
                        intent.putExtra("ChitietSPs",getChitietSP);
                        intent.putExtra("SanPham",getSP);
                        context.startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }

    public void DanhGia(int idsp, TextView txtdanhgia){
        //Log.d("DanhGia1111111",iddonhang+" "+chiTietDonHang.getIdsp()+"");

        new CountDanhGia(idsp, txtdanhgia).execute(Server.duongdangetDanhGiaofSP);
    }
    class CountDanhGia extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int   idsp;
        TextView textView;

        public CountDanhGia(int idsp, TextView textView) {
            this.idsp = idsp;
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idsp",String.valueOf(idsp)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetDanhGiaofSP).post(requestBody).build();
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
            ArrayList<Integer> success=new ArrayList<>();
            String message="";
            Log.d("DanhGia1111111",s);

            try {
                JSONArray jsonArray= new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    success.add(jsonObject.getInt("danhgia"));
                }



            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if(success.size()>0){
                float tong=0;
                for (int i=0;i<success.size();i++){
                    tong=tong+success.get(i);

                }
                float trungbinh=tong/success.size();
                textView.setText("Đánh giá: "+trungbinh+"/5");


            }
            else {
                textView.setText("Chưa có nhận xét");

            }
            super.onPostExecute(s);
        }
    }

}
