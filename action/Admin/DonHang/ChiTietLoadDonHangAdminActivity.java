package com.example.appbanhangnew.action.Admin.DonHang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhangnew.R;


import com.example.appbanhangnew.action.Admin.DonHang.Fragment.DaHuyFragment;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.DaNhanFragment;
import com.example.appbanhangnew.adapter.Admin.ChiTietLoadDonHangAdminAdapter;
import com.example.appbanhangnew.controller.Admin.ChiTietDonHangAdminController;

import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietLoadDonHangAdminActivity extends AppCompatActivity {
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    int position;
    ListView listViewChiTiet;
    ChiTietLoadDonHangAdminAdapter dongChiTietChoXacNhanAdapter;
    TextView txtngaythang, txtTongTien;
    Button btnhuy;
    ChiTietDonHangAdminController chiTietDonHangController;
    int status;
    DonHang donHang;
    androidx.appcompat.widget.Toolbar toolbarLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_load_don_hang_admin);
        AnhXa();
        ActionBar();
        getData();
    }
    public void AnhXa(){
        chiTietDonHangArrayList=new ArrayList<>();
        listViewChiTiet= (ListView) findViewById(R.id.listchitietdonhang);

        txtngaythang=(TextView) findViewById(R.id.txtngaythang);
        txtTongTien=(TextView) findViewById(R.id.txtthanhtienchoxacnhan);
        btnhuy=(Button) findViewById(R.id.btnHuy);
        toolbarLoad=(Toolbar) findViewById(R.id.toolbarChiTietDonHang);

        chiTietDonHangController=new ChiTietDonHangAdminController(ChiTietLoadDonHangAdminActivity.this);
    }

    public void getData(){
        position= getIntent().getIntExtra("chitietdonhang",0);
        status= getIntent().getIntExtra("trangthai",0);
        //Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
        Log.d("ChiTietDonHangLog",status+"");
        Log.d("ChiTietDonHangLog",position+"");

        switch (status){

            case 4:
                chiTietDonHangArrayList= DaHuyFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaHuyFragment.donHangs.get(position);
                break;
            case 5:
                chiTietDonHangArrayList= DaNhanFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaNhanFragment.donHangs.get(position);
                break;
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtngaythang.setText(donHang.getNgaythang());
        int thanhtien=0;

        txtTongTien.setText("Thành tiền: "+decimalFormat.format(donHang.getTongtien())+"Đ");
        dongChiTietChoXacNhanAdapter=new ChiTietLoadDonHangAdminAdapter(this,chiTietDonHangArrayList);
        listViewChiTiet.setAdapter(dongChiTietChoXacNhanAdapter);


    }
    public void ActionBar(){
        setSupportActionBar(toolbarLoad);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarLoad.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);



    }

}