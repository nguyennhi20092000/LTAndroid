package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Admin.Add.AddChiTietSPController;
import com.example.appbanhangnew.controller.Admin.Add.AddSanPhamController;
import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;

import java.util.ArrayList;

public class AddChiTietSPActivity extends AppCompatActivity {
    Spinner spinnerloaisp, spinnerSp;
    TextView txttenchitietsp,txtlinkchitietsp, txtgiachitietsp;
    Button btnaddchitietsp;
    AddChiTietSPController addChiTietSPController;
    String tenchitietsp;
    String linkchitietsp;
    String giachitietsp;
    ArrayList<Loaisp> loaisps;
    ArrayList<SanPham> sanPhams;
    Toolbar toolbarAddChiTietSP;
    int idloaisp=0, idsp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chi_tiet_s_p);
        Anhxa();
        ActionBar();
        addChiTietSPController.getLoaiSP(loaisps,spinnerloaisp,idloaisp);
        spinnerloaisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addChiTietSPController.getSP(sanPhams,spinnerSp,idsp,position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnaddchitietsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txttenchitietsp.getText().toString().trim().equals("") ||txtlinkchitietsp.getText().toString().trim().equals("")||
                        txtgiachitietsp.getText().toString().trim().equals("")){
                    Toast.makeText(AddChiTietSPActivity.this,"Chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    //Log.d("CheckClick","ddd");
                } else{
                    try{
                        Log.d("ChiTietsP",sanPhams.get(spinnerSp.getSelectedItemPosition()).getIdsp()+"");
                        addChiTietSPController.ThemChiTietsp(new ChitietSP(sanPhams.get(spinnerSp.getSelectedItemPosition()).getIdsp(),1,txttenchitietsp.getText().toString().trim(),txtlinkchitietsp.getText().toString().trim(),Integer.parseInt(txtgiachitietsp.getText().toString().trim())));
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Bạn vui lòng điền số",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(toolbarAddChiTietSP);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarAddChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    private void Anhxa(){
        spinnerloaisp = (Spinner) findViewById(R.id.spinnerLoaiSP);
        spinnerSp=(Spinner) findViewById(R.id.spinnerSP);
        txttenchitietsp = (TextView) findViewById(R.id.txttenchitietSP);
        txtlinkchitietsp = (TextView) findViewById(R.id.txtlinkchitietSP);
        txtgiachitietsp = (TextView) findViewById(R.id.txtgiachitietSP);
        btnaddchitietsp = (Button) findViewById(R.id.btnAddChiTietSP);
        toolbarAddChiTietSP = (Toolbar) findViewById(R.id.ToolbarAddChiTietsp);
        addChiTietSPController = new AddChiTietSPController(AddChiTietSPActivity.this);
        loaisps=new ArrayList<>();
        sanPhams=new ArrayList<>();
        idloaisp=getIntent().getIntExtra("idloaisp",0);
        idsp=getIntent().getIntExtra("idsp",0);
    }
}