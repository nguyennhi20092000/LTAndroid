package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddChiTietSPActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddLoaiSPActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddSanPhamActivity;

public class EditActivity extends AppCompatActivity {
    Toolbar toolbarEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        AnhXa();
        ActionBar();
    }
    public void AnhXa(){
        toolbarEdit=(Toolbar) findViewById(R.id.ToolbarEdit);
    }
    public void ActionBar() {
        setSupportActionBar(toolbarEdit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarEdit.setNavigationOnClickListener(new View.OnClickListener() {
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
            case R.id.btnEditLoaiSP:
                intent=new Intent(EditActivity.this, EditLoaiSPActivity.class);
                break;
            case R.id.btnEditSP:
                intent=new Intent(EditActivity.this,EditSanPhamActivity.class);
                break;
            default:
                intent=new Intent(EditActivity.this, EditChiTietActivity.class);
                break;
        }
        startActivity(intent);
    }
}