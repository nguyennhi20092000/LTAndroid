package com.example.appbanhangnew.controller.User;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;
import com.example.appbanhangnew.model.DonHang.GioHang;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DonHangController {
    Context context;
    int i=1;
    ArrayList<ChiTietDonHang> chiTietDonHangs;
    ArrayList<GioHang> gioHangs;

    public DonHangController(Context context, ArrayList<ChiTietDonHang> chiTietDonHangs, ArrayList<GioHang> gioHangs) {
        this.context = context;
        this.chiTietDonHangs = chiTietDonHangs;
        this.gioHangs = gioHangs;
    }

    public void AddChiTietDonHang(ChiTietDonHang chitietdonhang, ArrayList<GioHang> gioHangs){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetCountChiTietDonHang, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();
                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("idchitietdonhang"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //oast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    Log.d("AAACheckChiTiet",count.size()+"");
                    if (count.size() < 1) {
                        chitietdonhang.setIdchitietdonhang(1);
                        new PostChiTietDonHang(chitietdonhang,gioHangs).execute(Server.duongdanAddChiTietDonHang);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j) == true) {
                                chitietdonhang.setIdchitietdonhang(j);
                                new PostChiTietDonHang(chitietdonhang,gioHangs).execute(Server.duongdanAddChiTietDonHang);
                                break;
                            }
                        }
                    }
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context, error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);
    }

    public void AddDonHang(DonHang donHang, ArrayList<ChiTietDonHang> chiTietDonHangs,ArrayList<GioHang> gioHangs) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetCountDonHang, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();
                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("iddonhang"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        donHang.setIddonhang(1);
                        chiTietDonHangs.get(0).setIddonhang(1);
                        //Toast.makeText(context,donHang.getIduser()+"",Toast.LENGTH_LONG).show();
                        new PostToserver(donHang,chiTietDonHangs,gioHangs).execute(Server.duongdanAddDonHang);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j) == true) {
                                donHang.setIddonhang(j);
                                chiTietDonHangs.get(0).setIddonhang(j);
                                new PostToserver(donHang,chiTietDonHangs,gioHangs).execute(Server.duongdanAddDonHang);
                                break;
                            }
                        }
                    }
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context, error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);

    }

    public boolean CheckId(ArrayList<Integer> checkid, int check) {
        //boolean ischeck=true;
        for (int i = 0; i < checkid.size(); i++) {
            if (check == checkid.get(i)) {
                return false;
            }

        }
        return true;


    }

    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        //Truyền dữ liệu user và password

        DonHang donHang;
        ArrayList<ChiTietDonHang> chiTietDonHangs;
        ArrayList<GioHang> gioHangs;

        public PostToserver(DonHang donHang, ArrayList<ChiTietDonHang> chiTietDonHangs, ArrayList<GioHang> gioHangs) {
            this.donHang = donHang;
            this.chiTietDonHangs = chiTietDonHangs;
            this.gioHangs = gioHangs;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser", String.valueOf(donHang.getIduser()))
                    .addFormDataPart("iddonhang", String.valueOf(donHang.getIddonhang()))
                    .addFormDataPart("ngaythang",donHang.getNgaythang())
                    .addFormDataPart("sodienthoai", donHang.getSodienthoai())
                    .addFormDataPart("diachi",donHang.getDiachi())
                    .addFormDataPart("email", donHang.getEmail())
                    .addFormDataPart("tongtien",String.valueOf(donHang.getTongtien()))
                    .addFormDataPart("trangthai",String.valueOf(donHang.getTrangthai())).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanAddDonHang).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
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
            Log.d("AAACheck",donHang.getIduser()+"");
            int success = 0;
            String message="";


            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if (success == 1) {
                Toast.makeText(context, "Đơn hàng đã được tạo", Toast.LENGTH_LONG).show();
                //for(int i=0;i<gioHangs.size();i++){
                AddChiTietDonHang(chiTietDonHangs.get(0),gioHangs);
                //new PostDeleteGioHang(chiTietDonHangs.get(0),gioHangs.get(0).getIdgiohang()).execute(Server.duongdanDeleteGioHang);
                i=1;
                //}
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    class PostChiTietDonHang extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        ChiTietDonHang chiTietDonHang;
        ArrayList<GioHang> gioHangs;

        public PostChiTietDonHang(ChiTietDonHang chiTietDonHang, ArrayList<GioHang> gioHangs) {
            this.chiTietDonHang = chiTietDonHang;
            this.gioHangs = gioHangs;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iddonhang", String.valueOf(chiTietDonHang.getIddonhang()))
                    .addFormDataPart("idchitietdonhang", String.valueOf(chiTietDonHang.getIdchitietdonhang()))
                    .addFormDataPart("idsp",String.valueOf(chiTietDonHang.getIdsp()))
                    .addFormDataPart("idchitietsp", String.valueOf(chiTietDonHang.getIdchitietsp()))
                    .addFormDataPart("tensp",chiTietDonHang.getTensp())
                    .addFormDataPart("tenchitietsp",chiTietDonHang.getTenchitietsp())
                    .addFormDataPart("giatien",String.valueOf(chiTietDonHang.getGia()))
                    .addFormDataPart("soluong",String.valueOf(chiTietDonHang.getSoluong()))
                    .addFormDataPart("thanhtien",String.valueOf(chiTietDonHang.getGia()*chiTietDonHang.getSoluong())).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanAddChiTietDonHang).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
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
            int success = 0;
            String message="";
            Log.d("AAACheck",chiTietDonHang.getTensp()+"");
            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                //message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"E:"+ e.toString(), Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if (success == 1) {
                Toast.makeText(context, "Đơn hàng đã được tạo", Toast.LENGTH_LONG).show();
                if(i<chiTietDonHangs.size()){
                    //new PostDeleteGioHang()
                    chiTietDonHangs.get(i).setIddonhang(chiTietDonHang.getIddonhang());
                    new PostDeleteGioHang(chiTietDonHangs.get(i),gioHangs.get(i-1).getIdgiohang()).execute(Server.duongdanDeleteGioHang);
                    //AddChiTietDonHang(chiTietDonHangs.get(i),gioHangs);

                }
                else {
                    if(i==chiTietDonHangs.size()){
                        new PostDeleteGioHang(chiTietDonHangs.get(i-1),gioHangs.get(i-1).getIdgiohang()).execute(Server.duongdanDeleteGioHang);
                    }
                    
                }
            } else {
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            }
            super.onPostExecute(s);
        }
    }



    class PostDeleteGioHang extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        ChiTietDonHang chiTietDonHang;
        int idgioHangs;

        public PostDeleteGioHang(ChiTietDonHang chiTietDonHang, int idgioHangs) {
            this.chiTietDonHang = chiTietDonHang;
            this.idgioHangs = idgioHangs;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idgiohang", String.valueOf(idgioHangs)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanDeleteGioHang).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
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
            int success = 0;
            String message="";
            Log.d("AAACheck",chiTietDonHang.getTensp()+"");
            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                //message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"E:"+ e.toString(), Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if (success == 1) {
                //Toast.makeText(context, "Đơn hàng đã được tạo", Toast.LENGTH_LONG).show();
                Log.d("DeleteGioHang",idgioHangs+"");
                if(i<chiTietDonHangs.size()){
                    chiTietDonHangs.get(i).setIddonhang(chiTietDonHang.getIddonhang());
                    AddChiTietDonHang(chiTietDonHangs.get(i),gioHangs);
                    i++;
                }
                else {

                }
            } else {
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            }
            super.onPostExecute(s);
        }
    }



}
