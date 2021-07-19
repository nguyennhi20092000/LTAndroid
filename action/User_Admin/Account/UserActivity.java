package com.example.appbanhangnew.action.User_Admin.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Singleton.InitializedSingleton;
import com.example.appbanhangnew.controller.User_Admin.UserController;
import com.example.appbanhangnew.model.User;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class UserActivity extends AppCompatActivity {

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
    SharedPreferences sharedPreferences;
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Anhxa();
        InitializedSingleton temp = InitializedSingleton.getInstance();
        AnhXa_User(temp);


    }

    public void AnhXa_User(InitializedSingleton temp) {
        User user = temp.getUser(sharedPreferences);
        if(user.getIduser()!=0){
            txthoten.setText(user.getTen());
            txtsodienthoai.setText(user.getSodienthoai());
            txtNgaySinh.setText(user.getNgaysinh());
            txtEmail.setText(user.getEmail());
            txtDiachi.setText(user.getDiachi());
            Picasso.get().load(Server.duongdanHTTP+user.getImage())
                    .placeholder(R.drawable.noimage).error(R.drawable.error)
                    .into(imgAvarta);
            btnSave.setEnabled(false);
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
        btnSave = (Button) findViewById(R.id.btnSave);

        imgAvarta = (ImageView) findViewById(R.id.imgAvarta);
        sharedPreferences = this.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        userController=new UserController();

        userController.setTILs(txthoten,TILhoten,txtDiachi,TILDiachi,txtEmail,TILEmail,txtNgaySinh,TILngaysinh,txtsodienthoai,TILsodienthoai);



    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDate:
                userController.Chonngay(txtNgaySinh,this);
                break;
            case R.id.btnuploadimage:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), Request_Code_Image);

                break;
            case R.id.btnSave:
                hoten = txthoten.getText().toString().trim();
                ngaysinh = txtNgaySinh.getText().toString().trim();
                //Toast.makeText(getApplicationContext(),ngaysinh,Toast.LENGTH_SHORT).show();
                Diachi = txtDiachi.getText().toString().trim();
                sodienthoai = txtsodienthoai.getText().toString().trim();
                email = txtEmail.getText().toString().trim();
                int iduser=getIntent().getIntExtra("ID_USER",0);
                userController.SaveInformation(iduser,hoten, ngaysinh, email, Diachi, sodienthoai, UserActivity.this, realpath);

            case R.id.btnUpdate:
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