package com.example.appbanhangnew.controller.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.action.Admin.XemSanPham.SanPhamActivity;
import com.example.appbanhangnew.adapter.Admin.LoaispAdapter;

import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DanhSachSPAdminController {
    Context context;

    public DanhSachSPAdminController(Context context) {
        this.context = context;
    }
    public void CatchOnItemListView(ListView listViewDanhMucSP, ArrayList<Loaisp> loaispArrayList) {
        listViewDanhMucSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context,position+"",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(context, SanPhamActivity.class);
                intent.putExtra("idloaisp",loaispArrayList.get(position).get_idloaisp());
                intent.putExtra("Title",loaispArrayList.get(position).get_tenloaisp());
                context.startActivity(intent);
            }
        });
    }
    public void GetDuLieuLoaiSP(ArrayList<Loaisp> arrayListLoaisp, LoaispAdapter loaispAdapter, ListView listViewDanhSachLoaiSP) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //JsonArrayRequest: Là HTTP request có kết quả trả về là JSONArray.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if(requestQueue!=null){
                    int id;
                    String tenloaisp="";
                    String imageLoaisp="";
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
        //CatchOnItemListView(listViewDanhSachLoaiSP,arrayListLoaisp);
    }
}
