package com.example.appbanhangnew.controller.Admin.TheoDoiSP;

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
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.adapter.Admin.SPBanChayAdapter;
import com.example.appbanhangnew.model.SPBanChay;
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

public class SPBanChayController {
    Context context;

    public SPBanChayController(Context context) {
        this.context = context;
    }
    public  void getLoaiSP(ArrayList<SPBanChay> spBanChays, SPBanChayAdapter spBanChayAdapter){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetSPbanChay, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idsp,soluongban;
                String  hinhanhloaisp,tensp;

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            tensp=jsonObject.getString("tensp");
                            hinhanhloaisp=jsonObject.getString("image");
                            soluongban=jsonObject.getInt("soluongban");
                            idsp=jsonObject.getInt("idsp");



                            spBanChays.add(new SPBanChay(idsp,soluongban,tensp,hinhanhloaisp));
                            spBanChayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SPSpinner",e.toString()+"");
                        }
                    }


                }else {
                    Log.d("SPSpinner",null+"");
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
    public void getSP(ArrayList<SPBanChay> sanPhams, int idsp, SPBanChayAdapter spBanChayAdapter){
        new getSP(sanPhams,idsp,spBanChayAdapter).execute(Server.duongdangetChiTietSPbanChay);
    }



    class getSP extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        ArrayList<SPBanChay> sanPhams;
        int idsp;
        SPBanChayAdapter spBanChayAdapter;

        public getSP(ArrayList<SPBanChay> sanPhams, int idsp, SPBanChayAdapter spBanChayAdapter) {
            this.sanPhams = sanPhams;
            this.idsp = idsp;
            this.spBanChayAdapter = spBanChayAdapter;
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
                    .url(Server.duongdangetChiTietSPbanChay).post(requestBody).build();
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

            //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
            int idsp, soluongban;
            String  hinhanhloaisp, tensp;


            try {
                JSONArray json = new JSONArray(s);
                sanPhams.clear();

                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        tensp=jsonObject.getString("tenchitietsp");
                        hinhanhloaisp=jsonObject.getString("hinhanhsp");
                        soluongban=jsonObject.getInt("soluongban");
                        idsp=jsonObject.getInt("idchitietsp");
                        sanPhams.add(new SPBanChay(idsp,soluongban,tensp,hinhanhloaisp));
                        spBanChayAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("LoaiSPSpinner",e.toString()+"");
                    }
                }

                //spinnersp.setSelection(sanPhams.size()-1);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }

}
