package com.example.appbanhangnew.controller.Admin.Add;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddChiTietSPActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddSanPhamActivity;
import com.example.appbanhangnew.adapter.User.LoaispAdapter;
import com.example.appbanhangnew.adapter.User.SPNewAdapter;
import com.example.appbanhangnew.adapter.User.SPadapter;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
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

public class AddSanPhamController {
    Context context;
    public  void getLoaiSP(ArrayList<Loaisp> loaisps, Spinner spinnerLoaisp, int _idloaisp){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanLoaisp, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idloaisp;
                String  hinhanhloaisp;
                ArrayList<String> tenloaisp= new ArrayList<>();
                loaisps.clear();
                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idloaisp=jsonObject.getInt("id");
                            tenloaisp.add(jsonObject.getString("tenloaisp"));
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
                            loaisps.add(new Loaisp(idloaisp,tenloaisp.get(i),hinhanhloaisp));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("LoaiSPSpinner",e.toString()+"");
                        }
                    }
                    ArrayAdapter spinnerAdapter= new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,tenloaisp);
                    spinnerLoaisp.setAdapter(spinnerAdapter);
                    spinnerLoaisp.setSelection(_idloaisp-1);
                    Log.d("LoaiSPSpinner",loaisps.size()+"");

                }else {
                    Log.d("LoaiSPSpinner",null+"");
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

    public AddSanPhamController(Context context){
        this.context = context;
    }
    public void Themsp( SanPham sanPham){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetCountSP, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("idsp"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        new AddSanPhamController.PostToserver( sanPham.getIdsp(),sanPham.getTensp(), sanPham.getHinhanhsp(), sanPham.getMota(), sanPham.getIdloaisp()).execute(Server.duongdanaddsp);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j)== true) {
                                sanPham.setIdsp(j);
                                new AddSanPhamController.PostToserver(sanPham.getIdsp(),sanPham.getTensp(), sanPham.getHinhanhsp(), sanPham.getMota(), sanPham.getIdloaisp()).execute(Server.duongdanaddsp);
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
        int idsp, idloaisp;
        String tensp,image,mota;

        public PostToserver(int idsp, String tensp, String image,String mota, int idloaisp) {
            this.idsp = idsp;
            this.tensp = tensp;
            this.image = image;
            this.mota = mota;
            this.idloaisp = idloaisp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idsp",String.valueOf(idsp))
                    .addFormDataPart("tensp",tensp)
                    .addFormDataPart("image",image)
                    .addFormDataPart("mota",mota)
                    .addFormDataPart("idloaisp",String.valueOf(idloaisp)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanaddsp).post(requestBody).build();
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

            try {
                JSONObject jsonObject= new JSONObject(s);
                success=jsonObject.getInt("success");
                message=jsonObject.getString("message");
                if(success==1){
                    Intent intent= new Intent(context, AddChiTietSPActivity.class);
                    intent.putExtra("idloaisp",idloaisp);
                    intent.putExtra("idsp",idsp);
                    context.startActivity(intent);
                    Activity activity=(Activity) context;
                    activity.finish();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

            super.onPostExecute(s);
        }
    }

}