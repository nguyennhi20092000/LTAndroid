package com.example.appbanhangnew.adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.GioHang.GioHangActivity;
import com.example.appbanhangnew.model.DonHang.ChiTietDonHang;
import com.example.appbanhangnew.model.DonHang.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.appbanhangnew.action.User.GioHang.GioHangActivity.TongTien;

public class DonHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChiTietDonHang> arrayListGioHang;


    public DonHangAdapter(Context context, ArrayList<ChiTietDonHang> arrayListGioHang) {
        this.context = context;
        this.arrayListGioHang = arrayListGioHang;
    }

    @Override
    public int getCount() {
        return arrayListGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder {
        public TextView txtGiohang, txtGiaGiohang, txtsoluong, txtTongTienSP;
        public ImageView imgGiohang;



    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_chi_tiet_donhang, null);
            viewHolder.txtGiaGiohang = (TextView) convertView.findViewById(R.id.txtGiaSP);
            viewHolder.txtGiohang = (TextView) convertView.findViewById(R.id.txttenChitiet);
            viewHolder.txtsoluong = (TextView) convertView.findViewById(R.id.txtchitietgiohang);
            viewHolder.imgGiohang = (ImageView) convertView.findViewById(R.id.imgChitiet);

            viewHolder.txtTongTienSP = (TextView) convertView.findViewById(R.id.txtTongtienSP);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChiTietDonHang chitietGioHang = (ChiTietDonHang) getItem(position);
        //Log.d("Check",sanPham.getGia()+"");
        viewHolder.txtGiohang.setText("[" + chitietGioHang.getTenchitietsp() + "]" + " " + chitietGioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGiohang.setText("Giá: " + decimalFormat.format(chitietGioHang.getGia()) + " Đ");
        int tongtien = chitietGioHang.getGia() * chitietGioHang.getSoluong();
        viewHolder.txtTongTienSP.setText("Thành tiền: " + decimalFormat.format(tongtien) + " Đ");


        viewHolder.txtsoluong.setText(chitietGioHang.getSoluong() + "");
        Picasso.get().load(chitietGioHang.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgGiohang);


        return convertView;
    }


}
