package com.example.appbanhangnew.adapter.User;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DanhmucAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListSanPham ;
    ArrayList<ChitietSPs> arrayListsChitietSP;
    public static String giaSP;
    //int gianhonhat;

    public DanhmucAdapter(Context context, ArrayList<SanPham> arrayListSanPham, ArrayList<ChitietSPs> arrayListsChitietSP) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
        this.arrayListsChitietSP = arrayListsChitietSP;
        //this.gianhonhat = gianhonhat;
    }

    @Override
    public int getCount() {
        return arrayListSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtThoiTrang,txtGiaThoiTrang,txtMotaThoiTrang;
        ImageView imgThoiTrang;


    }
    public int getPriceOnArrChitietGia(ChitietSPs _chitietSP){
        int min=10000000;
        //int position=0;
        for(int i=0;i<_chitietSP.CountChitietSPs();i++){
            if(min>_chitietSP.getChitiet(i).getGia()&&_chitietSP.getChitiet(i).getGia()>0){
                min=_chitietSP.getChitiet(i).getGia();
                //position=i;
            }
        }
        return min;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if(viewHolder==null){
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.danhmuc_sp,null);
            viewHolder.txtThoiTrang=(TextView) convertView.findViewById(R.id.txtTenSP);
            viewHolder.txtGiaThoiTrang=(TextView) convertView.findViewById(R.id.txtGiaSP);
            viewHolder.txtMotaThoiTrang=(TextView)convertView.findViewById(R.id.txtMotaSP);
            viewHolder.imgThoiTrang=(ImageView)convertView.findViewById(R.id.imgSP);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        SanPham sanPham= (SanPham) getItem(position);
        //Log.d("Check",sanPham.getGia()+"");
        viewHolder.txtThoiTrang.setText(sanPham.getTensp());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        if(arrayListsChitietSP.size()>position){
            viewHolder.txtGiaThoiTrang.setText("Giá: "+ decimalFormat.format(getPriceOnArrChitietGia(arrayListsChitietSP.get(position)))+" Đ");
        }
        //giaSP= (String) viewHolder.txtGiaThoiTrang.getText();

        //Toast.makeText(context,arrayListsChitietSP.get(position).getChitiet(0).getIdChiTietSP()+"",Toast.LENGTH_SHORT).show();
        viewHolder.txtMotaThoiTrang.setMaxLines(2);
        viewHolder.txtMotaThoiTrang.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaThoiTrang.setText(sanPham.getMota());
        Picasso.get().load(sanPham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgThoiTrang);

        return convertView;
    }
}
