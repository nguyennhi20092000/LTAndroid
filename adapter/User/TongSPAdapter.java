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

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.example.appbanhangnew.until.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TongSPAdapter extends RecyclerView.Adapter<TongSPAdapter.ItemHolder> {

    Context context;
    ArrayList<SanPham> arrSp;
    ArrayList<ChitietSPs> arrayListsChitietSP;

    public TongSPAdapter(Context context, ArrayList<SanPham> arrSp, ArrayList<ChitietSPs> arrayListsChitietSP) {
        this.context = context;
        this.arrSp = arrSp;
        this.arrayListsChitietSP = arrayListsChitietSP;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhsp;
        TextView txttensp, txtgiasp;

        public ItemHolder(View itemView) {
            super(itemView);
            imghinhsp = itemView.findViewById(R.id.imvSP);
            txtgiasp = itemView.findViewById((R.id.txtGiasp));
            txttensp = itemView.findViewById(R.id.txtTensp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChitietSanPhamActivity.class);
                    intent.putExtra("thongtinsp",arrSp.get(getAdapterPosition()));
                    intent.putExtra("ThongtinChiTietSP",arrayListsChitietSP.get(getAdapterPosition()));
                    intent.putExtra("Gianhonhat",txtgiasp.getText());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.Show_Tost_Short(context, arrSp.get(getAdapterPosition()).getTensp());
                    context.startActivity(intent);
                }
            });

        }


    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham_new, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    public int getPriceOnArrChitietGia(ChitietSPs _chitietSP) {
        int min = 10000000;
        //int position=0;
        for (int i = 0; i < _chitietSP.CountChitietSPs(); i++) {
            if (min > _chitietSP.getChitiet(i).getGia() && _chitietSP.getChitiet(i).getGia() > 0) {
                min = _chitietSP.getChitiet(i).getGia();
                //position=i;
            }
        }
        return min;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham = arrSp.get(position);
        //Log.d("Check",sanPham.getGia()+"");
        holder.txttensp.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (arrayListsChitietSP.size() > position) {
            holder.txtgiasp.setText("Giá: " + decimalFormat.format(getPriceOnArrChitietGia(arrayListsChitietSP.get(position))) + " Đ");
        }

        //Toast.makeText(context,arrayListsChitietSP.get(position).getChitiet(0).getIdChiTietSP()+"",Toast.LENGTH_SHORT).show();

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

