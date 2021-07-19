package com.example.appbanhangnew.action.Admin.FragmentAdmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.DonHang.XacNhanDonHangActivity;
import com.example.appbanhangnew.action.Admin.XemSanPham.DanhSachSanPhamActivity;
import com.example.appbanhangnew.action.Admin.ThaoTacQuanLy.ThaoTacQuanLyActivity;
import com.example.appbanhangnew.action.Admin.TheoDoiSP.TheoDoiSPActivity;


public class HomeAdminFragment extends Fragment {

    Context context;
    Toolbar toolbarDanhSachSP, toolbarThaoTacQuanLy, toolbarTheoDoiSP, toolbarTrangThaiDonHang;

    public HomeAdminFragment(Context context) {
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
        return inflater.inflate(R.layout.fragment_home_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
    }
    public void AnhXa(){
        toolbarDanhSachSP=(Toolbar) getView().findViewById(R.id.toolbarDanhSachSanPham);
        toolbarThaoTacQuanLy=(Toolbar)getView().findViewById(R.id.toolbarThaoTacQuanLy);
        toolbarTheoDoiSP=(Toolbar) getView().findViewById(R.id.toolbarTheoDoi);
        toolbarTrangThaiDonHang=(Toolbar)  getView().findViewById(R.id.toolbarTrangthaidonhang);
        toolbarDanhSachSP.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent= new Intent(context, DanhSachSanPhamActivity.class);
                startActivity(intent);
                return true;
            }
        });

        toolbarTheoDoiSP.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(context, TheoDoiSPActivity.class);
                startActivity(intent);
                return true;
            }
        });

        toolbarThaoTacQuanLy.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent= new Intent(context, ThaoTacQuanLyActivity.class);
                startActivity(intent);
                return true;
            }
        });
        toolbarTrangThaiDonHang.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent= new Intent(context, XacNhanDonHangActivity.class);
                intent.putExtra("status",1);
                startActivity(intent);
                return true;
            }
        });
    }

}