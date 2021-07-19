package com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.Add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Admin.Add.AddSanPhamController;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import java.util.ArrayList;

public class AddSanPhamActivity extends AppCompatActivity {
    Spinner spinnerloaisp;
    TextView txttensp,txtlinksp, txtmotasp;
    Button btnaddsp;
    AddSanPhamController addSanPhamController;
    SanPham sanPham ;
    ArrayList<Loaisp> loaisps;
    Toolbar toolbarAddsp;
    int idloaisp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_san_pham);
        Anhxa();
        ActionBar();
        addSanPhamController.getLoaiSP(loaisps,spinnerloaisp,idloaisp);
        btnaddsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtlinksp.getText().toString().trim().equals("")|| txtmotasp.getText().toString().trim().equals("")||txttensp.getText().toString().trim().equals("") ){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ thông tin cần thiết",Toast.LENGTH_LONG).show();
                }else {
                    //Log.d("LoaiSP",loaisps.get(spinnerloaisp.getSelectedItemPosition()).get_tenloaisp());
                    sanPham=new SanPham(1,txttensp.getText().toString(),txtlinksp.getText().toString(),txtmotasp.getText().toString(),loaisps.get(spinnerloaisp.getSelectedItemPosition()).get_idloaisp());
                    addSanPhamController.Themsp(sanPham);
                    //finish();
                    //onDestroy();
                }
            }
        });




    }
    public void ActionBar() {
        setSupportActionBar(toolbarAddsp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarAddsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    private void Anhxa(){
        spinnerloaisp = (Spinner) findViewById(R.id.spinnerLoaiSP);
        txttensp = (TextView) findViewById(R.id.txttenSP);
        txtlinksp = (TextView) findViewById(R.id.txtlinkSP);
        txtmotasp = (TextView) findViewById(R.id.txtmotaSP);
        btnaddsp = (Button) findViewById(R.id.btnAddSP);
        toolbarAddsp = (Toolbar) findViewById(R.id.ToolbarAddsp);
        addSanPhamController = new AddSanPhamController(AddSanPhamActivity.this);
        loaisps=new ArrayList<>();
        idloaisp=getIntent().getIntExtra("idloaisp",0);
    }


}