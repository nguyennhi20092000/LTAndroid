package com.example.appbanhangnew.adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.model.QuanHuyen;

import java.util.ArrayList;
import java.util.Queue;

public class VanChuyenAdapter extends BaseAdapter {
    ArrayList<QuanHuyen> listVanchuyen;
    Context context;

    public VanChuyenAdapter(ArrayList<QuanHuyen> listVanchuyen, Context context) {
        this.listVanchuyen = listVanchuyen;
        this.context=context;
    }

    @Override
    public int getCount() {
        return listVanchuyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listVanchuyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtquanhuyen;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return null;
        ViewHolder viewHolder= null;
        if(viewHolder==null){
            //khi chưa có view, thực hiện ánh xạ và truyền hẳn vào viewhoder lưu lại
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.dong_quan_huyen,null);

            viewHolder.txtquanhuyen= (TextView) convertView.findViewById(R.id.textviewquanhuyen);

            //Truyền view hiện tại vào tag. gán giá trị vào viewhoder
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }



        viewHolder.txtquanhuyen.setText(listVanchuyen.get(position).getName());

        return convertView;
    }
}
