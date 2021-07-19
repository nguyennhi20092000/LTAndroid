package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Delete;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit.EditActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit.EditChiTietActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit.EditLoaiSPActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit.EditSanPhamActivity;
import com.example.appbanhangnew.controller.Admin.Delete.DeleteLoaiSPController;

public class DeleteActivity extends AppCompatActivity {
    Toolbar toolbarDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        AnhXa();
        ActionBar();
    }
    public void AnhXa(){
        toolbarDelete=(Toolbar) findViewById(R.id.toolbarDelete);
    }
    public void ActionBar() {
        setSupportActionBar(toolbarDelete);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarDelete.setNavigationOnClickListener(new View.OnClickListener() {
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
            case R.id.btnXoaLoaiSP:
                intent=new Intent(DeleteActivity.this, DeleteLoaiSPActivity.class);
                break;
            case R.id.btnXoaSP:
                intent=new Intent(DeleteActivity.this, DeleteSanPhamActivity.class);
                break;
            default:
                intent=new Intent(DeleteActivity.this, DeleteChiTietSPActivity.class);
                break;
        }
        startActivity(intent);
    }
}