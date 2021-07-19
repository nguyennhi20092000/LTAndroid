package com.example.appbanhangnew.controller.User_Admin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.User.LoaispAdapter;
import com.example.appbanhangnew.adapter.User.SPNewAdapter;
import com.example.appbanhangnew.adapter.User.TongSPAdapter;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainController {
    public void MaiController(){

    }

    public void ActionViewFlipper(ViewFlipper viewFlipper, Context context){
        ArrayList<String> mangquangcao= new ArrayList<>();
        mangquangcao.add("https://galle.vn/images/slideshow/2021/03/11/compress/banner-web_1615426173.jpg");
        mangquangcao.add("https://golmart.com.vn/wp-content/uploads/2019/12/3CE-Cloud-Lip-Tint.jpg");
        mangquangcao.add("https://youngmediapro.com/wp-content/uploads/2017/08/fxxXTi7.jpg");
        mangquangcao.add("https://dobonusymi.com/wp-content/uploads/2020/06/slider.jpg");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView= new ImageView(context);

            //thực hiện load ảnh quảng cáo từ link vào inmageView.
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            //Fuit hình cho vừa vs inmage
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        // tạo hiệu ứng cb động
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in= AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(context,R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);



    }


    public void GetDuLieuDanhMuc(ArrayList<Loaisp> arrayListLoaisp, LoaispAdapter loaispAdapter, Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id=0;
                String tenloaisp="";
                String imageLoaisp="";
                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if(requestQueue!=null){
                    //Loc từng Json object con.
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisp");
                            imageLoaisp=jsonObject.getString("hinhanhloaisp");
                            arrayListLoaisp.add(new Loaisp(id,tenloaisp,imageLoaisp));
                            //Update lại bản vẽ..
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context,error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);

    }

    class PostToserver extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int _idSP;
        RecyclerView.Adapter adapter;
        ArrayList<ChitietSPs>chitietSPs;
        ArrayList<ChitietSP> chitietSPsnew;
        ChitietSPs _chitietSPsNew;
        ArrayList<ChitietSPs> arrayListsChitietSPNew;

        public PostToserver(int _idSP, RecyclerView.Adapter adapter, ArrayList<ChitietSPs> chitietSPs, ArrayList<ChitietSP> chitietSPsnew, ChitietSPs _chitietSPsNew) {
            this._idSP = _idSP;
            this.adapter = adapter;
            this.chitietSPs = chitietSPs;
            this.chitietSPsnew = chitietSPsnew;
            this._chitietSPsNew = _chitietSPsNew;
            this.arrayListsChitietSPNew = arrayListsChitietSPNew;
            Log.d("AAA",_idSP+"");
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("idsp",String.valueOf(_idSP))
                    .setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            okhttp3.Request request= new okhttp3.Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetChitietSP).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                okhttp3.Response response=okHttpClient.newCall(request).execute();
                //Toast.makeText(getApplicationContext(),"1234555",Toast.LENGTH_SHORT).show();
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
            Log.d("AAA",s);
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            int idsp=0;
            int idchitietsp=0;
            String tenchitietsp="";
            String hinhanhchitiet="";
            int gia=0;

            //ChitietSP chitietSP= new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitiet,gia);

            //chitietSPs.add(chitietSP);
            //_chitietSPs=new ChitietSPs(chitietSPs);
            try {
                JSONArray json = new JSONArray(s);
                for(int i=0;i<json.length();i++){
                    JSONObject jsonObject=json.getJSONObject(i);
                    if(jsonObject.length()>0){
                        idsp=jsonObject.getInt("idsp");
                        idchitietsp=jsonObject.getInt("idchitietsp");
                        tenchitietsp=jsonObject.getString("tenchitietsp");
                        hinhanhchitiet=jsonObject.getString("hinhanhsp");
                        gia=jsonObject.getInt("gia");
                        chitietSPsnew.add(new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitiet,gia));
                        //chitietSPs.add(chitietSP);
                        Log.d("Check1234 "+i+"",chitietSPsnew.get(i).getIdChiTietSP()+"");
                    }

                }
                //_chitietSPs.Clear();
                _chitietSPsNew=new ChitietSPs(chitietSPsnew);
                Log.d("Check123",_chitietSPsNew.CountChitietSPs()+"abc");
                for(int i=0;i<_chitietSPsNew.CountChitietSPs();i++){
                    Log.d("Check12356",_chitietSPsNew.getChitiet(i).getIdChiTietSP()+"");
                    //arrayListsChitietSP.add();
                }
                chitietSPs.add(_chitietSPsNew);
                adapter.notifyDataSetChanged();
                chitietSPsnew.clear();


                //Toast.makeText(getApplicationContext(),arrayListsChitietSP.get(1).getChitiet(1).getGia()+"",Toast.LENGTH_SHORT).show();



                //Intent intent= new Intent(Activity_Account.this,MainActivity.class);
                //startActivity(intent);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                //Toast.makeText(getApplicationContext(),jsonException.toString(),Toast.LENGTH_SHORT).show();
                Log.d("Check123",jsonException.toString());
            }

            super.onPostExecute(s);
        }
    }
    /*
    Tổng sản phẩm
     */

    public void GetDataTongSP(ArrayList<SanPham> arrayListTongSanPham, TongSPAdapter tongSPAdapter, ArrayList<ChitietSP> chitietSPNew, ArrayList<ChitietSPs> arrayListsChitietTongSP, ChitietSPs _chitietsSPNew, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetTongSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idloaisp=0;
                String tenHoa="";
                //int giaHoa=0;
                String hinhanhHoa="";
                String mota="";
                int idspHoa=0;
                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if(requestQueue!=null){
                    //Loc từng Json object con.
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            idloaisp=jsonObject.getInt("idloaisp");
                            tenHoa=jsonObject.getString("tensp");
                            hinhanhHoa=jsonObject.getString("image");
                            mota=jsonObject.getString("mota");
                            idspHoa=jsonObject.getInt("idsp");
                            arrayListTongSanPham.add(new SanPham(idspHoa,tenHoa,hinhanhHoa,mota,idloaisp));
                            //Log.d("Check12","idsp: "+arrayListSanPhamNew.get(i).getIdsp());
                            new PostToserver(arrayListTongSanPham.get(i).getIdsp(),tongSPAdapter,arrayListsChitietTongSP, chitietSPNew,_chitietsSPNew).execute(Server.duongdangetChitietSP);
                            //if(arrayListsChitietSPNew!=null && arrayListsChitietSPNew!=null){
                            //spNewAdapter.notifyDataSetChanged();
                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context,error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);

    }
      /*
        Load sp mới.
     */

    public void GetDataSPNew(Context context, ArrayList<SanPham> arrayListSanPhamNew, SPNewAdapter spNewAdapter, ArrayList<ChitietSP> chitietSPNew, ArrayList<ChitietSPs> arrayListsChitietSPNew, ChitietSPs _chitietsSPNew) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetSPNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int idloaisp=0;
                String tenHoa="";
                //int giaHoa=0;
                String hinhanhHoa="";
                String mota="";
                int idspHoa=0;

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if(requestQueue!=null){
                    //Loc từng Json object con.

                    for(int i=0;i<response.length();i++){
                        try {

                            JSONObject jsonObject=response.getJSONObject(i);
                            idloaisp=jsonObject.getInt("idloaisp");
                            tenHoa=jsonObject.getString("tensp");
                            hinhanhHoa=jsonObject.getString("image");
                            mota=jsonObject.getString("mota");
                            idspHoa=jsonObject.getInt("idsp");
                            arrayListSanPhamNew.add(new SanPham(idspHoa,tenHoa,hinhanhHoa,mota,idloaisp));
                            //Log.d("Check12","idsp: "+arrayListSanPhamNew.get(i).getIdsp());
                            new PostToserver(arrayListSanPhamNew.get(i).getIdsp(),spNewAdapter,arrayListsChitietSPNew, chitietSPNew,_chitietsSPNew).execute(Server.duongdangetChitietSP);
                            //if(arrayListsChitietSPNew!=null && arrayListsChitietSPNew!=null){
                            //spNewAdapter.notifyDataSetChanged();
                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context,error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);

    }

}
