package com.example.appbanhangnew.action.Admin.TheoDoiSP;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Admin.TheoDoiSP.ChartHoatDongController;

public class TheoDoiSPActivity extends AppCompatActivity {
    Button btnDanhSachSPbanChay, btnBieuDo, btnDanhSachkhachhang;
    Toolbar toolbartheodoisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theo_doi_s_p);
        AnhXa();
        ActionBar();
    }
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btnDanhSachBanChay:
                intent= new Intent(TheoDoiSPActivity.this,DanhSachSPBanChayActivity.class);
                startActivity(intent);
                break;
            case R.id.btnBieuDo:
                intent = new Intent(TheoDoiSPActivity.this, ChartHoatDongActivity.class);
                startActivity(intent);
                break;

        }
    }
    public void ActionBar() {
        setSupportActionBar(toolbartheodoisp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbartheodoisp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    public void AnhXa(){
        btnDanhSachSPbanChay=(Button) findViewById(R.id.btnDanhSachBanChay);
        toolbartheodoisp = (Toolbar) findViewById(R.id.toolbartheodoisp);
        btnBieuDo=(Button) findViewById(R.id.btnBieuDo);
    }
}