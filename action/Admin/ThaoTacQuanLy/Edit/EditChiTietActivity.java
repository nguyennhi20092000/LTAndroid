package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.SanPham.ChitietSanPhamActivity;
import com.example.appbanhangnew.controller.Admin.Edit.EditChiTietSPController;
import com.example.appbanhangnew.controller.Admin.Edit.EditSanPhamController;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditChiTietActivity extends AppCompatActivity {
    Spinner spinnerLoaiSP, spinnerSanPham, spinnerChiTiet;
    EditChiTietSPController editChiTietSPController;
    ArrayList<Loaisp> loaisps;
    ArrayList<SanPham> sanPhams;
    ArrayList<ChitietSP> chitietSPs;
    TextInputEditText txttenchitietsp, txtlinkhinhanh,txtgia;
    Button btnEdit;
    ChitietSP chitietSP;
    Toolbar toolbarEditChiTietsp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chi_tiet);
        AnhXa();
        ActionBar();
        editChiTietSPController.getLoaiSP(loaisps,spinnerLoaiSP,0);
        spinnerLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editChiTietSPController.getSP(sanPhams,spinnerSanPham,0,loaisps.get(position).get_idloaisp());
                spinnerSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("ChiTietS",sanPhams.get(position).getIdsp()+"");
                        //editChiTietSPController.SetSP(sanPham,txttensp,txtlinkhinhanh,txtmota,sanPhams.get(position).getIdsp());
                        editChiTietSPController.getChiTiet(chitietSPs,spinnerChiTiet,sanPhams.get(position).getIdsp(),0);
                        spinnerChiTiet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                editChiTietSPController.SetChiTietSP(chitietSPs.get(position),txttenchitietsp,txtlinkhinhanh,txtgia,chitietSPs.get(position).getIdChiTietSP());
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txttenchitietsp.getText().toString().trim().equals("")|| txtlinkhinhanh.getText().toString().trim().equals("")||txtgia.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn phải điền đủ thông tin",Toast.LENGTH_LONG).show();
                }else {
                    try{
                        ChitietSP _chitietSP=chitietSPs.get(spinnerChiTiet.getSelectedItemPosition());
                        //Log.d("EditSP",_sanpham.getIdsp()+"");
                        _chitietSP.setHinhanhChiTietsp(txtlinkhinhanh.getText().toString().trim());
                        _chitietSP.setGia(Integer.parseInt(txtgia.getText().toString()));
                        _chitietSP.setTenChiTietsp(txttenchitietsp.getText().toString());
                        editChiTietSPController.EditChiTietSP(_chitietSP);
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Bạn vui lòng điền số",Toast.LENGTH_LONG).show();

                    }

                }

            }
        });

    }
    public void ActionBar() {
        setSupportActionBar(toolbarEditChiTietsp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarEditChiTietsp.setNavigationOnClickListener(new View.OnClickListener() {
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
        spinnerChiTiet=(Spinner) findViewById(R.id.spinnerChiTietSP);
        txttenchitietsp=(TextInputEditText)  findViewById(R.id.txtEdittenchitietSP);
        txtlinkhinhanh=(TextInputEditText) findViewById(R.id.txtEditlinkchitietSP);
        txtgia=(TextInputEditText) findViewById(R.id.txtEditgiachitietSP);
        btnEdit=(Button) findViewById(R.id.btnEditChiTietSP);
        toolbarEditChiTietsp = (Toolbar) findViewById(R.id.ToolbarEditChiTietsp);
        loaisps=new ArrayList<>();
        sanPhams=new ArrayList<>();
        chitietSPs=new ArrayList<>();
        editChiTietSPController=new EditChiTietSPController(this);
    }
}