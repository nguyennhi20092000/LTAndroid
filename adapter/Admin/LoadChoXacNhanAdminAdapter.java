package com.example.appbanhangnew.adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.model.DonHang.DonHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoadChoXacNhanAdminAdapter extends BaseAdapter {
    Context context;
    ArrayList<DonHang> arrayListDonhang;


    public LoadChoXacNhanAdminAdapter(Context context, ArrayList<DonHang> arrayListDonhang) {
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

        TextView txtmacode, txtngaythang, txtsodienthoai,txtdiachi, txttongtien;
        ImageView imageViewAnh;



    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_choxacnhan, null);
            viewHolder.txtngaythang = (TextView) convertView.findViewById(R.id.txtngaythang);
            viewHolder.txtmacode=(TextView) convertView.findViewById(R.id.txtmaDon);
            viewHolder.txtsodienthoai=(TextView) convertView.findViewById(R.id.txtSodienthoai);
            viewHolder.txtdiachi=(TextView) convertView.findViewById(R.id.txtdiachi);
            viewHolder.txttongtien=(TextView) convertView.findViewById(R.id.txttongtien);
            viewHolder.imageViewAnh=(ImageView) convertView.findViewById(R.id.imgAnh);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DonHang donHang = (DonHang) getItem(position);
        //int thanhtien=0;
        //for(int i=0;i<donHang.getArrayListchitietdonhang().size();i++){
        //thanhtien=thanhtien+donHang.getArrayListchitietdonhang().get(i).getGia()*donHang.getArrayListchitietdonhang().get(i).getSoluong();
        //}

        //Log.d("Check",sanPham.getGia()+"");
        viewHolder.txtngaythang.setText( donHang.getNgaythang() );
        viewHolder.txtmacode.setText("MVĐ"+donHang.getIddonhang());
        viewHolder.txtdiachi.setText(donHang.getDiachi());
        viewHolder.txtsodienthoai.setText(donHang.getSodienthoai());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txttongtien.setText("Giá: " + decimalFormat.format(donHang.getTongtien()) + " Đ");
        if(donHang.getArrayListchitietdonhang().size()>0){
            Picasso.get().load(donHang.getArrayListchitietdonhang().get(0).getHinhanhsp())
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.error)
                    .into(viewHolder.imageViewAnh);
        }

        //Log.d("ListDonHang",donHang.getArrayListchitietdonhang().size()+"");
        //viewHolder.listViewChiTietChoXN.setAdapter(dongChiTietChoXacNhanAdapter);


        return convertView;
    }
}
