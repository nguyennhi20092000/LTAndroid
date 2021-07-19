package com.example.appbanhangnew.controller.User;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangnew.adapter.User.SPSearchAdapter;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
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
import okhttp3.RequestBody;

public class SearchController {
    public SearchController() {
    }

    class PostToserver extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int _idSP;
        RecyclerView.Adapter adapter;
        ArrayList<ChitietSPs> chitietSPs;
        ArrayList<ChitietSP> chitietSPsSearch;
        ChitietSPs _chitietSPsSearch;


        public PostToserver(int _idSP, RecyclerView.Adapter adapter, ArrayList<ChitietSPs> chitietSPs, ArrayList<ChitietSP> chitietSPsSearch, ChitietSPs _chitietSPsSearch) {
            this._idSP = _idSP;
            this.adapter = adapter;
            this.chitietSPs = chitietSPs;
            this.chitietSPsSearch = chitietSPsSearch;
            this._chitietSPsSearch = _chitietSPsSearch;
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
                        chitietSPsSearch.add(new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitiet,gia));
                        //chitietSPs.add(chitietSP);
                        Log.d("Check1234 "+i+"",chitietSPsSearch.get(i).getIdChiTietSP()+"");
                    }

                }
                //_chitietSPs.Clear();
                _chitietSPsSearch=new ChitietSPs(chitietSPsSearch);
                Log.d("Check123s",_chitietSPsSearch.CountChitietSPs()+"abc");
                for(int i=0;i<_chitietSPsSearch.CountChitietSPs();i++){
                    Log.d("Check12356",_chitietSPsSearch.getChitiet(i).getIdChiTietSP()+"");
                    //arrayListsChitietSP.add();
                }
                chitietSPs.add(_chitietSPsSearch);
                adapter.notifyDataSetChanged();
                chitietSPsSearch.clear();


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

    class PostToserverOfSearch extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        String searchtext;
        SPSearchAdapter spSearchAdapter;
        ArrayList<SanPham> arrayListSPSearch;
        ArrayList<ChitietSP> chitietSPSearch;
        ArrayList<ChitietSPs> arrayListsChiTietSPSearch;
        ChitietSPs _chitietsSPSearch;

        public PostToserverOfSearch(String searchtext, SPSearchAdapter spSearchAdapter, ArrayList<ChitietSP> chitietSPSearch, ArrayList<SanPham> arrayListSPSearch, ArrayList<ChitietSPs> arrayListsChiTietSPSearch, ChitietSPs _chitietsSPSearch) {
            this.searchtext = searchtext;
            this.spSearchAdapter = spSearchAdapter;
            this.arrayListSPSearch = arrayListSPSearch;
            this.arrayListSPSearch = arrayListSPSearch;
            this.arrayListsChiTietSPSearch = arrayListsChiTietSPSearch;
            this._chitietsSPSearch=_chitietsSPSearch;
            this.chitietSPSearch=chitietSPSearch;
            //Log.d("AAA",_idSP+"");
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("search",searchtext)
                    .setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            okhttp3.Request request= new okhttp3.Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetSPSearch).post(requestBody).build();
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
            int idloaisp=0;
            String tenHoa="";
            //int giaHoa=0;
            String hinhanhHoa="";
            String mota="";
            int idspHoa=0;
            Log.d("Search",s);
            //ChitietSP chitietSP= new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitiet,gia);

            //chitietSPs.add(chitietSP);
            //_chitietSPs=new ChitietSPs(chitietSPs);
            try {
                JSONArray json = new JSONArray(s);
                for(int i=0;i<json.length();i++){
                    JSONObject jsonObject=json.getJSONObject(i);
                    if(jsonObject.length()>0){

                        idloaisp=jsonObject.getInt("idloaisp");
                        tenHoa=jsonObject.getString("tensp");
                        hinhanhHoa=jsonObject.getString("image");
                        mota=jsonObject.getString("mota");
                        idspHoa=jsonObject.getInt("idsp");
                        arrayListSPSearch.add(new SanPham(idspHoa,tenHoa,hinhanhHoa,mota,idloaisp));
                        //Log.d("Check12","idsp: "+arrayListSanPhamNew.get(i).getIdsp());
                        new PostToserver(arrayListSPSearch.get(i).getIdsp(),spSearchAdapter,arrayListsChiTietSPSearch, chitietSPSearch,_chitietsSPSearch).execute(Server.duongdangetChitietSP);
                        //if(arrayListsChitietSPNew!=null && arrayListsChitietSPNew!=null){
                    }

                }


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



    public void GetDataSPSearch(String SearchText, ArrayList<SanPham> arrayListSPSearch, SPSearchAdapter spSearchAdapter, ArrayList<ChitietSP> chitietSPSearch, ArrayList<ChitietSPs> arrayListsChiTietSPSearch, ChitietSPs _chitietsSPSearch, Context context) {
        Log.d("Search",SearchText);

        new PostToserverOfSearch(SearchText,spSearchAdapter,chitietSPSearch,arrayListSPSearch,arrayListsChiTietSPSearch,_chitietsSPSearch).execute(Server.duongdangetSPSearch);
        Log.d("Search",SearchText);
    }
}
