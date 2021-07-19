package com.example.appbanhangnew.adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.model.SPBanChay;
import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SPBanChayAdapter extends BaseAdapter {
    ArrayList<SPBanChay> spBanChays;
    Context context; // truyền vào màn hình cần vẽ

    public SPBanChayAdapter(ArrayList<SPBanChay> spBanChays, Context context) {
        this.spBanChays = spBanChays;
        this.context = context;
    }

    //gọi dữ liệu từ mảng ra.
    @Override
    public int getCount() {

        return spBanChays.size();
    }

    //gọi giá trị trong từng thuộc tính
    @Override
    public Object getItem(int position) {

        return spBanChays.get(position);
    }

    //gọi giá trị vt trong arraylist.
    @Override
    public long getItemId(int position) {

        return position;
    }

    // Khi ct run lần đầu, bắt những ánh xạ. gọi lần sau thì k cần gọi lại dữ liệu mà gán thẳng vào.
    public  class ViewHolder{
        TextView txtTensp, txtSoluongban;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if(viewHolder==null){
            //khi chưa có view, thực hiện ánh xạ và truyền hẳn vào viewhoder lưu lại
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.dong_sp_ban_chay,null);

            viewHolder.txtTensp= (TextView) convertView.findViewById(R.id.txttenSanPham);
            viewHolder.imgloaisp= (ImageView) convertView.findViewById(R.id.imgChitiet);
            viewHolder.txtSoluongban=(TextView) convertView.findViewById(R.id.txtsoluongmua);
            //Truyền view hiện tại vào tag. gán giá trị vào viewhoder
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //lấy giá trị ra dùng.

       SPBanChay spBanChay= spBanChays.get(position);

        viewHolder.txtTensp.setText(spBanChay.getTensp());
        viewHolder.txtSoluongban.setText(spBanChay.getSoluongban()+"");

        //chưa load hình sẽ chèn tấm hình noimage (placeholder), nếu hình lỗi, chèn hình error.
        //Nếu chèn dc dùng into. trả về view của mình
        Picasso.get().load(spBanChay.getHinhanhsp())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return convertView;
    }

}
