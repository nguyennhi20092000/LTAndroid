package com.example.appbanhangnew.action.Admin.DonHang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.DaGiaoFragment;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.XacNhanFragment;
import com.example.appbanhangnew.adapter.User.DongChiTietChoXacNhanAdapter;
import com.example.appbanhangnew.controller.Admin.ChiTietDonHangAdminController;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietDonHangAdMinActivity extends AppCompatActivity {
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    int position;
    ListView listViewChiTiet;
    DongChiTietChoXacNhanAdapter dongChiTietAdapter;
    TextView txtngaythang, txtTongTien;
    Button btnhuy, btnhuydonhang;
    ChiTietDonHangAdminController chiTietDonHangController;
    Toolbar toolbar;
    int status;
    DonHang donHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang_ad_min);
        AnhXa();
        ActionBar();
        getData();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==0){
                    chiTietDonHangController.EditInformation(donHang.getIddonhang(),2);


                }else {
                    chiTietDonHangController.EditInformation(donHang.getIddonhang(),3);

                }
                /*Intent intent= new Intent(ChiTietDonHangAdMinActivity.this, XacNhanDonHangActivity.class);
                intent.putExtra("status",1);
                startActivity(intent);*/
                finish();
            }
        });

    }
    public void AnhXa(){
        chiTietDonHangArrayList=new ArrayList<>();
        listViewChiTiet= (ListView) findViewById(R.id.listchitietdonhang);

        txtngaythang=(TextView) findViewById(R.id.txtngaythang);
        txtTongTien=(TextView) findViewById(R.id.txtthanhtienchoxacnhan);
        btnhuy=(Button) findViewById(R.id.btnXacNhan);
        chiTietDonHangController=new ChiTietDonHangAdminController(ChiTietDonHangAdMinActivity.this);
        toolbar= (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarChiTietDonHang);

    }

    public void getData(){
        position= getIntent().getIntExtra("chitietdonhang",0);
        status= getIntent().getIntExtra("status",0);
        //Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
        Log.d("ChiTietDonHangLog",status+"");
        Log.d("ChiTietDonHangLog",position+"");
        switch (status){
            case 0:
                chiTietDonHangArrayList= XacNhanFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=XacNhanFragment.donHangs.get(position);
                btnhuy.setText("Xác nhận");
                break;
            case 1:
                chiTietDonHangArrayList= DaGiaoFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaGiaoFragment.donHangs.get(position);
                btnhuy.setText("Đã giao");
                
                break;

        }


        chiTietDonHangArrayList= donHang.getArrayListchitietdonhang();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtngaythang.setText(donHang.getNgaythang());
        txtTongTien.setText("Thành tiền: "+decimalFormat.format(donHang.getTongtien())+"Đ");
        dongChiTietAdapter=new DongChiTietChoXacNhanAdapter(this,chiTietDonHangArrayList);
        listViewChiTiet.setAdapter(dongChiTietAdapter);
    }
    public void ActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);



    }
}