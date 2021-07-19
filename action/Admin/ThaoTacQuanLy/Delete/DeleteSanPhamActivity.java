package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Delete;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Admin.Delete.DeleteChiTietSPController;
import com.example.appbanhangnew.controller.Admin.Delete.DeleteSPController;
import com.example.appbanhangnew.controller.Admin.Edit.EditSanPhamController;
import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class DeleteSanPhamActivity extends AppCompatActivity {
    Spinner spinnerLoaiSP;
    Spinner spinnerSanPham;
    DeleteSPController deleteSanPhamController;
    ArrayList<Loaisp> loaisps;
    ArrayList<SanPham> sanPhams;
    SanPham sanPham;
    TextInputEditText txttensp, txtlinkhinhanh,txtmota;
    Button btnEdit;
    Toolbar toolbarDeletesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_san_pham);
        AnhXa();
        ActionBar();
        deleteSanPhamController.getLoaiSP(loaisps,spinnerLoaiSP,0);
        spinnerLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deleteSanPhamController.getSP(sanPhams,spinnerSanPham,0,loaisps.get(position).get_idloaisp());
                spinnerSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        deleteSanPhamController.SetSP(sanPham,txttensp,txtlinkhinhanh,txtmota,sanPhams.get(position).getIdsp());


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txttensp.getText().toString().trim().equals("")|| txtlinkhinhanh.getText().toString().trim().equals("")||txtmota.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn phải điền đủ thông tin",Toast.LENGTH_LONG).show();
                }else {

                    SanPham _sanpham=sanPhams.get(spinnerSanPham.getSelectedItemPosition());
                    Log.d("EditSP",_sanpham.getIdsp()+"");
                    _sanpham.setHinhanhsp(txtlinkhinhanh.getText().toString().trim());
                    _sanpham.setMota(txtmota.getText().toString());
                    _sanpham.setTensp(txttensp.getText().toString());
                    deleteSanPhamController.DeleteSP(_sanpham);
                }
            }
        });


    }
    public void ActionBar() {
        setSupportActionBar(toolbarDeletesp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarDeletesp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    public  void AnhXa(){
        spinnerLoaiSP=(Spinner) findViewById(R.id.spinnerLoaiSP);
        spinnerSanPham=(Spinner) findViewById(R.id.spinnerSP);
        txttensp=(TextInputEditText)  findViewById(R.id.txtEdittenSP);
        txtlinkhinhanh=(TextInputEditText) findViewById(R.id.txtEditlinkchitietSP);
        txtmota=(TextInputEditText) findViewById(R.id.txtEditgiachitietSP);
        btnEdit=(Button) findViewById(R.id.btnEditSP);
        toolbarDeletesp = (Toolbar) findViewById(R.id.ToolbarDeletesp);
        deleteSanPhamController=new DeleteSPController(this);
        loaisps=new ArrayList<>();
        sanPhams=new ArrayList<>();
    }

}