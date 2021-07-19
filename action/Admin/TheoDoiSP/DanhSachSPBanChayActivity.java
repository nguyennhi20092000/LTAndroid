package com.example.appbanhangnew.action.Admin.TheoDoiSP;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.Admin.SPBanChayAdapter;
import com.example.appbanhangnew.controller.Admin.TheoDoiSP.SPBanChayController;
import com.example.appbanhangnew.model.SPBanChay;

import java.util.ArrayList;

public class DanhSachSPBanChayActivity extends AppCompatActivity {
    Toolbar toolbardanhsachsp;
    ListView listViewChiTiet;
    SPBanChayAdapter spBanChayAdapter;
    ArrayList<SPBanChay> spBanChays;
    SPBanChayController spBanChayController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_s_p_ban_chay);
        AnhXa();
        ActionBar();
        spBanChayController.getLoaiSP(spBanChays,spBanChayAdapter);
        listViewChiTiet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachSPBanChayActivity.this, ChiTietBanChayActivity.class);
                intent.putExtra("idsp",spBanChays.get(position).getIdsp());
                startActivity(intent);
            }
        });
    }
    public void ActionBar() {
        setSupportActionBar(toolbardanhsachsp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbardanhsachsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    public void AnhXa(){
        listViewChiTiet=(ListView) findViewById(R.id.listSPbanChay);
        toolbardanhsachsp=(Toolbar) findViewById(R.id.toolbardanhsachsp);
        spBanChays=new ArrayList<>();
        spBanChayAdapter=new SPBanChayAdapter(spBanChays,this);
        listViewChiTiet.setAdapter(spBanChayAdapter);
        spBanChayController=new SPBanChayController(this);
    }
}