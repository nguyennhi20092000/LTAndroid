package com.example.appbanhangnew.action.User.GioHang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.DonHangActivity;
import com.example.appbanhangnew.action.User.SanPham.ChitietSanPhamActivity;
import com.example.appbanhangnew.adapter.User.GioHangAdapter;
import com.example.appbanhangnew.controller.User.GioHangController;
import com.example.appbanhangnew.model.DonHang.GioHang;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbarGioHang;
    ListView listViewGiohang;
    public static TextView txtTongTien;
    Button btnMuaHang;
    public  static ArrayList<GioHang> gioHangArrayList;
    static GioHangAdapter gioHangAdapter;
    static GioHangController gioHangController;
    public static TextView textviewThongBao;
    public static ArrayList<Integer> idchecked;
    static int iduser;
    SharedPreferences sharedPreferences;
    public static  int TongTien=0;
    ArrayList<GioHang> giohangcheckArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionBar();
        getGioHang();
        ClickItem();
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                extra.putSerializable("giohang", gioHangArrayList);
                Intent intent= new Intent(getBaseContext(), DonHangActivity.class);
                intent.putExtra("status",0);
                intent.putExtra("extra",extra);
                startActivity(intent);
                finish();
            }
        });

    }
    public static void getGioHang(){
        gioHangArrayList.clear();
        //gioHangAdapter=new GioHangAdapter(,gioHangArrayList);
        gioHangController.getGioHang(iduser,gioHangArrayList,gioHangAdapter);
    }
    private void ClickItem() {

        listViewGiohang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ArrayList<Object> objects = new ArrayList<Object>();




                Intent intent= new Intent(GioHangActivity.this,EditGioHangActivity.class);
                intent.putExtra("idgiohang",gioHangArrayList.get(position).getIdgiohang());
                intent.putExtra("tenSP",gioHangArrayList.get(position).getTensp());
                intent.putExtra("tenChiTiet",  gioHangArrayList.get(position).getTenchitietsp());
                intent.putExtra("SoLuong",gioHangArrayList.get(position).getSoluong());
                intent.putExtra("Gia",gioHangArrayList.get(position).getGia());
                intent.putExtra("HinhAnh",gioHangArrayList.get(position).getHinhanhsp());

                //intent.putParcelableArrayListExtra("giohang", (ArrayList<? extends Parcelable>) gioHangArrayList);
                startActivity(intent);
            }
        });

    }



    public  void AnhXa(){
        toolbarGioHang=(Toolbar) findViewById(R.id.toolbar_giohang);
        listViewGiohang=(ListView) findViewById(R.id.listviewgiohang);
        txtTongTien=(TextView) findViewById(R.id.txttongtien);
        btnMuaHang=(Button) findViewById(R.id.btnmuahang);
        textviewThongBao=(TextView) findViewById(R.id.textviewthongbao);

        gioHangArrayList=new ArrayList<>();
        gioHangAdapter=new GioHangAdapter(GioHangActivity.this,gioHangArrayList);
        listViewGiohang.setAdapter(gioHangAdapter);
        gioHangController=new GioHangController(GioHangActivity.this);
        idchecked=new ArrayList<>();
        sharedPreferences=getSharedPreferences("USER_FILE", MODE_PRIVATE);

        iduser = sharedPreferences.getInt("iduser", 0);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        TongTien=0;
        GioHangActivity.txtTongTien.setText("Tổng: " + decimalFormat.format(TongTien) + " Đ");
        giohangcheckArraylist=new ArrayList<>();

    }
    public void ActionBar(){
        setSupportActionBar(toolbarGioHang);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);



    }
}