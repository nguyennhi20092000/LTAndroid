package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appbanhangnew.R;

public class AddActivity extends AppCompatActivity {
    Toolbar toolbarAdd;
    Button btnAddLoaiSP, btnAddSanPham, btnaddChiTietSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        AnhXa();
        ActionBar();
    }
    public void AnhXa(){
        toolbarAdd=(Toolbar) findViewById(R.id.ToolbarAdd);
        btnAddLoaiSP=(Button) findViewById(R.id.btnAddLoaiSP);
        btnAddSanPham=(Button) findViewById(R.id.btnAddSP);
        btnaddChiTietSP=(Button) findViewById(R.id.btnAddChiTietSP);
    }
    public void ActionBar() {
        setSupportActionBar(toolbarAdd);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarAdd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btnAddLoaiSP:
                intent=new Intent(AddActivity.this,AddLoaiSPActivity.class);
                break;
            case R.id.btnAddSP:
                intent=new Intent(AddActivity.this,AddSanPhamActivity.class);
                break;
            default:
                intent=new Intent(AddActivity.this,AddChiTietSPActivity.class);
                break;
        }
        startActivity(intent);
    }
}