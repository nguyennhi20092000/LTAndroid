package com.example.appbanhangnew.action.Admin.XemSanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.R;

import com.example.appbanhangnew.adapter.Admin.ButtonAdapterAdmin;
import com.example.appbanhangnew.adapter.User.ButtonAdapter;

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class PopChiTietSPActivity extends AppCompatActivity {

    SanPham sanPham;
    //ChitietSPs chitietSPs;
    ButtonAdapterAdmin buttonAdapter;
    RecyclerView recyclerViewButton;
    ChitietSPs chitietSPs;
    public static ImageView imgeChiTietSP;
    public static TextView txtGiaChitiet;
    int Soluong=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_chi_tiet_s_p);
        final DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        AnhXa();


    }
    public  void AnhXa(){
        recyclerViewButton=(RecyclerView) findViewById(R.id.recycleViewListChiTiet);
        imgeChiTietSP=(ImageView) findViewById(R.id.imgChiTietImage);
        txtGiaChitiet=(TextView)findViewById(R.id.txtGiaChiTietSanPham);

        //chitietSPArrayList= new ArrayList<>();
        getData();
        Picasso.get().load(chitietSPs.getChitiet(0).getHinhanhChiTietsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgeChiTietSP);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txtGiaChitiet.setText("Giá: "+ decimalFormat.format(chitietSPs.getChitiet(0).getGia())+" Đ");
        buttonAdapter=new ButtonAdapterAdmin(getApplicationContext(),sanPham,chitietSPs);
        recyclerViewButton.setHasFixedSize(true);
        recyclerViewButton.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewButton.setAdapter(buttonAdapter);



    }
    public void getData(){
        sanPham=null;
        chitietSPs=null;
        //chitietSPArrayList= new ArrayList<>();
        //buttonAdapter.notifyDataSetChanged();
        chitietSPs= (ChitietSPs) getIntent().getSerializableExtra("ChitietSPs");
        sanPham=(SanPham) getIntent().getSerializableExtra("SanPham");

    }
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btnThemVaoGioHang:
                if(ButtonAdapter.getDataItemCLick()==null){
                    Toast.makeText(getApplicationContext(),"Bạn phải chọn loại hàng trước khi thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),ButtonAdapter.getDataItemCLick().getTenChiTietsp(),Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }
}