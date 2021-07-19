package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add.AddActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Delete.DeleteActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit.EditActivity;

public class ThaoTacQuanLyActivity extends AppCompatActivity {
    Button btnAdd, btnEdit, btnDelete;
    Toolbar toolbarTTQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thao_tac_quan_ly);
        AnhXa();
        ActionBar();
    }
    public void AnhXa(){
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnEdit=(Button) findViewById(R.id.btnEdit);
        btnDelete=(Button) findViewById(R.id.btnDelete);
        toolbarTTQL=(Toolbar) findViewById(R.id.ToolbarTTQL);
    }
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btnAdd:
                intent=new Intent(ThaoTacQuanLyActivity.this, AddActivity.class);
                break;
            case R.id.btnEdit:
                intent=new Intent(ThaoTacQuanLyActivity.this, EditActivity.class);
                break;
            default:
                intent=new Intent(ThaoTacQuanLyActivity.this, DeleteActivity.class);
                break;
        }
        startActivity(intent);
    }
    public void ActionBar() {
        setSupportActionBar(toolbarTTQL);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarTTQL.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
}