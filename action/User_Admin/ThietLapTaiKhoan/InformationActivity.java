package com.example.appbanhangnew.action.User_Admin.ThietLapTaiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User_Admin.Account.AccountActivity;
import com.example.appbanhangnew.action.User_Admin.Account.DangKiActivity;
import com.example.appbanhangnew.controller.User_Admin.InformationController;
import com.example.appbanhangnew.controller.User_Admin.UserController;
import com.example.appbanhangnew.model.User;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class InformationActivity extends AppCompatActivity {
    private static final int Request_Code_Image = 123;
    private static final int RED_EXTERNAL = 2;
    String realpath = "";
    TextInputLayout TILhoten;
    TextInputLayout TILngaysinh;
    TextInputLayout TILDiachi;
    TextInputLayout TILsodienthoai;
    TextInputLayout TILEmail;

    TextInputEditText txthoten;
    TextView txtNgaySinh;
    TextInputEditText txtDiachi;
    TextInputEditText txtsodienthoai;
    TextInputEditText txtEmail;

    Button btnDate;
    Button btnUpload;
    Button btnSave;
    Button btnUpdate;

    ImageView imgAvarta;

    String hoten;
    String ngaysinh;
    String Diachi;
    String sodienthoai;
    String email;
    String password;
    SharedPreferences sharedPreferences;
    UserController userController;

    Toolbar toolbaruser;
    ActionMenuView actionMenu_DangXuat;
    //SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InformationController informationController;
    int iduser;
    boolean setimage;
    String linkgetImage;
    //UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Anhxa();
        AnhXa_User();
        ActionBar();
    }

    public void ActionBar(){
        setSupportActionBar(toolbaruser);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbaruser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dangxuat,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_dangxuat:
                //Toast.makeText(getApplicationContext(),"Đăng xuất",Toast.LENGTH_LONG).show();
                editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent= new Intent(InformationActivity.this, AccountActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AnhXa_User() {
        Toast.makeText(getApplicationContext(),sharedPreferences.getInt("iduser", 0)+" ",Toast.LENGTH_LONG).show();
        User user = new User(sharedPreferences.getInt("iduser", 0), sharedPreferences.getString("sodienthoai", ""), sharedPreferences.getString("password", ""),
                sharedPreferences.getString("ten", ""), sharedPreferences.getString("ngaysinh", ""), sharedPreferences.getString("diachi", ""),
                sharedPreferences.getString("email", ""), sharedPreferences.getString("image", ""));
        password=sharedPreferences.getString("password",null);
        iduser=sharedPreferences.getInt("iduser",0);
        linkgetImage=user.getImage();
        if(user.getIduser()!=0){
            txthoten.setText(user.getTen());
            txtsodienthoai.setText(user.getSodienthoai());
            txtNgaySinh.setText(user.getNgaysinh());
            txtEmail.setText(user.getEmail());
            txtDiachi.setText(user.getDiachi());
            Picasso.get().load(Server.duongdanHTTP+user.getImage())
                    .placeholder(R.drawable.noimage).error(R.drawable.error)
                    .into(imgAvarta);
            //btnSave.setEnabled(false);
        }


    }


    public void Anhxa() {
        TILhoten = (TextInputLayout) findViewById(R.id.TILhoten);
        TILngaysinh = (TextInputLayout) findViewById(R.id.TILNgaysinh);
        TILDiachi = (TextInputLayout) findViewById(R.id.TILDiachi);
        TILsodienthoai = (TextInputLayout) findViewById(R.id.TILSodienthoai);
        TILEmail = (TextInputLayout) findViewById(R.id.TILEmail);

        txthoten = (TextInputEditText) findViewById(R.id.txthoten);
        txtNgaySinh = (TextView) findViewById(R.id.txtNgaysinh);
        txtDiachi = (TextInputEditText) findViewById(R.id.txtdiachi);
        txtsodienthoai = (TextInputEditText) findViewById(R.id.txtSodienthoai);
        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        txtsodienthoai.setText(DangKiActivity._sodienthoai);

        btnDate = (Button) findViewById(R.id.btnDate);
        btnUpload = (Button) findViewById(R.id.btnuploadimage);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        toolbaruser=(Toolbar) findViewById(R.id.toolbaruser);
        //actionMenu_DangXuat=(ActionMenuView) findViewById(R.id.actionMenu_dangxuat);
        userController=new UserController();
        setimage=false;
        imgAvarta = (ImageView) findViewById(R.id.imgAvarta);
        sharedPreferences = this.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        userController=new UserController();
        informationController= new InformationController();
        userController.setTILs(txthoten,TILhoten,txtDiachi,TILDiachi,txtEmail,TILEmail,txtNgaySinh,TILngaysinh,txtsodienthoai,TILsodienthoai);



    }
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btnDate:
                userController.Chonngay(txtNgaySinh,this);
                break;
            case R.id.btnEdit:
                informationController.EditInformation(setimage,linkgetImage,iduser,txthoten.getText().toString(),txtNgaySinh.getText().toString(),txtEmail.getText().toString()
                ,txtDiachi.getText().toString(),txtsodienthoai.getText().toString(),password,InformationActivity.this,realpath);
                intent = new Intent(InformationActivity.this, ThietLapTaiKhoanActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnuploadimage:
                intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), Request_Code_Image);
                setimage=true;
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Request_Code_Image && RESULT_OK == resultCode && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            //Toast.makeText(getApplicationContext(),imageUri.toString(),Toast.LENGTH_SHORT).show();
            realpath = userController.getRealPathFromURI(imageUri,this);
            //Toast.makeText(getApplicationContext(),imageUri.toString(),Toast.LENGTH_SHORT).show();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap photo = BitmapFactory.decodeStream(inputStream);
                imgAvarta.setImageBitmap(photo);
                //image=photo;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}