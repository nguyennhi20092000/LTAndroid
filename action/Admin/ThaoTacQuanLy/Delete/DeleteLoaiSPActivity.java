package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Delete;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.controller.Admin.Delete.DeleteLoaiSPController;
import com.example.appbanhangnew.controller.Admin.Delete.DeleteSPController;
import com.example.appbanhangnew.controller.Admin.Edit.EditLoaiSPController;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class DeleteLoaiSPActivity extends AppCompatActivity {
    Spinner spinnerLoaiSP;
    Spinner spinnerSanPham;
    DeleteLoaiSPController deleteLoaiSanPhamController;
    ArrayList<Loaisp> loaisps;
    ArrayList<SanPham> sanPhams;
    SanPham sanPham;
    TextInputEditText txttenloaisp, txtlinkhinhanh,txtmota;
    Button btnEdit;
    Toolbar toolbarDeleteloaisp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_loai_s_p);
        AnhXa();
        ActionBar();
        deleteLoaiSanPhamController.getLoaiSP(loaisps,spinnerLoaiSP,0);
        spinnerLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deleteLoaiSanPhamController.setLoaisp(loaisps.get(position),txttenloaisp,txtlinkhinhanh,loaisps.get(position).get_idloaisp());
                deleteLoaiSanPhamController.getSP(sanPhams,1,loaisps.get(position).get_idloaisp());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txttenloaisp.getText().toString().trim().equals("") || txtlinkhinhanh.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Bạn phải điền đủ thông tin", Toast.LENGTH_LONG).show();
                } else {

                    Loaisp _loaisp = loaisps.get(spinnerLoaiSP.getSelectedItemPosition());
                    _loaisp.set_tenloaisp(txttenloaisp.getText().toString());
                    _loaisp.set_hinhanhloaisp(txtlinkhinhanh.getText().toString());
                    deleteLoaiSanPhamController.DeleteLoaiSP(loaisps.get(spinnerLoaiSP.getSelectedItemPosition()),sanPhams);
                    //Intent intent= new Intent(DeleteLoaiSPActivity.this, MainActivity.class);
                    //startActivity(intent);
                }

            }
        });

    }
    public void ActionBar() {
        setSupportActionBar(toolbarDeleteloaisp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarDeleteloaisp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    public  void AnhXa(){
        spinnerLoaiSP=(Spinner) findViewById(R.id.spinnerLoaiSP);
        txttenloaisp=(TextInputEditText)  findViewById(R.id.txtSuaTenloaiSP);
        txtlinkhinhanh=(TextInputEditText) findViewById(R.id.txtSualinkhinhanhLoaiSP);

        btnEdit=(Button) findViewById(R.id.btnSuaLoaiSP);
        loaisps=new ArrayList<>();
        deleteLoaiSanPhamController=new DeleteLoaiSPController(this);
        sanPhams=new ArrayList<>();
        toolbarDeleteloaisp = (Toolbar) findViewById(R.id.ToolbarDeleteLoaisp);

    }
}