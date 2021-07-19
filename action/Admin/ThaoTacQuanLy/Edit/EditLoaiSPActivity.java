package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Edit;
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
import com.example.appbanhangnew.controller.Admin.Edit.EditLoaiSPController;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditLoaiSPActivity extends AppCompatActivity {
    Spinner spinnerLoaiSP;
    EditLoaiSPController editLoaiSP;
    ArrayList<Loaisp> loaisps;
    TextInputEditText txttenloaisp, txtlinkhinhanh;
    Button btnEdit;
    Loaisp loaisp;
    Toolbar toolbarEditloaisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loai_s_p);
        AnhXa();
        ActionBar();
        editLoaiSP.getLoaiSP(loaisps,spinnerLoaiSP,0);
        spinnerLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editLoaiSP.setLoaisp(loaisp,txttenloaisp,txtlinkhinhanh,loaisps.get(position).get_idloaisp());
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
                    editLoaiSP.EditLoaiSP(_loaisp);
                }

            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(toolbarEditloaisp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarEditloaisp.setNavigationOnClickListener(new View.OnClickListener() {
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
        toolbarEditloaisp = (Toolbar) findViewById(R.id.ToolbarEditLoaisp);
        btnEdit=(Button) findViewById(R.id.btnSuaLoaiSP);
        loaisps=new ArrayList<>();
        editLoaiSP=new EditLoaiSPController(this);
    }
}