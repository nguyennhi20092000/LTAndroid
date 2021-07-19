package com.example.appbanhangnew.model.SanPham;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class ChitietSPs implements Serializable {
    ArrayList<ChitietSP> chitietSPs=new ArrayList<>();

    public ChitietSPs(ArrayList<ChitietSP> chitietSPs) {
        this.chitietSPs= (ArrayList<ChitietSP>) chitietSPs.clone();
        for (int i=0;i<chitietSPs.size();i++)
        {
            Log.d("SEt",this.chitietSPs.get(i).getIdChiTietSP()+"");
        }
    }



    public ArrayList<ChitietSP> getChitietSPs() {
        return chitietSPs;
    }


    public void setChitietSPs(ArrayList<ChitietSP> chitietSPs) {
        this.chitietSPs = chitietSPs;
    }
    public void addChitiets(ChitietSP chitietSP){
        this.chitietSPs.add(chitietSP);
    }
    public ChitietSP getChitiet(int position){
        if(chitietSPs==null){
            return null;
        }else {
            return chitietSPs.get(position);
        }



    }
    public int CountChitietSPs(){
        return chitietSPs.size();
    }
    public void Clear(){
        chitietSPs.clear();
    }
}
