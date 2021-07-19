package com.example.appbanhangnew.action.User.DonHang;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.GioHang.GioHangActivity;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.adapter.User.DonHangAdapter;
import com.example.appbanhangnew.adapter.User.GioHangAdapter;
import com.example.appbanhangnew.controller.User.DonHangController;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.DonHang;
import com.example.appbanhangnew.model.DonHang.GioHang;
import com.example.appbanhangnew.model.User;
import com.example.appbanhangnew.until.Server;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.ArrayList;

public class DonHangActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbarthanhtoan, toolbardiachinhanhang;
    public static TextView txtdiachinhanhang, txttennguoinhan, txtsodienthoainguoinhan, txtphivanchuyen, txtTongtien;
    ListView listViewSPduocChon;
    Button btnMuaHang;

    ArrayList<ChiTietDonHang> gioHangArrayList;
    DonHangAdapter donHangAdapter;
    SharedPreferences sharedPreferences;
    User user;
    DonHang donHang;
    DonHangController donHangController;
    ArrayList<ChiTietDonHang> chiTietDonHangs;
    ArrayList<GioHang> gioHangs;

    public static boolean setVanChuyen = false;

    public static int Tongtien = 0;
    int status = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        AnhXa();
        AnhXa_User();
        //ActionBar();
        toolbardiachinhanhang.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_start:
                        Intent intent = new Intent(DonHangActivity.this, VanChuyenDonHangActivity.class);
                        intent.putExtra("tennguoinhan", user.getTen());
                        intent.putExtra("sodienthoai", user.getSodienthoai());
                        intent.putExtra("diachi", user.getDiachi());
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (setVanChuyen == true) {
                    donHang = new DonHang(user.getIduser(), 1, LocalDate.now().toString(), txtsodienthoainguoinhan.getText().toString().trim(),
                            txtdiachinhanhang.getText().toString().trim(), user.getEmail(), Tongtien, 1, chiTietDonHangs);
                    donHangController.AddDonHang(donHang, gioHangArrayList, gioHangs);
                    Intent intent = new Intent(DonHangActivity.this, MainActivity.class);
                    //DonMuaActivity.tabLayout.getTabAt(0).select();
                    startActivity(intent);
                    finish();

                }


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AnhXa() {
        toolbarthanhtoan = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarThanhToan);
        toolbardiachinhanhang = (Toolbar) findViewById(R.id.toolbarDiaChiNhanHang);
        txtdiachinhanhang = (TextView) findViewById(R.id.txtdiachinhanhang);
        txttennguoinhan = (TextView) findViewById(R.id.txttennguoinhan);
        txtsodienthoainguoinhan = (TextView) findViewById(R.id.txtsodienthoainguoinhan);
        txtphivanchuyen = (TextView) findViewById(R.id.txtPhiVC);
        txtTongtien = (TextView) findViewById(R.id.txttongtien);
        listViewSPduocChon = (ListView) findViewById(R.id.listviewSPChon);
        btnMuaHang = (Button) findViewById(R.id.btnmuahang);
        gioHangArrayList = new ArrayList<>();
        gioHangs = new ArrayList<>();
        status = getIntent().getIntExtra("status", 0);
        //ArrayList<GioHang> gioHangs1= new ArrayList<>();
        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<GioHang> _gioHangs = (ArrayList<GioHang>) extra.getSerializable("giohang");
        //Log.d("GioHangABC", objects.size()+"");

        for (int i = 0; i < _gioHangs.size(); i++) {
            if (_gioHangs.get(i).getCheck() == true) {
                GioHang gioHang = _gioHangs.get(i);
                gioHangs.add(_gioHangs.get(i));
                ChiTietDonHang chiTietDonHang = new ChiTietDonHang(1, 1, gioHang.getIdsp(), gioHang.getIdchitietsp(),
                        gioHang.getSoluong(), gioHang.getGia(), gioHang.getTensp(), gioHang.getTenchitietsp(), gioHang.getHinhanhsp(), LocalDate.now().toString());
                gioHangArrayList.add(chiTietDonHang);
                //
            }
        }


        Tongtien = 0;
        for (int i = 0; i < gioHangArrayList.size(); i++) {
            Tongtien = Tongtien + (gioHangArrayList.get(i).getGia() * gioHangArrayList.get(i).getSoluong());
        }

        donHangAdapter = new DonHangAdapter(getApplicationContext(), gioHangArrayList);
        listViewSPduocChon.setAdapter(donHangAdapter);
        sharedPreferences = this.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        donHangController = new DonHangController(DonHangActivity.this, gioHangArrayList, gioHangs);
        chiTietDonHangs = new ArrayList<>();


    }

    public void AnhXa_User() {

        Toast.makeText(getApplicationContext(), sharedPreferences.getInt("iduser", 0) + " ", Toast.LENGTH_LONG).show();
        user = new User(sharedPreferences.getInt("iduser", 0), sharedPreferences.getString("sodienthoai", ""), sharedPreferences.getString("password", ""),
                sharedPreferences.getString("ten", ""), sharedPreferences.getString("ngaysinh", ""), sharedPreferences.getString("diachi", ""),
                sharedPreferences.getString("email", ""), sharedPreferences.getString("image", ""));

        txttennguoinhan.setText("Người nhận: " + user.getTen());
        txtsodienthoainguoinhan.setText("SĐT: " + user.getSodienthoai());
        txtdiachinhanhang.setText("Địa chỉ: " + user.getDiachi());


    }

//    public void ActionBar() {
//        setSupportActionBar(toolbarthanhtoan);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
//        toolbarthanhtoan.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);
//
//
//    }


}