package com.example.appbanhangnew.model;

public class User {
    int iduser;
    String Sodienthoai;
    String password;
    String Ten;
    String ngaysinh;
    String Diachi;
    String Email;
    String image;

    public User(int iduser, String sodienthoai, String password
            , String ten, String ngaysinh, String diachi, String email, String image) {
        this.iduser = iduser;
        Sodienthoai = sodienthoai;
        this.password = password;
        Ten = ten;
        this.ngaysinh = ngaysinh;
        Diachi = diachi;
        Email = email;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSodienthoai(String sodienthoai) {
        Sodienthoai = sodienthoai;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getIduser() {
        return iduser;
    }

    public String getDiachi() {
        return Diachi;
    }

    public String getEmail() {
        return Email;
    }

    public String getImage() {
        return image;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getPassword() {
        return password;
    }

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public String getTen() {
        return Ten;
    }
}
