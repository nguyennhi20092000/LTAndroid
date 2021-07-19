package com.example.appbanhangnew.controller.Admin.Edit;

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
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit.EditActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.ThaoTacQuanLyActivity;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;

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

public class EditChiTietSPController {
    Context context;

    public EditChiTietSPController(Context context) {
        this.context = context;
    }

    public  void getSP(ArrayList<SanPham> sanPhams, Spinner spinnersp, int _idsp, int idloaisp){
        new getSP(sanPhams,spinnersp,_idsp,idloaisp).execute(Server.duongdangetSPAdd);
    }
    public void SetChiTietSP(ChitietSP chitietSP, TextInputEditText txttensp, TextInputEditText linkhinhanh, TextInputEditText txtgia, int _idsp){
        new setChiTiet(chitietSP,txttensp,linkhinhanh,txtgia,_idsp).execute(Server.duongdangetinformationSP);
    }

    public  void getLoaiSP(ArrayList<Loaisp> loaisps, Spinner spinnersp, int _idloaisp){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanLoaisp, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idloaisp;
                String  hinhanhloaisp;
                ArrayList<String> tenloaisp= new ArrayList<>();
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
                            Log.d("SPSpinner",e.toString()+"");
                        }
                    }
                    ArrayAdapter spinnerAdapter= new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,tenloaisp);
                    spinnersp.setAdapter(spinnerAdapter);
                    spinnersp.setSelection(_idloaisp-1);
                    Log.d("SPSpinner",loaisps.size()+"");

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


    class setChiTiet extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        ChitietSP chitietSP;
        TextInputEditText txttenchitietsp, linkhinhanh, txtgia;
        int _idchitiet;

        public setChiTiet(ChitietSP chitietSP, TextInputEditText txttenchitietsp, TextInputEditText linkhinhanh, TextInputEditText txtgia, int _idchitiet) {
            this.chitietSP = chitietSP;
            this.txttenchitietsp = txttenchitietsp;
            this.linkhinhanh = linkhinhanh;
            this.txtgia = txtgia;
            this._idchitiet = _idchitiet;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()

                    .addFormDataPart("idchitietsp", String.valueOf(_idchitiet)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetInForChiTietSP).post(requestBody).build();
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

            //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
            int idchitietsp, idsp,gia;
            String  hinhanhchitietsp,tenchitietsp;


            try {
                JSONArray json = new JSONArray(s);
                try {
                    JSONObject jsonObject = json.getJSONObject(0);
                    idsp=jsonObject.getInt("idsp");
                    idchitietsp=jsonObject.getInt("idchitietsp");
                    tenchitietsp=jsonObject.getString("tenchitietsp");
                    hinhanhchitietsp=jsonObject.getString("hinhanhsp");
                    gia=jsonObject.getInt("gia");
                    chitietSP=new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitietsp,gia);
                    linkhinhanh.setText(hinhanhchitietsp);
                    txtgia.setText(gia+"");
                    txttenchitietsp.setText(tenchitietsp);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("LoaiSPSpinner", e.toString() + "");
                }




            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }


    


    class getSP extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        ArrayList<SanPham> sanPhams;
        Spinner spinnersp;
        int _idsp,idloaisp;

        public getSP(ArrayList<SanPham> sanPhams, Spinner spinnersp, int _idsp, int idloaisp) {
            this.sanPhams = sanPhams;
            this.spinnersp = spinnersp;
            this._idsp = _idsp;
            this.idloaisp = idloaisp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;

                    .addFormDataPart("idloaisp",String.valueOf(idloaisp)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetSPAdd).post(requestBody).build();
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
            int idloaisp, idsp;
            String  hinhanhloaisp, mota;
            ArrayList<String> tensp = new ArrayList<>();

            try {
                JSONArray json = new JSONArray(s);
                sanPhams.clear();

                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        idloaisp=jsonObject.getInt("idloaisp");
                        idsp=jsonObject.getInt("idsp");
                        tensp.add(jsonObject.getString("tensp"));
                        hinhanhloaisp=jsonObject.getString("image");
                        mota=jsonObject.getString("mota");
                        sanPhams.add(new SanPham(idsp,tensp.get(i),hinhanhloaisp,mota,idloaisp));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("LoaiSPSpinner",e.toString()+"");
                    }
                }
                ArrayAdapter spinnerAdapter= new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,tensp);
                spinnersp.setAdapter(spinnerAdapter);
                //spinnersp.setSelection(sanPhams.size()-1);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }
    public void EditChiTietSP(ChitietSP chitietSP){
        new EditChiTietSP(chitietSP).execute(Server.duongdaneditChiTietSP);
    }

    class EditChiTietSP extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        ChitietSP chitietSP;

        public EditChiTietSP(ChitietSP chitietSP) {
            this.chitietSP = chitietSP;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;

                    .addFormDataPart("idchitietsp",String.valueOf(chitietSP.getIdChiTietSP()))
                    .addFormDataPart("idsp",String.valueOf(chitietSP.getIdsp()))
                    .addFormDataPart("tenchitietsp",chitietSP.getTenChiTietsp())
                    .addFormDataPart("hinhanhsp",chitietSP.getHinhanhChiTietsp())
                    .addFormDataPart("gia",String.valueOf(chitietSP.getGia())).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdaneditChiTietSP).post(requestBody).build();
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
            int success=0;
            String message="";

            try {
                JSONObject jsonObject= new JSONObject(s);
                success=jsonObject.getInt("success");
                message=jsonObject.getString("message");
                //Toast.makeText(context,.toString(),Toast.LENGTH_SHORT).show();
                if(success==1){
                    Intent intent= new Intent(context, EditActivity.class);
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
    public void getChiTiet(ArrayList<ChitietSP> chitietSPs, Spinner spinnerChiTietsP, int idsp, int idchitietsp){
        new getChiTietSP(chitietSPs,spinnerChiTietsP,idsp,idchitietsp).execute(Server.duongdangetChitietSP);
    }
    class getChiTietSP extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        ArrayList<ChitietSP> chiTietsanPhams;
        Spinner spinnerChiTietsp;
        int _idsp,idChiTietsp;

        public getChiTietSP(ArrayList<ChitietSP> sanPhams, Spinner spinnerChiTietsp, int _idsp, int idChiTietsp) {
            this.chiTietsanPhams = sanPhams;
            this.spinnerChiTietsp = spinnerChiTietsp;
            this._idsp = _idsp;
            this.idChiTietsp = idChiTietsp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;

                    .addFormDataPart("idsp",String.valueOf(_idsp)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetChitietSP).post(requestBody).build();
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
            int idchitietsp, idsp,gia;
            String  hinhanhchitietsp;

            ArrayList<String> tenchitietsp = new ArrayList<>();

            try {
                JSONArray json = new JSONArray(s);
                chiTietsanPhams.clear();

                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        idsp=jsonObject.getInt("idsp");
                        idchitietsp=jsonObject.getInt("idchitietsp");
                        tenchitietsp.add(jsonObject.getString("tenchitietsp"));
                        hinhanhchitietsp=jsonObject.getString("hinhanhsp");
                        gia=jsonObject.getInt("gia");
                        chiTietsanPhams.add(new ChitietSP(idsp,idchitietsp,tenchitietsp.get(i),hinhanhchitietsp,gia));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("LoaiSPSpinner",e.toString()+"");
                    }
                }
                ArrayAdapter spinnerAdapter= new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,tenchitietsp);
                spinnerChiTietsp.setAdapter(spinnerAdapter);
                //spinnersp.setSelection(sanPhams.size()-1);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }
}
