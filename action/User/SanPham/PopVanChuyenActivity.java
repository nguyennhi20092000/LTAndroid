package com.example.appbanhangnew.action.User.SanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.model.SanPham.ChitietSP;

public class PopVanChuyenActivity extends AppCompatActivity {
    public static Toolbar toolbarDiaChi;
    String quan="";
    Button btnOK;
    public String type;
    public static TextView textViewGHTK;
    public static boolean setVanChuyen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_van_chuyen);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        btnOK=(Button) findViewById(R.id.btnOK) ;
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        getData();
        AnhXa();
    }
    public void getData(){
        //quan=getIntent().getStringExtra("quan");
        //type=getIntent().getStringExtra("type");

    }
    public void AnhXa(){
        toolbarDiaChi=(Toolbar) findViewById(R.id.ToolBar_DiaChi);
        btnOK =(Button)findViewById(R.id.btnOK);
        textViewGHTK=(TextView)findViewById(R.id.txtTiltileGHTK);
        setVanChuyen=false;
        //int x= ChitietSanPhamActivity.txtVanChuyen.getText().toString().trim().length();
        //toolbarDiaChi.setTitle(ChitietSanPhamActivity.txtVanChuyen.getText().toString().trim().substring(11,x));
        //textViewGHTK.setText(ChitietSanPhamActivity.txtPhiVC.getText().toString().trim());
        //toolbarDiaChi=(Toolbar) findViewById(R.id.ToolBar_DiaChi);
        /*if(quan==null){
            quan="";
            //toolbarDiaChi.setTitle("Vận chuyển đến: "+quan);
        }else {
            setVanChuyen=true;
            toolbarDiaChi.setTitle(quan);
            if(type.trim().equals("quan")){
                textViewGHTK.setText("22.000");
            }else {
                textViewGHTK.setText("35.000");
            }
        }*/

        toolbarDiaChi.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_start:
                        Intent intent= new Intent(PopVanChuyenActivity.this, DiaChiActivity.class);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setVanChuyen==true){
                    ChitietSanPhamActivity.txtPhiVC.setText(textViewGHTK.getText().toString());
                    ChitietSanPhamActivity.txtVanChuyen.setText("Vận chuyển: "+" "+toolbarDiaChi.getTitle().toString());
                    finish();
                }

            }
        });
    }

}