package com.example.appbanhangnew.action.User.DonHang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.User.DanhGiaConTroller;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DanhGiaActivity extends AppCompatActivity {
    ImageView imageView;
    TextView txttensp, txtgia;
    RatingBar ratingBar;
    Button btnDanhgia;
    int idsp, gia, iduser;
    String hinhanhsp,tensp;
    SharedPreferences sharedPreferences;
    float danhgia;
    DanhGiaConTroller danhGiaConTroller;
    int iddonhang;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        AnhXa();
        ActionBar();
        AnhXa_User();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                danhgia=rating;
            }
        });
        btnDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danhGiaConTroller.ThemRatting(idsp,iduser,danhgia,iddonhang);
                //Toast.makeText(getApplicationContext(),idsp+iduser+danhgia+"",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    public void AnhXa(){
        imageView=(ImageView) findViewById(R.id.imgAnhSP);
        txttensp=(TextView) findViewById(R.id.txttensp);
        txtgia=(TextView) findViewById(R.id.txtDongia);
        btnDanhgia=(Button) findViewById(R.id.btnDanhGia);
        ratingBar=(RatingBar) findViewById(R.id.rattingbar);
        danhGiaConTroller=new DanhGiaConTroller(this);
        toolbar = (Toolbar) findViewById(R.id.toolbardanhgia);
    }
    public void AnhXa_User(){
        sharedPreferences = this.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        iduser=sharedPreferences.getInt("iduser", 0);
        idsp=getIntent().getIntExtra("idsp",0);
        hinhanhsp=getIntent().getStringExtra("hinhanhsp");
        gia=getIntent().getIntExtra("gia",0);
        tensp=getIntent().getStringExtra("tensp");
        iddonhang=getIntent().getIntExtra("iddonhang",0);
        txttensp.setText(tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+decimalFormat.format(gia)+"Đ");

        Picasso.get().load(hinhanhsp)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageView);

    }
    public void ActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);



    }
}