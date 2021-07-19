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

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.Fragment.ChoXacNhanFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaHuyFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaMuaFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaNhanFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaXacNhanFragment;
import com.example.appbanhangnew.adapter.User.DongChiTietChoXacNhanAdapter;
import com.example.appbanhangnew.controller.User.ChiTietDonHangController;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietDonHangActivity extends AppCompatActivity {
    ArrayList<ChiTietDonHang> chiTietDonHangArrayList;
    int position;
    ListView listViewChiTiet;
    DongChiTietChoXacNhanAdapter dongChiTietChoXacNhanAdapter;
    TextView txtngaythang, txtTongTien;
    Button btnhuy;
    ChiTietDonHangController chiTietDonHangController;
    Toolbar toolbar;
    int status;
    DonHang donHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        AnhXa();
        ActionBar();
        getData();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==0 ){
                    chiTietDonHangController.EditInformation(donHang.getIddonhang(),4);

                }else {

                        chiTietDonHangController.EditInformation(donHang.getIddonhang(),5);
                        //finish();

                }

                finish();
            }
        });
    }
    public void AnhXa(){
        chiTietDonHangArrayList=new ArrayList<>();
        listViewChiTiet= (ListView) findViewById(R.id.listchitietdonhang);

        txtngaythang=(TextView) findViewById(R.id.txtngaythang);
        txtTongTien=(TextView) findViewById(R.id.txtthanhtienchoxacnhan);
        btnhuy=(Button) findViewById(R.id.btnHuy);
        chiTietDonHangController=new ChiTietDonHangController(ChiTietDonHangActivity.this);
        toolbar= (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarChiTietDonHang);
    }

    public void getData(){
        position= getIntent().getIntExtra("chitietdonhang",0);
        status= getIntent().getIntExtra("trangthai",0);
        //Toast.makeText(getApplicationContext(),status+"",Toast.LENGTH_LONG).show();
        Log.d("ChiTietDonHangLog",status+"");
        Log.d("ChiTietDonHangLog",position+"");

        switch (status){
            case 0:
                chiTietDonHangArrayList= ChoXacNhanFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=ChoXacNhanFragment.donHangs.get(position);
                btnhuy.setText("Hủy đơn hàng");
                break;
            case 3:
                chiTietDonHangArrayList= DaNhanFragment.donHangs.get(position).getArrayListchitietdonhang();
                donHang=DaNhanFragment.donHangs.get(position);
                btnhuy.setText("Đã nhận hàng");
                break;


        }

       chiTietDonHangArrayList= donHang.getArrayListchitietdonhang();
       DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
       txtngaythang.setText(donHang.getNgaythang());
        txtTongTien.setText("Thành tiền: "+decimalFormat.format(donHang.getTongtien())+"Đ");
        dongChiTietChoXacNhanAdapter=new DongChiTietChoXacNhanAdapter(this,chiTietDonHangArrayList);
        listViewChiTiet.setAdapter(dongChiTietChoXacNhanAdapter);
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