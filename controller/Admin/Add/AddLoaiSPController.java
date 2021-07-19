package com.example.appbanhangnew.controller.Admin.Add;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddLoaiSPActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddSanPhamActivity;
import com.example.appbanhangnew.adapter.User.LoaispAdapter;
import com.example.appbanhangnew.model.SanPham.Loaisp;
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

public class AddLoaiSPController {
    Context context;
    public AddLoaiSPController(Context context){
        this.context = context;
    }
    public void Themloaisp( String tenloaisp, String hinhanhloaisp){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetCountloaisp, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("idloaisp"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        new AddLoaiSPController.PostToserver( 1,tenloaisp, hinhanhloaisp).execute(Server.duongdanaddloaisp);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j)== true) {
                                new AddLoaiSPController.PostToserver(j, tenloaisp, hinhanhloaisp).execute(Server.duongdanaddloaisp);
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
    public void GetDuLieuDanhMuc(ArrayList< Loaisp > arrayListLoaisp, LoaispAdapter loaispAdapter, Context context) {



    }

    class PostToserver extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int idloaisp;
        String tenloaisp,hinhanhloaisp;

        public PostToserver(int idloaisp, String tenloaisp, String hinhanhloaisp) {
            this.idloaisp = idloaisp;
            this.tenloaisp = tenloaisp;
            this.hinhanhloaisp = hinhanhloaisp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idloaisp",String.valueOf(idloaisp))
                    .addFormDataPart("tenloaisp",tenloaisp)
                    .addFormDataPart("hinhanhloaisp",hinhanhloaisp).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanaddloaisp).post(requestBody).build();
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

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if(success==1){
                Toast.makeText(context,"Sản phẩm đã được thêm vào danh sách",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, AddSanPhamActivity.class);
                intent.putExtra("idloaisp",idloaisp);
                intent.putExtra("tenloaisp",tenloaisp);
                context.startActivity(intent);
                Activity activity=(Activity) context;
                activity.finish();
            }
            else {

            }
            super.onPostExecute(s);
        }
    }

}