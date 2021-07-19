package com.example.appbanhangnew.adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;

import com.example.appbanhangnew.model.SanPham.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListhelpAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListLoaisp;
    Context context; // truyền vào màn hình cần vẽ

    public ListhelpAdapter(ArrayList<Loaisp> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    //gọi dữ liệu từ mảng ra.
    @Override
    public int getCount() {

        return arrayListLoaisp.size();
    }

    //gọi giá trị trong từng thuộc tính
    @Override
    public Object getItem(int position) {

        return arrayListLoaisp.get(position);
    }

    //gọi giá trị vt trong arraylist.
    @Override
    public long getItemId(int position) {

        return position;
    }

    // Khi ct run lần đầu, bắt những ánh xạ. gọi lần sau thì k cần gọi lại dữ liệu mà gán thẳng vào.
    public  class ViewHolder{
        TextView txtTenloaisp;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if(viewHolder==null){
            //khi chưa có view, thực hiện ánh xạ và truyền hẳn vào viewhoder lưu lại
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.dong_listview_loaisp,null);

            viewHolder.txtTenloaisp= (TextView) convertView.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp= (ImageView) convertView.findViewById(R.id.imageviewLoaisp);
            //Truyền view hiện tại vào tag. gán giá trị vào viewhoder
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //lấy giá trị ra dùng.

        Loaisp loaisp= (Loaisp) getItem(position);
        viewHolder.txtTenloaisp.setText(loaisp.get_tenloaisp());

        //chưa load hình sẽ chèn tấm hình noimage (placeholder), nếu hình lỗi, chèn hình error.
        //Nếu chèn dc dùng into. trả về view của mình
        Picasso.get().load(loaisp.get_hinhanhloaisp())
                .placeholder(R.drawable.noimage).error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return convertView;
    }

}
