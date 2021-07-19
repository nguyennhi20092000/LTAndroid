package com.example.appbanhangnew.model.SanPham;

import java.io.Serializable;

public class Loaisp implements Serializable {
    int _idloaisp;
    String _tenloaisp;
    String _hinhanhloaisp;



    public  Loaisp(int idloaisp, String tenloaisp, String hinhanhloaisp){
        _idloaisp=idloaisp;
        _tenloaisp=tenloaisp;
        _hinhanhloaisp=hinhanhloaisp;
    }
    public int get_idloaisp(){

        return _idloaisp;
    }
    public  void  set_idloaisp(int idloaisp){

        _idloaisp=idloaisp;
    }
    public String get_tenloaisp(){

        return _tenloaisp;
    }
    public void  set_tenloaisp(String tenloaisp)
    {
        _tenloaisp=tenloaisp;
    }
    public String get_hinhanhloaisp(){

        return _hinhanhloaisp;
    }
    public void  set_hinhanhloaisp(String hinhanhloaisp){

        _hinhanhloaisp=hinhanhloaisp;
    }
}
