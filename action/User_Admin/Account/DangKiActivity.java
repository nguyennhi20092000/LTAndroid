package com.example.appbanhangnew.action.User_Admin.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.User_Admin.DangKyController;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DangKiActivity extends AppCompatActivity {

    TextInputLayout TILsodienthoai;
    TextInputLayout TILpassword;
    TextInputLayout TILnhaplaipassword;
    public static int ID_USER;
    TextInputEditText txtsodienthoai;
    TextInputEditText txtpassword;
    TextInputEditText txtnhaplaipassword;
    Button btnDangki;
    public static String _sodienthoai;
    public static int _start=0;
    RadioGroup radioGroup;
    RadioButton radioButtonAdmin,radioButtonUser;
    String ulr=Server.duongdaninsertuser;
    DangKyController dangKyController;
    int id_per;
    int count_re;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        Anhxa();

        btnDangki=findViewById(R.id.btnDangKy);
        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sdt=txtsodienthoai.getText().toString().trim();
                String pass=txtpassword.getText().toString().trim();
                String nhaplaipw=txtnhaplaipassword.getText().toString().trim();
                if(radioButtonUser.isChecked()==false && radioButtonAdmin.isChecked()==false){
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn quyền",Toast.LENGTH_LONG).show();
                }else {
                    if(radioButtonAdmin.isChecked()==true){
                        id_per=1;
                    }else {
                        id_per=2;
                    }
                    _sodienthoai=dangKyController.SetDangKy(id_per,sdt,pass,nhaplaipw,DangKiActivity.this,TILsodienthoai,TILpassword,TILnhaplaipassword,txtsodienthoai,txtpassword);

                }


                //GetMassege();

            }
        });


    }


    public void  Anhxa(){
        TILsodienthoai=(TextInputLayout) findViewById(R.id.TILsodienthoaiDK);
        TILnhaplaipassword= (TextInputLayout) findViewById(R.id.TILnhaplaipassword);
        TILpassword = (TextInputLayout) findViewById(R.id.TILpasswordDK);

        txtsodienthoai=(TextInputEditText) findViewById(R.id.txtsodienthoaiDK);
        txtpassword=(TextInputEditText) findViewById(R.id.txtpasswordDK);
        txtnhaplaipassword=(TextInputEditText) findViewById(R.id.txtnhaplaipassword);
        dangKyController= new DangKyController();

        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        radioButtonAdmin=(RadioButton) findViewById(R.id.radioAdmin);
        radioButtonUser = (RadioButton) findViewById(R.id.radioUser);

        dangKyController.SetTIL(txtsodienthoai,txtpassword,txtnhaplaipassword,TILsodienthoai,TILpassword,TILnhaplaipassword);

        //getCount_Relationship();
    }


}