package com.example.appbanhangnew.action.User.GioHang;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.User.EditGioHangController;
import com.example.appbanhangnew.model.DonHang.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;


public class EditGioHangActivity extends AppCompatActivity {

    ImageView imageViewSP;
    TextView txtTenSP, txtGiaSP, txtThanhTien,txtSoLuongHT;
    Spinner spinnerThem;
    Button btnEdit;
    DecimalFormat decimalFormat;
    String tensp,tenchitietsp,hinhanhsp;
    int gia,soluong,idgiohang;
    EditGioHangController editGioHangController;
    int soluongthem;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gio_hang);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        AnhXa();
        getData();
        this.spinnerThem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int soluonghientai=Integer.parseInt(txtSoLuongHT.getText().toString().trim());
                soluongthem= (int) spinnerThem.getItemAtPosition(position);
                txtThanhTien.setText("Thành tiền: "+decimalFormat.format((soluonghientai+soluongthem)*gia)+" Đ");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                editGioHangController.EditDioHang(idgiohang,soluongthem+soluong, LocalDate.now().toString().trim());
                GioHangActivity.getGioHang();
                finish();

            }
        });
    }

    public void AnhXa(){
        imageViewSP =(ImageView) findViewById(R.id.imgSP);
        txtTenSP=(TextView) findViewById(R.id.txtTenSPGio);
        txtGiaSP=(TextView) findViewById(R.id.txtGiaSPGH);
        txtThanhTien = (TextView) findViewById(R.id.txtthanhtien);
        txtSoLuongHT=(TextView) findViewById(R.id.txtSoluonghientai);
        spinnerThem=(Spinner) findViewById(R.id.spinnerThem);
        btnEdit=(Button) findViewById(R.id.btnEdit);
        editGioHangController=new EditGioHangController(EditGioHangActivity.this);
        decimalFormat= new DecimalFormat("###,###,###");



    }

    public void getData(){

        tensp=getIntent().getStringExtra("tenSP");
        tenchitietsp=getIntent().getStringExtra("tenChiTiet");
        gia=getIntent().getIntExtra("Gia",0);
        soluong=getIntent().getIntExtra("SoLuong",0);
        hinhanhsp=getIntent().getStringExtra("HinhAnh");
        idgiohang=getIntent().getIntExtra("idgiohang",0);

        ArrayList<Integer> so= new ArrayList<>();
        for(int i=-soluong;i<soluong+1;i++){
            so.add(i);
        }
        ArrayAdapter spinnerAdapter= new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,so);
        spinnerThem.setAdapter(spinnerAdapter);
        int pos = spinnerAdapter.getPosition(0);
        spinnerThem.setSelection(pos);
        txtTenSP.setText("["+tenchitietsp+"] "+ tensp);

        txtGiaSP.setText("Giá: "+decimalFormat.format(gia)+" Đ");
        txtSoLuongHT.setText(soluong+"");
        txtThanhTien.setText("Thành tiền: "+decimalFormat.format(gia*soluong)+" Đ");
        Picasso.get().load(hinhanhsp)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageViewSP);
    }
}