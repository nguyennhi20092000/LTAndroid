package com.example.appbanhangnew.action.User.DonHang.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.ChiTietLoadDonHangActivity;
import com.example.appbanhangnew.adapter.User.LoadDonHangAdapter;
import com.example.appbanhangnew.controller.User.LoadDonHangController;
import com.example.appbanhangnew.model.DonHang.DonHang;

import java.util.ArrayList;


public class DaXacNhanFragment extends Fragment {

    ListView listViewDongdonhang;
    public static ArrayList<DonHang> donHangs;
    LoadDonHangAdapter DaXacNhanDonHangAdapter;
    LoadDonHangController DaXacNhanDonHangController;
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
        return inflater.inflate(R.layout.fragment_da_xac_nhan, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
       DaXacNhanDonHangController.getDonhang(iduser,donHangs,DaXacNhanDonHangAdapter,2);
        listViewDongdonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietLoadDonHangActivity.class);
                intent.putExtra("chitietdonhang",position);
                intent.putExtra("trangthai",2);
                startActivity(intent);
            }
        });
    }
    public void AnhXa(){
        listViewDongdonhang= (ListView) getView().findViewById(R.id.listDanhSachChoXacNhan);
        donHangs=new ArrayList<>();
        DaXacNhanDonHangAdapter=new LoadDonHangAdapter(getContext(),donHangs);
        listViewDongdonhang.setAdapter(DaXacNhanDonHangAdapter);
        DaXacNhanDonHangController=new LoadDonHangController(getContext());
        sharedPreferences = getContext().getSharedPreferences("USER_FILE", getContext().MODE_PRIVATE);
        iduser=sharedPreferences.getInt("iduser", 0);

    }
}