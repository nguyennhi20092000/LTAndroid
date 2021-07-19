package com.example.appbanhangnew.adapter.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User_Admin.DanhmucSPMainActivity;

import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends RecyclerView.Adapter<LoaispAdapter.ItemHolder> {
    ArrayList<Loaisp> arrayListLoaisp;
    Context context; // truyền vào màn hình cần vẽ

    public LoaispAdapter(ArrayList<Loaisp> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_danhmuc,null);
        ItemHolder itemHolder= new ItemHolder(v);
        return itemHolder;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Loaisp LoaisanPham=arrayListLoaisp.get(position);
        holder.txtTenLoaiSP.setText(LoaisanPham.get_tenloaisp());
        //tạo format 3 số 0 là phẩy.
        Picasso.get().load(LoaisanPham.get_hinhanhloaisp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgHinhLoaiSP);
    }

    @Override
    public int getItemCount() {
        return arrayListLoaisp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhLoaiSP;
        TextView txtTenLoaiSP;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhLoaiSP=itemView.findViewById(R.id.imgDanhmuc);
            txtTenLoaiSP=itemView.findViewById(R.id.txtTenDanhmuc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, DanhmucSPMainActivity.class);
                    intent.putExtra("idloaisp",arrayListLoaisp.get(getAdapterPosition()).get_idloaisp());
                    intent.putExtra("Title",arrayListLoaisp.get(getAdapterPosition()).get_tenloaisp());
                    //Toast.makeText(context,"xxxxx "+arrayListLoaisp.get(getAdapterPosition()).get_idloaisp()+"",Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });

        }
    }
}
