package com.example.appbanhangnew.controller.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.XemSanPham.PopChiTietSPActivity;

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;

public class ChiTietSPAdminController {
    int SWIPE_THRESHOLD = 10;
    int SWIPE_VECLOCITY_THRESHOLD = 10;
    RecyclerView recyclerViewChonChiTietSP, recyclerViewSpTuongTu;
    ViewFlipper viewFlipper_ChitietSP;
    Context context;

    public ChiTietSPAdminController(int SWIPE_THRESHOLD, int SWIPE_VECLOCITY_THRESHOLD, RecyclerView recyclerViewChonChiTietSP, RecyclerView recyclerViewSpTuongTu, ViewFlipper viewFlipper_ChitietSP, Context context) {
        this.SWIPE_THRESHOLD = SWIPE_THRESHOLD;
        this.SWIPE_VECLOCITY_THRESHOLD = SWIPE_VECLOCITY_THRESHOLD;
        this.recyclerViewChonChiTietSP = recyclerViewChonChiTietSP;
        this.recyclerViewSpTuongTu = recyclerViewSpTuongTu;
        this.viewFlipper_ChitietSP = viewFlipper_ChitietSP;
        this.context = context;
    }
    //class dùng lắng nghe cử chỉ


    public void ActionBar(Toolbar toolbarChonChiTiet, SanPham getSP, ChitietSPs getChitietSP, String gia){

        toolbarChonChiTiet.inflateMenu(R.menu.menu_toolbar);
        toolbarChonChiTiet.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Log.d("123","123");
                switch (item.getItemId()){
                    case R.id.menu_start:
                        Intent intent= new Intent(context, PopChiTietSPActivity.class);
                        intent.putExtra("ChitietSPs",getChitietSP);
                        intent.putExtra("SanPham",getSP);
                        context.startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }
}

