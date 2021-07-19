package com.example.appbanhangnew.adapter.Admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.XemSanPham.PopChiTietSPActivity;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ButtonAdapterAdmin extends RecyclerView.Adapter<ButtonAdapterAdmin.ItemHolder>{
    Context context;
    SanPham sanPham;
    ChitietSPs chitietSPs;
    ArrayList<Button> buttonArrayList= new ArrayList<>();
    public static ChitietSP chitietSPonItemClick;

    public ButtonAdapterAdmin(Context context, SanPham sanPham, ChitietSPs chitietSPs) {
        this.context = context;
        this.sanPham = sanPham;
        this.chitietSPs = chitietSPs;
    }
    public static ChitietSP getDataItemCLick(){
        return chitietSPonItemClick;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public Button button_chitiet;
        public ItemHolder(View itemView){
            super(itemView);
            Log.d("Nhi" ,"1");
            button_chitiet=itemView.findViewById(R.id.btn_ChonChiTiet);
            button_chitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,getAdapterPosition()+"",Toast.LENGTH_SHORT).show();
                    chitietSPonItemClick=chitietSPs.getChitiet(getAdapterPosition());
                    Picasso.get().load(chitietSPs.getChitiet(getAdapterPosition()).getHinhanhChiTietsp())
                            .placeholder(R.drawable.noimage)
                            .error(R.drawable.error)
                            .into(PopChiTietSPActivity.imgeChiTietSP);
                    for(int i=0;i<buttonArrayList.size();i++){
                        buttonArrayList.get(i).setBackgroundResource(R.drawable.khung_khongchon);
                    }
                    DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                    PopChiTietSPActivity.txtGiaChitiet.setText("Giá: "+ decimalFormat.format(chitietSPs.getChitiet(getAdapterPosition()).getGia())+" Đ");
                    button_chitiet.setBackgroundResource(R.drawable.khung_button);
                    buttonArrayList.add(button_chitiet);
                }
            });


        }


    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.button_chitiet,null);
        ItemHolder itemHolder= new ItemHolder(v);
        Log.d("Nhi" ,"2");
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        chitietSPonItemClick=null;
        ChitietSP chitiet=chitietSPs.getChitiet(position);
        holder.button_chitiet.setText(chitiet.getTenChiTietsp());
        //Log.d("Nhi" ,"3");

    }


    @Override
    public int getItemCount() {
        if(chitietSPs==null){
            Log.d("Nhi" ,"4");
            return 0;
        }else {
            Log.d("Nhi" ,"4");
            return chitietSPs.CountChitietSPs();

        }
    }
}
