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
import java.util.ConcurrentModificationException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditLoaiSPController {
    Context context;

    public EditLoaiSPController(Context context) {
        this.context = context;
    }



    public  void getLoaiSP(ArrayList<Loaisp> loaisps, Spinner spinnerloaisp, int _idloaisp){
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
                    spinnerloaisp.setAdapter(spinnerAdapter);
                    spinnerloaisp.setSelection(_idloaisp-1);
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
    public void setLoaisp(Loaisp loaisp, TextInputEditText txttenloaitsp, TextInputEditText linkhinhanh, int _idloaisp ){
        new SetLoaiSP(loaisp,txttenloaitsp,linkhinhanh,_idloaisp).execute(Server.duongdangetInForLoaiSP);
    }

    class SetLoaiSP extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Loaisp loaisp;
        TextInputEditText txttenloaitsp, linkhinhanh;
        int _idloaisp;

        public SetLoaiSP(Loaisp loaisp, TextInputEditText txttenloaitsp, TextInputEditText linkhinhanh, int _idloaisp) {
            this.loaisp = loaisp;
            this.txttenloaitsp = txttenloaitsp;
            this.linkhinhanh = linkhinhanh;
            this._idloaisp = _idloaisp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()

                    .addFormDataPart("idloaisp", String.valueOf(_idloaisp)).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetInForLoaiSP).post(requestBody).build();
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
            int idloaisp;
            String  hinhanhloaisp,tenloaisp;


            try {
                JSONArray json = new JSONArray(s);
                try {
                    JSONObject jsonObject = json.getJSONObject(0);
                    idloaisp=jsonObject.getInt("id");
                    tenloaisp=jsonObject.getString("tenloaisp");
                    hinhanhloaisp=jsonObject.getString("hinhanhloaisp");

                    txttenloaitsp.setText(tenloaisp);
                    linkhinhanh.setText(hinhanhloaisp);
                    loaisp=new Loaisp(idloaisp,tenloaisp,hinhanhloaisp);


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
    public void EditLoaiSP(Loaisp loaisp){
        new EditLoaiSP(loaisp).execute(Server.duongdaneditLoaiSP);
    }

    class EditLoaiSP extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Loaisp loaisp;

        public EditLoaiSP(Loaisp loaisp) {
            this.loaisp = loaisp;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;

                    .addFormDataPart("idloaisp",String.valueOf(loaisp.get_idloaisp()))
                    .addFormDataPart("tenloaisp",loaisp.get_tenloaisp())
                    .addFormDataPart("hinhanhloaisp",loaisp.get_hinhanhloaisp()).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request= new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdaneditLoaiSP).post(requestBody).build();
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





}
