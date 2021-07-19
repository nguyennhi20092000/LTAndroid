package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appbanhangnew.R;

import android.widget.TextView;


import com.example.appbanhangnew.controller.Admin.Add.AddLoaiSPController;
import com.google.android.material.textfield.TextInputLayout;

public class AddLoaiSPActivity extends AppCompatActivity {
    Toolbar toolbarloaisp;
    Button btnaddloaisp;
    TextInputLayout TILTenloaiSP,TILLinkHinhAnhLoaiSP;
    TextView txtTenloaisp,txtHinhanhloaisp;
    AddLoaiSPController addloaispController;
    String tenloaisp;
    String hinhanhloaisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loai_s_p);
        Anhxa();
        ActionBar();
        btnaddloaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtTenloaisp.getText().toString().trim().equals("") ||txtHinhanhloaisp.getText().toString().trim().equals("")){
                    //Toast.makeText(AddLoaiSPActivity.this,"Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    //Log.d("CheckClick","ddd");
                } else{
                    tenloaisp = txtTenloaisp.getText().toString().trim();
                    hinhanhloaisp = txtHinhanhloaisp.getText().toString().trim();
                    addloaispController.Themloaisp(tenloaisp, hinhanhloaisp);
                    //finish();
                    //onDestroy();

                }

            }
        });



    }
    public void ActionBar() {
        setSupportActionBar(toolbarloaisp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarloaisp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    private void Anhxa(){
        btnaddloaisp = (Button)findViewById(R.id.btnAddLoaiSP);
        TILTenloaiSP = (TextInputLayout)findViewById(R.id.TILTenloaiSP);
        TILLinkHinhAnhLoaiSP = (TextInputLayout)findViewById(R.id.TILLinkHinhAnhLoaiSP);
        txtTenloaisp = (TextView)findViewById(R.id.txtTenloaiSP);
        txtHinhanhloaisp = (TextView)findViewById(R.id.txtlinkhinhanhLoaiSP);
        toolbarloaisp = (Toolbar) findViewById(R.id.ToolbarAddLoaisp);
        addloaispController = new AddLoaiSPController(AddLoaiSPActivity.this);
    }




}