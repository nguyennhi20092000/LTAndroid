package com.example.appbanhangnew.action.Admin.TheoDoiSP;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.Admin.SPBanChayAdapter;
import com.example.appbanhangnew.controller.Admin.TheoDoiSP.SPBanChayController;
import com.example.appbanhangnew.model.SPBanChay;

import java.util.ArrayList;

public class ChiTietBanChayActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewChiTiet;
    SPBanChayAdapter spBanChayAdapter;
    ArrayList<SPBanChay> spBanChays;
    SPBanChayController spBanChayController;
    int idsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban_chay);
        AnhXa();
        ActionBar();
       spBanChayController.getSP(spBanChays,idsp,spBanChayAdapter);
    }
    public void AnhXa(){
        listViewChiTiet=(ListView) findViewById(R.id.listSPbanChay);
        toolbar=(Toolbar) findViewById(R.id.toolbartheodoisp);
        spBanChays=new ArrayList<>();
        spBanChayAdapter=new SPBanChayAdapter(spBanChays,this);
        listViewChiTiet.setAdapter(spBanChayAdapter);
        spBanChayController=new SPBanChayController(this);
        idsp=getIntent().getIntExtra("idsp",0);
    }
    public void ActionBar() {
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
