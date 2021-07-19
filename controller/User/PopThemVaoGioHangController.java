package com.example.appbanhangnew.controller.User;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.model.DonHang.GioHang;
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

public class PopThemVaoGioHangController {
    Context context;

    public PopThemVaoGioHangController(Context context) {
        this.context = context;
    }

    public void ControllerbtnAddCard(int id_user, int idSP, int idChiTietSP, int soluong, String ngaythang){
        new GetToCard(context,id_user,idSP,idChiTietSP,soluong,ngaythang).execute(Server.duongdangetGiohang);
    }

    public int CheckidSPinCard(ArrayList<GioHang> gioHangs, int idsp) {
        for (int i = 0; i < gioHangs.size(); i++) {
            if (gioHangs.get(i).getIdsp() == idsp) {
                return gioHangs.get(i).getIdsp();
            }
        }
        return -1;
    }

    public int CheckidCTSPinCard(ArrayList<GioHang> gioHangs, int idchitietsp) {
        for (int i = 0; i < gioHangs.size(); i++) {
            if (gioHangs.get(i).getIdchitietsp() == idchitietsp) {
                return i;
            }
        }
        return -1;
    }

    public void AddGioHang(int iduser, int idsanpham, int idchitietsp, int soluong, String ngaythang) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetCountGiohang, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("idgiohang"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        new PostToserver(iduser, 1, idsanpham, idchitietsp, soluong, ngaythang).execute(Server.duongdanAddGiohang);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j) == true) {
                                new PostToserver(iduser, j, idsanpham, idchitietsp, soluong, ngaythang).execute(Server.duongdanAddGiohang);
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
        int iduser, idgiohang, idsanpham, idchitietsp, soluong;
        String ngaythang;

        public PostToserver(int iduser, int idgiohang, int idsanpham, int idchitietsp, int soluong, String ngaythang) {
            this.iduser = iduser;
            this.idgiohang = idgiohang;
            this.idsanpham = idsanpham;
            this.idchitietsp = idchitietsp;
            this.soluong = soluong;
            this.ngaythang = ngaythang;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser", String.valueOf(iduser))
                    .addFormDataPart("idgiohang", String.valueOf(idgiohang))
                    .addFormDataPart("idsanpham", String.valueOf(idsanpham))
                    .addFormDataPart("idchitietsp", String.valueOf(idchitietsp))
                    .addFormDataPart("soluong", String.valueOf(soluong))
                    .addFormDataPart("ngaythang", ngaythang).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanAddGiohang).post(requestBody).build();
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
            //String message="";

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                //message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            if (success == 1) {
                Toast.makeText(context, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
            } else {

            }
            super.onPostExecute(s);
        }
    }

    class GetToCard extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password


        Context context;
        int id_user;

        int idSP;
        int idChiTietSP;
        int Soluong;
        String Ngaythang;

        public GetToCard(Context context, int id_user,  int idSP, int idChiTietSP, int soluong, String ngaythang) {
            this.context = context;
            this.id_user = id_user;

            this.idSP = idSP;
            this.idChiTietSP = idChiTietSP;
            Soluong = soluong;
            Ngaythang = ngaythang;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser", String.valueOf(id_user))
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
            ArrayList<GioHang> arrayGioHang=new ArrayList<>();
            try {
                JSONArray json = new JSONArray(s);
                //GioHangActivity.textviewThongBao.setText("");
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int position=CheckidCTSPinCard(arrayGioHang,idChiTietSP);
                if (CheckidSPinCard(arrayGioHang, idSP)!=-1) {

                    if(position!=-1){
                        new EdittoServer(arrayGioHang.get(position).getIdgiohang(),arrayGioHang.get(position).getSoluong()+Soluong,Ngaythang).execute(Server.duongdanEditGiohang);
                    }
                    else{
                        AddGioHang(id_user, idSP, idChiTietSP, Soluong, Ngaythang);
                    }
                } else {

                    AddGioHang(id_user, idSP, idChiTietSP, Soluong, Ngaythang);

                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }

    class EdittoServer extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int idgiohang, soluong;
        String ngaythang;

        public EdittoServer( int idgiohang, int soluong, String ngaythang) {

            this.idgiohang = idgiohang;
            this.soluong = soluong;
            this.ngaythang = ngaythang;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idgiohang", String.valueOf(idgiohang))
                    .addFormDataPart("soluong", String.valueOf(soluong))
                    .addFormDataPart("ngaythang", ngaythang).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanEditGiohang).post(requestBody).build();
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

            int success = 0;
            //String message="";

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                //message=jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            if (success == 1) {
                Toast.makeText(context, "Sản phẩm đã sửa trong giỏ hàng", Toast.LENGTH_LONG).show();
            } else {

            }
            super.onPostExecute(s);
        }
    }
}
