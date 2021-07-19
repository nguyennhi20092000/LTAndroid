package com.example.appbanhangnew.action.User_Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.SanPham.ChitietSanPhamActivity;
import com.example.appbanhangnew.adapter.User.DanhmucAdapter;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DanhmucSPMainActivity extends AppCompatActivity {

    Toolbar toolbarDanhMuc;
    ListView listViewDanhMuc;
    DanhmucAdapter danhmucAdapter;
    ArrayList<SanPham> arrayListSanPham;

    ArrayList<ChitietSPs> arrayListsChitietSP;
    int idThoiTrang=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    boolean limitdata=false;

    ArrayList<ChitietSP> chitietSPs= new ArrayList<>();
    ChitietSPs _chitietSPs;
    mHandler handler;

    //Load_Danhmuc_SP load_danhmuc_sp;

    String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuc_sp_main);
        Anhxa();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIDLoaiSP();
            ActionToolBar();
            GetData(page);
            //load_danhmuc_sp=new Load_Danhmuc_SP(listViewDanhMuc,danhmucAdapter,arrayListSanPham,arrayListsChitietSP,idThoiTrang,page,footerview,limitdata
            //,chitietSPs,_chitietSPs,getApplicationContext());
            LoadmoreData();
        }else {
            Toast.makeText(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public int getPriceOnArrChitietGia(ChitietSPs _chitietSP){
        int min=_chitietSP.getChitiet(0).getGia();
        //int position=0;
        for(int i=0;i<_chitietSP.CountChitietSPs();i++){
            if(min>_chitietSP.getChitiet(i).getGia()){
                min=_chitietSP.getChitiet(i).getGia();
                //position=i;
            }
        }
        return min;
    }

    private void LoadmoreData() {
        listViewDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(), ChitietSanPhamActivity.class);
                intent.putExtra("thongtinsp",arrayListSanPham.get(position));
                intent.putExtra("ThongtinChiTietSP",arrayListsChitietSP.get(position));
                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                intent.putExtra("Gianhonhat","Giá: "+ decimalFormat.format(getPriceOnArrChitietGia(arrayListsChitietSP.get(position)))+" Đ");
                startActivity(intent);
                finish();
            }
        });
        listViewDanhMuc.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount!=0 && isLoading==false &&limitdata==false){
                    //Dùng luồng chạy song song vs tiến trình chính. vd luồng chính là đọc, luồng phụ là load dl
                    isLoading=true;
                    ThreadData threadData= new ThreadData();
                    threadData.start();




                }
            }
        });
    }
    class PostToserver extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        int _idSP;

        public PostToserver(int _idSP) {
            this._idSP = _idSP;
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

            ChitietSP chitietSP= new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitiet,gia);

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
                        chitietSPs.add(new ChitietSP(idsp,idchitietsp,tenchitietsp,hinhanhchitiet,gia));
                        //chitietSPs.add(chitietSP);
                        Log.d("Check1234 "+i+"",chitietSPs.get(i).getIdChiTietSP()+"");
                    }

                }
                //_chitietSPs.Clear();
                _chitietSPs=new ChitietSPs(chitietSPs);
                Log.d("Check123",_chitietSPs.CountChitietSPs()+"abc");
                for(int i=0;i<_chitietSPs.CountChitietSPs();i++){
                    Log.d("Check12356",_chitietSPs.getChitiet(i).getIdChiTietSP()+"");
                    //arrayListsChitietSP.add();
                }
                arrayListsChitietSP.add(_chitietSPs);
                for(int i=0;i<arrayListsChitietSP.size();i++){
                    for(int j=0;j<arrayListsChitietSP.get(i).CountChitietSPs();j++){

                        Log.d("Check1235",arrayListsChitietSP.get(i).getChitiet(j).getIdChiTietSP()+"");
                    }

                }
                chitietSPs.clear();


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

    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.duongdangetSP+String.valueOf(Page);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idloaisp=0;
                String tenHoa="";
                //int giaHoa=0;
                String hinhanhHoa="";
                String mota="";
                int idspHoa=0;
                //Toast.makeText(getApplicationContext(),"Length "+response.length()+"",Toast.LENGTH_SHORT).show();
                if(response!=null && response.length()!=2){
                    listViewDanhMuc.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            idloaisp=jsonObject.getInt("idloaisp");
                            tenHoa=jsonObject.getString("tensp");
                            hinhanhHoa=jsonObject.getString("image");
                            mota=jsonObject.getString("mota");
                            idspHoa=jsonObject.getInt("idsp");
                            arrayListSanPham.add(new SanPham(idspHoa,tenHoa,hinhanhHoa,mota,idloaisp));
                            Log.d("Check12","idsp: "+arrayListSanPham.get(i).getIdsp());
                            new PostToserver(arrayListSanPham.get(i).getIdsp()).execute(Server.duongdangetChitietSP);
                            if(arrayListsChitietSP!=null){
                                danhmucAdapter.notifyDataSetChanged();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"abc"+e.toString(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    //CheckConnection.Show_Tost_Short(getApplicationContext(),"abcsdddd"+arrayListSanPham.size()+" ");
                    limitdata=true;
                    listViewDanhMuc.removeFooterView(footerview);
                    CheckConnection.Show_Tost_Short(getApplicationContext(),"Đã hết dữ liệu");
                }


            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"abd"+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param= new HashMap<String, String>();
                param.put("idloaisp",String.valueOf(idThoiTrang));
                //Toast.makeText(getApplicationContext(),idThoiTrang+"",Toast.LENGTH_SHORT).show();
                return param;
            }
        };
        requestQueue.add(stringRequest);


    }

    private void ActionToolBar() {
        //toolbarDanhMuc.setTitle("Hoa");
        setSupportActionBar(toolbarDanhMuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDanhMuc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getIDLoaiSP() {
        idThoiTrang=getIntent().getIntExtra("idloaisp",-1);

        page=1;
        //Toast.makeText(getApplicationContext(),idThoiTrang+"",Toast.LENGTH_SHORT).show();
        //arrayListSanPham.clear();
        //arrayListsChitietSP.clear();
        //danhmucAdapter.notifyDataSetChanged();

        //Log.d("Giatriloaisp",idThoiTrang+"");
    }

    private void Anhxa() {
        toolbarDanhMuc=findViewById(R.id.ToolbarDanhMuc);
        listViewDanhMuc=findViewById(R.id.ListViewDanhMuc);
        arrayListSanPham= new ArrayList<>();
        //ArrayList<ChitietSP> chitietSPs= new ArrayList<>();
        arrayListsChitietSP=new ArrayList<>();
        danhmucAdapter =new DanhmucAdapter(getApplicationContext(),arrayListSanPham,arrayListsChitietSP);
        listViewDanhMuc.setAdapter(danhmucAdapter);
        LayoutInflater layoutInflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=layoutInflater.inflate(R.layout.progressbar,null);
        handler=new mHandler();
        Title=getIntent().getStringExtra("Title");
        //Toast.makeText(getApplicationContext(),Title,Toast.LENGTH_LONG).show();
        toolbarDanhMuc.setTitle(Title);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewDanhMuc.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            //obtainMessage là phép liên kết với các Thread và Handler
            Message message=handler.obtainMessage(1);
            handler.sendMessage(message);
            super.run();
        }
    }


}
