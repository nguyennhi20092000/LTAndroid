package com.example.appbanhangnew.action.User.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.DonMuaActivity;
import com.example.appbanhangnew.action.User.GioHang.GioHangActivity;
import com.example.appbanhangnew.action.User_Admin.Account.AccountActivity;
import com.example.appbanhangnew.action.User_Admin.ThietLapTaiKhoan.ThietLapTaiKhoanActivity;
import com.example.appbanhangnew.until.Server;
import com.squareup.picasso.Picasso;


public class MeFragment extends Fragment {

    ImageView imgMyUser;
    TextView txtMyUser;
    Toolbar toolbarDonMua, toolbarMuaLai,toolbarThietLap;
    Context context;
    public static int ID_USER=0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String linkgetAvar;

    public  MeFragment(Context context){
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();

    }

    public void AnhXa(){
        imgMyUser=(ImageView) getView().findViewById(R.id.imgME);
        txtMyUser =(TextView) getView().findViewById(R.id.txtNameUser);
        toolbarDonMua=(Toolbar) getView().findViewById(R.id.toolbarDonmua);
        toolbarMuaLai=(Toolbar) getView().findViewById(R.id.toolbarMualai);
        toolbarThietLap=(Toolbar) getView().findViewById(R.id.toolbarthietlaptaikhoan);
        sharedPreferences=context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        ID_USER=sharedPreferences.getInt("iduser",0);
        linkgetAvar=sharedPreferences.getString("image","");
        if(ID_USER!=0){
            txtMyUser.setText(sharedPreferences.getString("ten",""));
            Picasso.get().load(Server.duongdanHTTP+linkgetAvar)
                    .placeholder(R.drawable.noimage).error(R.drawable.error)
                    .into(imgMyUser);
        }

        toolbarThietLap.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_start:
                        if(ID_USER==0){
                            Intent intent= new Intent(context, AccountActivity.class);
                            context.startActivity(intent);
                        }else {
                            Intent intent= new Intent(context, ThietLapTaiKhoanActivity.class);
                            context.startActivity(intent);
                        }

                        break;
                }
                return true;
            }
        });
        toolbarDonMua.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_start:
                        if(ID_USER==0){
                            Intent intent= new Intent(context, AccountActivity.class);
                            context.startActivity(intent);
                        }else {
                            Intent intent= new Intent(context, GioHangActivity.class);
                            context.startActivity(intent);
                        }

                        break;
                }
                return true;
            }
        });
        toolbarMuaLai.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_start:
                        if(ID_USER==0){
                            Intent intent= new Intent(context, AccountActivity.class);
                            context.startActivity(intent);
                        }else {
                            Intent intent= new Intent(context, DonMuaActivity.class);
                            context.startActivity(intent);
                        }

                        break;
                }
                return true;
            }
        });


    }


}