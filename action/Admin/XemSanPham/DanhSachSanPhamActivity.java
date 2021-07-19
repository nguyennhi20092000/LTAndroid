package com.example.appbanhangnew.action.Admin.XemSanPham;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.Admin.LoaispAdapter;
import com.example.appbanhangnew.controller.Admin.DanhSachSPAdminController;
import com.example.appbanhangnew.model.SanPham.Loaisp;


import java.util.ArrayList;

public class DanhSachSanPhamActivity extends AppCompatActivity {
    ListView listViewDanhMucAdmin;
    ArrayList<Loaisp> loaispArrayList;
    Toolbar toolbarDanhSachSP;
    LoaispAdapter loaispAdapter;
    DanhSachSPAdminController danhSachSPAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);
        AnhXa();
        ActionBar();
        danhSachSPAdmin.GetDuLieuLoaiSP(loaispArrayList,loaispAdapter,listViewDanhMucAdmin);
        danhSachSPAdmin.CatchOnItemListView(listViewDanhMucAdmin,loaispArrayList);

    }

    public void AnhXa(){
        listViewDanhMucAdmin=(ListView) findViewById(R.id.listDanhMucAdmin);
        loaispArrayList=new ArrayList<>();
        toolbarDanhSachSP=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarDanhSachSPAd);
        danhSachSPAdmin=new DanhSachSPAdminController(DanhSachSanPhamActivity.this);
        loaispAdapter=new LoaispAdapter(loaispArrayList,DanhSachSanPhamActivity.this);
        listViewDanhMucAdmin.setAdapter(loaispAdapter);
    }
    public void ActionBar() {
        setSupportActionBar(toolbarDanhSachSP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarDanhSachSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
}