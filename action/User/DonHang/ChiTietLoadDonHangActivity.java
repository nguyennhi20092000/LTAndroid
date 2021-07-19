package com.example.appbanhangnew.action.User.DonHang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.Fragment.ChoXacNhanFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaHuyFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaMuaFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaNhanFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaXacNhanFragment;
import com.example.appbanhangnew.adapter.User.ChiTietLoadDonHangAdapter;
import com.example.appbanhangnew.adapter.User.ChiTietSPMuaAdapter;
import com.example.appbanhangnew.adapter.User.DongChiTietChoXacNhanAdapter;
import com.example.appbanhangnew.controller.User.ChiTietDonHangController;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietLoadDonHangActivity extends AppCompatActivity {
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    int position;
    ListView listViewChiTiet;
    ChiTietLoadDonHangAdapter dongChiTietChoXacNhanAdapter;
    TextView txtngaythang, txtTongTien;
    Button btnhuy;
    ChiTietDonHangController chiTietDonHangController;
    int status;
    DonHang donHang;
    androidx.appcompat.widget.Toolbar toolbarLoad;
    ChiTietSPMuaAdapter chiTietSPMuaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_load_don_hang);
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

        chiTietDonHangController=new ChiTietDonHangController(ChiTietLoadDonHangActivity.this);
        //chiTietSPMuaAdapter=new ChiTietSPMuaAdapter(ChiTietLoadDonHangActivity.this);
    }

    public void getData(){
        position= getIntent().getIntExtra("chitietdonhang",0);
        status= getIntent().getIntExtra("trangthai",0);
        //Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
        Log.d("ChiTietDonHangLog",status+"");
        Log.d("ChiTietDonHangLog",position+"");

        switch (status){
            case 2:
                chiTietDonHangArrayList= DaXacNhanFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaXacNhanFragment.donHangs.get(position);
                break;

            case 4:
                chiTietDonHangArrayList= DaHuyFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaHuyFragment.donHangs.get(position);
                break;
            case 5:
                chiTietDonHangArrayList= DaMuaFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaMuaFragment.donHangs.get(position);
                break;
        }
        if(status ==5){
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtngaythang.setText(donHang.getNgaythang());
            int thanhtien=0;
            txtTongTien.setText("Thành tiền: "+decimalFormat.format(donHang.getTongtien())+"Đ");
            chiTietSPMuaAdapter=new ChiTietSPMuaAdapter(ChiTietLoadDonHangActivity.this,chiTietDonHangArrayList);
            listViewChiTiet.setAdapter(chiTietSPMuaAdapter);

        }else {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtngaythang.setText(donHang.getNgaythang());
            int thanhtien=0;

            txtTongTien.setText("Thành tiền: "+decimalFormat.format(donHang.getTongtien())+"Đ");
            dongChiTietChoXacNhanAdapter=new ChiTietLoadDonHangAdapter(this,chiTietDonHangArrayList);
            listViewChiTiet.setAdapter(dongChiTietChoXacNhanAdapter);
        }



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