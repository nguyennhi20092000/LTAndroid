package com.example.appbanhangnew.action.Admin.DonHang.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.DonHang.ChiTietDonHangAdMinActivity;
import com.example.appbanhangnew.adapter.Admin.LoadChoXacNhanAdminAdapter;
import com.example.appbanhangnew.controller.Admin.LoadDonhangAdminController;
import com.example.appbanhangnew.model.DonHang.DonHang;

import java.util.ArrayList;


public class DaGiaoFragment extends Fragment {

    ListView listViewDongdonhang;
    public static ArrayList<DonHang> donHangs;
    LoadChoXacNhanAdminAdapter DagiaoAdapter;
    LoadDonhangAdminController DaGiaoController;
    int iduser;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_da_giao2, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        DaGiaoController.getDonhang(donHangs,DagiaoAdapter,2);
        listViewDongdonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietDonHangAdMinActivity.class);
                intent.putExtra("chitietdonhang",position);
                intent.putExtra("status",1);
                startActivity(intent);
            }
        });
        Log.d("ListDonHangController",donHangs.size()+"");

    }

    public void AnhXa(){
        listViewDongdonhang= (ListView) getView().findViewById(R.id.listDanhSachChoXacNhan);
        donHangs=new ArrayList<>();
        DagiaoAdapter=new LoadChoXacNhanAdminAdapter(getContext(),donHangs);
        listViewDongdonhang.setAdapter(DagiaoAdapter);
        DaGiaoController=new LoadDonhangAdminController(getContext());
        sharedPreferences = getContext().getSharedPreferences("USER_FILE", getContext().MODE_PRIVATE);
        iduser=sharedPreferences.getInt("iduser", 0);

    }
}