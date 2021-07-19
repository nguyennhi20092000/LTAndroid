package com.example.appbanhangnew.action.User.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.ViewFlipper;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.GioHang.GioHangActivity;
import com.example.appbanhangnew.action.User.SanPham.ChitietSanPhamActivity;
import com.example.appbanhangnew.action.User.Search.SearchActivity;
import com.example.appbanhangnew.adapter.User.LoaispAdapter;
import com.example.appbanhangnew.adapter.User.SPNewAdapter;
import com.example.appbanhangnew.adapter.User.TongSPAdapter;
import com.example.appbanhangnew.controller.User_Admin.MainController;

import com.example.appbanhangnew.model.DonHang.GioHang;
import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewSanPhamMoi;
    RecyclerView recyclerViewSanPhambanchay;
    RecyclerView recyclerViewSanPham;
    RecyclerView recyclerViewDanhMuc;

    ArrayList<Loaisp> arrayListLoaisp;
    LoaispAdapter loaispAdapter;


    ArrayList<SanPham> arrSanPham;
    ArrayList<Loaisp> listHelp;

    ArrayList<ChitietSP> chitietSPsnew= new ArrayList<>();
    ChitietSPs _chitietSPsNew;
    ArrayList<ChitietSPs> arrayListsChitietSPNew;
    ArrayList<SanPham> arrayListSanPhamNew;
    private SPNewAdapter spNewAdapter;

    ArrayList<SanPham> arrayListTongSanPham;
    //public static SPadapter spAdapter;
    TongSPAdapter tongSPAdapter;
    ArrayList<ChitietSPs> arrayListsChitietTongSP;

    MainController mainController;

    BottomNavigationView bottomNavigationView;
    Context context;
    SearchView searchView;

    public HomeFragment(Context context){
        this.context= context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa();
        if(CheckConnection.haveNetworkConnection(context)){
           // ActionBar();
            mainController.ActionViewFlipper(viewFlipper, context);
            mainController.GetDuLieuDanhMuc(arrayListLoaisp,loaispAdapter,context);
            mainController.GetDataSPNew(context,arrayListSanPhamNew,spNewAdapter,chitietSPsnew,arrayListsChitietSPNew,_chitietSPsNew);
            mainController.GetDataTongSP(arrayListTongSanPham,tongSPAdapter,chitietSPsnew,arrayListsChitietTongSP,_chitietSPsNew,context);
            // mainController.CatchOnItemListView(listViewmanhinhchinh,drawerLayout,ID_USER,editor,HomeFragment.this);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //Toast.makeText(context,query,Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(context, SearchActivity.class);
                    intent.putExtra("SearchText",query);
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //Toast.makeText(context,newText,Toast.LENGTH_LONG).show();
                    return true;
                }
            });
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_giohang:
                            Intent intent= new Intent(context, GioHangActivity.class);
                            startActivity(intent);
                            //Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                            break;
                    }
                    return true;
                }
            });


        }else {
            CheckConnection.Show_Tost_Short(context,"Hãy kiểm tra lại kết nối");
            //context.finish();
        }
    }

    private void AnhXa() {
        toolbar= (androidx.appcompat.widget.Toolbar) getView().findViewById(R.id.toolbarSearchMain);
        viewFlipper=(ViewFlipper) getView().findViewById(R.id.viewFlipper);




        searchView=(SearchView)getView().findViewById(R.id.txtSearch);

        bottomNavigationView=(BottomNavigationView) getView().findViewById(R.id.bottomnavigation);
        mainController= new MainController();
        arrSanPham= new ArrayList<>();

        SetAdapter();


    }
    public void SetAdapter(){
        recyclerViewDanhMuc=(RecyclerView) getView().findViewById(R.id.RecycleViewLoaiSP);
        recyclerViewSanPhamMoi=(RecyclerView) getView().findViewById(R.id.recycleViewSPMoiNhat);
        recyclerViewSanPham=(RecyclerView)getView().findViewById(R.id.recycleViewTongSP);
        recyclerViewSanPhambanchay=(RecyclerView) getView().findViewById(R.id.recycleViewSPBanChay);

        arrayListLoaisp= new ArrayList<>();
        listHelp= new ArrayList<>();
        loaispAdapter=new LoaispAdapter(arrayListLoaisp,context);
        recyclerViewDanhMuc.setHasFixedSize(true);
        recyclerViewDanhMuc.setAdapter(loaispAdapter);

        arrayListSanPhamNew=new ArrayList<>();
        arrayListsChitietSPNew=new ArrayList<>();
        spNewAdapter=new SPNewAdapter(context,arrayListSanPhamNew,arrayListsChitietSPNew);
        recyclerViewSanPhamMoi.setHasFixedSize(true);
        recyclerViewSanPhamMoi.setLayoutManager(new GridLayoutManager(context,2));
        recyclerViewSanPhamMoi.setAdapter(spNewAdapter);

        arrayListLoaisp= new ArrayList<>();
        listHelp= new ArrayList<>();
        loaispAdapter=new LoaispAdapter(arrayListLoaisp,context);
        recyclerViewDanhMuc.setHasFixedSize(true);
        recyclerViewDanhMuc.setAdapter(loaispAdapter);


        arrayListTongSanPham= new ArrayList<>();
        arrayListsChitietTongSP=new ArrayList<>();
        tongSPAdapter= new TongSPAdapter(context,arrayListTongSanPham,arrayListsChitietTongSP);
        recyclerViewSanPham.setHasFixedSize(true);
        recyclerViewSanPham.setLayoutManager(new GridLayoutManager(context,2));
        recyclerViewSanPham.setAdapter(tongSPAdapter);
    }


}