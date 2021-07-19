package com.example.appbanhangnew.action.User.Search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.User.SPSearchAdapter;
import com.example.appbanhangnew.controller.User.SearchController;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.Server;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<SanPham> arrayListSPSearch;
    SPSearchAdapter spSearchAdapter;
    ArrayList<ChitietSP> chitietSPSearch;
    ArrayList<ChitietSPs> arrayListsChiTietSPSearch;
    ChitietSPs _chitietsSPSearch;
    Toolbar toolbar;
    RecyclerView recyclerViewSanPhamSearch;
    TextView txtThongbao;
    SearchController searchController;
    String Searchtext="";
    SearchView txtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnhXa();
        ActionBar();
        txtSearch.setQuery(Searchtext,true);
        searchController.GetDataSPSearch(Searchtext,arrayListSPSearch,spSearchAdapter,chitietSPSearch,arrayListsChiTietSPSearch,_chitietsSPSearch,SearchActivity.this);
        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtThongbao.setText("Sản phẩm của từ khóa: "+query);
                arrayListSPSearch=new ArrayList<>();
                arrayListsChiTietSPSearch=new ArrayList<>();
                spSearchAdapter=new SPSearchAdapter(SearchActivity.this,arrayListSPSearch,arrayListsChiTietSPSearch);
                recyclerViewSanPhamSearch.setAdapter(spSearchAdapter);
                searchController.GetDataSPSearch(query,arrayListSPSearch,spSearchAdapter,chitietSPSearch,arrayListsChiTietSPSearch,_chitietsSPSearch,SearchActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    public void AnhXa(){
        toolbar=(Toolbar) findViewById(R.id.toolbarsearch);

        txtThongbao=(TextView) findViewById(R.id.txtThongBao);
        txtSearch=(SearchView) findViewById(R.id.txtSearch);

        Searchtext=getIntent().getStringExtra("SearchText");
        txtThongbao.setText("Sản phẩm của từ khóa: "+Searchtext);
        SetAdapter();
        searchController=new SearchController();
        //
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

    public void SetAdapter(){
        recyclerViewSanPhamSearch=(RecyclerView) findViewById(R.id.recycleViewSPSearch);
        arrayListsChiTietSPSearch=new ArrayList<>();
        chitietSPSearch=new ArrayList<>();
        arrayListSPSearch=new ArrayList<>();
        spSearchAdapter=new SPSearchAdapter(SearchActivity.this,arrayListSPSearch,arrayListsChiTietSPSearch);

        recyclerViewSanPhamSearch.setHasFixedSize(true);
        recyclerViewSanPhamSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
        recyclerViewSanPhamSearch.setAdapter(spSearchAdapter);
    }
}