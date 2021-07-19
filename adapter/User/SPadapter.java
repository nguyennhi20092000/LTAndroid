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
import com.example.appbanhangnew.action.User.SanPham.ChitietSanPhamActivity;

import com.example.appbanhangnew.model.SanPham.ChitietSP;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SPadapter extends RecyclerView.Adapter<SPadapter.ItemHolder> {

    Context context;
    ArrayList<SanPham> arrSp;
    ArrayList<ArrayList<ChitietSP>> arrayListsChitietSP;

    public SPadapter(Context context, ArrayList<SanPham> arrSp, ArrayList<ArrayList<ChitietSP>> arrayListsChitietSP) {
        this.context = context;
        this.arrSp = arrSp;
        this.arrayListsChitietSP = arrayListsChitietSP;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsp;
        TextView txttensp,txtgiasp;
        public ItemHolder(View itemView){
            super(itemView);
            imghinhsp=itemView.findViewById(R.id.imvSP);
            txtgiasp=itemView.findViewById((R.id.txtGiasp));
            txttensp=itemView.findViewById(R.id.txtTensp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, ChitietSanPhamActivity.class);
                    intent.putExtra("thongtinsp",arrSp.get(getAdapterPosition()));
                    intent.putExtra("Gianhonhat",txtgiasp.getText());
                    intent.putExtra("ChitietSanPham",txtgiasp.getText());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.Show_Tost_Short(context,arrSp.get(getAdapterPosition()).getTensp());
                    context.startActivity(intent);
                }
            });

        }


    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham,null);
        ItemHolder itemHolder= new ItemHolder(v);
        return itemHolder;
    }
    public int getPriceOnArrChitietGia(ArrayList<ChitietSP> _chitietSP){
        int min=_chitietSP.get(0).getGia();
        //int position=0;
        for(int i=0;i<_chitietSP.size();i++){
            if(min>_chitietSP.get(i).getGia()){
                min=_chitietSP.get(i).getGia();
                //position=i;
            }
        }
        return min;
    }
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham=arrSp.get(position);
        holder.txttensp.setText(sanPham.getTensp());
        //tạo format 3 số 0 là phẩy.
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+ decimalFormat.format(getPriceOnArrChitietGia(arrayListsChitietSP.get(position)))+" Đ");
        Picasso.get().load(sanPham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhsp);

    }


    @Override
    public int getItemCount() {
        return arrSp.size();
    }
}
