package com.example.appbanhangnew.action.User_Admin.ThietLapTaiKhoan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User_Admin.Account.AccountActivity;
import com.example.appbanhangnew.until.Server;
import com.squareup.picasso.Picasso;

public class ThietLapTaiKhoanActivity extends AppCompatActivity {
    Toolbar toolbarthietlap, toolbarInfo, toolbarDoiMK,toolbarDangXuat;
    public static int ID_USER = 0;
    ImageView imgMyUser;
    TextView txtMyUser;
    String linkgetAvar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_tai_khoan);
        AnhXa();
        ActionBar();
    }

    public void AnhXa() {
        toolbarthietlap = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarthietlap);
        toolbarInfo = (Toolbar) findViewById(R.id.toolbarInformation);
        toolbarDoiMK = (Toolbar) findViewById(R.id.toolbarDoiMatKhau);
        imgMyUser=(ImageView) findViewById(R.id.imgME);
        txtMyUser=(TextView) findViewById(R.id.txtNameUser);
        toolbarDangXuat=(Toolbar) findViewById(R.id.toolbarDangXuat);
        sharedPreferences=getSharedPreferences("USER_FILE", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        ID_USER = sharedPreferences.getInt("iduser", 0);
        linkgetAvar = sharedPreferences.getString("image", "");
        txtMyUser.setText(sharedPreferences.getString("ten",""));
        Picasso.get().load(Server.duongdanHTTP+linkgetAvar)
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(imgMyUser);
        toolbarInfo.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_start:
                        Intent intent = new Intent(ThietLapTaiKhoanActivity.this, InformationActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        toolbarDoiMK.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_start:
                        Intent intent = new Intent(ThietLapTaiKhoanActivity.this, DoiMatKhauActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        toolbarDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent= new Intent(ThietLapTaiKhoanActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void ActionBar() {
        setSupportActionBar(toolbarthietlap);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarthietlap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
}