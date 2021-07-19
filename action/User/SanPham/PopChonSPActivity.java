package com.example.appbanhangnew.action.User.SanPham;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.DonHangActivity;
import com.example.appbanhangnew.action.User.DonHang.DonMuaActivity;
import com.example.appbanhangnew.action.User_Admin.Account.AccountActivity;
import com.example.appbanhangnew.adapter.User.ButtonAdapter;

import com.example.appbanhangnew.controller.User.PopThemVaoGioHangController;
import com.example.appbanhangnew.model.DonHang.GioHang;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class PopChonSPActivity extends AppCompatActivity {
    SanPham sanPham;
    //ChitietSPs chitietSPs;
    ButtonAdapter buttonAdapter;
    RecyclerView recyclerViewButton;
    ChitietSPs chitietSPs;
    public static ImageView imgeChiTietSP;
    public static TextView txtGiaChitiet;
    int Soluong=1;
    Button btnCong,btnTru;
    TextView txtSoLuong;
    int idUser;
    SharedPreferences sharedPreferences;
    PopThemVaoGioHangController popThemVaoGioHangController;
    int status=0;
    public static GioHang gioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_chon_s_p);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        AnhXa();


    }
    public  void AnhXa(){
        recyclerViewButton=(RecyclerView) findViewById(R.id.recycleViewListChiTiet);
        imgeChiTietSP=(ImageView) findViewById(R.id.imgChiTietImage);
        txtGiaChitiet=(TextView)findViewById(R.id.txtGiaChiTietSanPham);
        btnCong= (Button) findViewById(R.id.btnCong);
        btnTru=(Button) findViewById(R.id.btnTru);
        txtSoLuong=(TextView) findViewById(R.id.txtSoLuong);
        //chitietSPArrayList= new ArrayList<>();
        getData();
        Picasso.get().load(chitietSPs.getChitiet(0).getHinhanhChiTietsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(PopChonSPActivity.imgeChiTietSP);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        PopChonSPActivity.txtGiaChitiet.setText("Giá: "+ decimalFormat.format(chitietSPs.getChitiet(0).getGia())+" Đ");
        buttonAdapter=new ButtonAdapter(getApplicationContext(),sanPham,chitietSPs);
        recyclerViewButton.setHasFixedSize(true);
        recyclerViewButton.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewButton.setAdapter(buttonAdapter);

        Soluong=Integer.parseInt(txtSoLuong.getText().toString());
        sharedPreferences = this.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        idUser=sharedPreferences.getInt("iduser", 0);
        popThemVaoGioHangController=new PopThemVaoGioHangController(PopChonSPActivity.this);

    }
    public void getData(){
        sanPham=null;
        chitietSPs=null;
        //chitietSPArrayList= new ArrayList<>();
        //buttonAdapter.notifyDataSetChanged();
        chitietSPs= (ChitietSPs) getIntent().getSerializableExtra("ChitietSPs");
        sanPham=(SanPham) getIntent().getSerializableExtra("SanPham");


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btnThemVaoGioHang:
                if(idUser!=0){
                    if(ButtonAdapter.getDataItemCLick()==null){
                        Toast.makeText(getApplicationContext(),"Bạn phải chọn loại hàng trước khi thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    }else {
                        status=0;
                        popThemVaoGioHangController.ControllerbtnAddCard(idUser,ButtonAdapter.getDataItemCLick().getIdsp(),ButtonAdapter.getDataItemCLick().getIdChiTietSP()
                                ,Integer.parseInt(txtSoLuong.getText().toString().trim()), LocalDate.now().toString().trim());
                        //Toast.makeText(getApplicationContext(),ButtonAdapter.getDataItemCLick().getTenChiTietsp(),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Intent intent= new Intent(getBaseContext(), AccountActivity.class);
                    startActivity(intent);
                    finish();
                }



                break;
            case R.id.btnCong:
                Soluong++;
                if(Soluong==10){
                    btnCong.setEnabled(false);
                }
                if(Soluong>1){
                    btnTru.setEnabled(true);
                }
                txtSoLuong.setText(Soluong+"");
                break;
            case R.id.btnTru:
                Soluong--;
                if(Soluong==1){
                    btnTru.setEnabled(false);
                }
                if(Soluong<10){
                    btnCong.setEnabled(true);
                }
                txtSoLuong.setText(Soluong+"");
                break;




        }
    }
}