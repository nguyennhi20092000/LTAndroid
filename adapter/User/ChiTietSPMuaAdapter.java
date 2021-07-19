package com.example.appbanhangnew.adapter.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.DonHang.DanhGiaActivity;
import com.example.appbanhangnew.controller.User.DanhGiaConTroller;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSPMuaAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChiTietDonHang> arrayListDonhang;


    public ChiTietSPMuaAdapter(Context context, ArrayList<ChiTietDonHang> arrayListDonhang) {
        this.context = context;
        this.arrayListDonhang = arrayListDonhang;
    }

    @Override
    public int getCount() {
        return arrayListDonhang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListDonhang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {
        ImageView imageViewChoXacNhan;
        TextView txttensp,txtsoluong,txtdongia,txtthanhtien;
        Button btnDanhGia;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_spdamua, null);
            viewHolder.txttensp = (TextView) convertView.findViewById(R.id.txtloaisp);
            viewHolder.txtdongia = (TextView) convertView.findViewById(R.id.txtDongia);
            viewHolder.txtsoluong = (TextView) convertView.findViewById(R.id.txtsoluong);
            viewHolder.txtthanhtien=(TextView) convertView.findViewById(R.id.txtthanhtien);
            viewHolder.imageViewChoXacNhan=(ImageView) convertView.findViewById(R.id.imgAnhChoXacNhan);
            viewHolder.btnDanhGia=(Button) convertView.findViewById(R.id.btnDanhGia);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChiTietDonHang chiTietDonHang=(ChiTietDonHang) getItem(position);



        //Log.d("Check",sanPham.getGia()+"");
        viewHolder.txttensp.setText("["+chiTietDonHang.getTenchitietsp()+"] "+ chiTietDonHang.getTensp() );
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtthanhtien.setText("Thành tiền: " + decimalFormat.format(chiTietDonHang.getGia()*chiTietDonHang.getSoluong()) + " Đ");
        viewHolder.txtdongia.setText("Giá: "+decimalFormat.format(chiTietDonHang.getGia())+" Đ");
        viewHolder.txtsoluong.setText("x"+ chiTietDonHang.getSoluong());
        DanhGiaConTroller danhGiaConTroller= new DanhGiaConTroller(context);

        Picasso.get().load(chiTietDonHang.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageViewChoXacNhan);
        viewHolder.btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DanhGia1111111",chiTietDonHang.getIddonhang()+" "+chiTietDonHang.getIdsp()+"");
                danhGiaConTroller.DanhGia(chiTietDonHang, chiTietDonHang.getIddonhang(), chiTietDonHang.getIdsp());
                Activity activity= (Activity)context;
                activity.finish();
                /*Intent intent= new Intent(context, DanhGiaActivity.class);
                intent.putExtra("idsp", chiTietDonHang.getIdsp());
                intent.putExtra("gia",chiTietDonHang.getGia());
                intent.putExtra("hinhanhsp",chiTietDonHang.getHinhanhsp());
                intent.putExtra("iddonhang",chiTietDonHang.getIddonhang());
                intent.putExtra("tensp","["+chiTietDonHang.getTenchitietsp()+"] "+ chiTietDonHang.getTensp());
                context.startActivity(intent);*/
            }
        });


        return convertView;
    }

}
