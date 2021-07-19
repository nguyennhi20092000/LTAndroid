package com.example.appbanhangnew.action.User_Admin.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appbanhangnew.Bridge.Account;
import com.example.appbanhangnew.Bridge.Nguoiban;
import com.example.appbanhangnew.Bridge.Nguoimua;
import com.example.appbanhangnew.Bridge.People;
import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.controller.User_Admin.AccountController;
import com.example.appbanhangnew.controller.User_Admin.FacebookController;
import com.example.appbanhangnew.model.User;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class AccountActivity extends AppCompatActivity {

    TextInputLayout TILsodienthoai;
    TextInputLayout TILpassword;

    TextInputEditText txtsodienthoai;
    TextInputEditText txtpassword;

    String sodienthoai;
    String password;


    public static User user;

    //public static int ID_USER=0;

    //CallbackManager callbackManager;
    AccountController accountController;

    SharedPreferences sharedPreferences;
    RadioGroup radioGroup;
    RadioButton radioButtonAdmin,radioButtonUser;
    int id_per;
    FacebookController facebookController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AnhXa();


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonDangKy:
                Intent intent= new Intent(AccountActivity.this, DangKiActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonDangNhap:
                sodienthoai=txtsodienthoai.getText().toString();
                password=txtpassword.getText().toString();
                if(radioButtonAdmin.isChecked()==false&& radioButtonUser.isChecked()==false){
                    Toast.makeText(getApplicationContext(),"Bạn chưa chọn quyền",Toast.LENGTH_LONG).show();
                }else {
                    if(radioButtonUser.isChecked()==true){
                        //id_per=2;
                        Account nguoimua=new People(sodienthoai,password,this,new Nguoimua());
                        nguoimua.dangnhap();
                    }else {
                        //id_per=1;
                        Account nguoiban=new People(sodienthoai,password,this,new Nguoiban());
                        nguoiban.dangnhap();
                    }
                    //accountController.Account_Controller(id_per,sodienthoai,password,this);
                }


                //ID_USER=accountController.getIDUser();


                //Toast.makeText(getApplicationContext(),ID_USER,Toast.LENGTH_SHORT).show();
                break;


        }

    }


    public void AnhXa(){
        TILpassword=(TextInputLayout) findViewById(R.id.TILPassword);
        TILsodienthoai=(TextInputLayout) findViewById(R.id.TILSodienthoai);

        txtpassword=(TextInputEditText) findViewById(R.id.txtPassword);
        txtsodienthoai=(TextInputEditText)findViewById(R.id.txtSodienthoai);

        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        radioButtonUser=(RadioButton) findViewById(R.id.radioUser);
        radioButtonAdmin=(RadioButton) findViewById(R.id.radioAdmin);

        //fbLoginButton=(LoginButton) findViewById(R.id.buttonDangnhapbangfb);

        //fbLoginButton.setReadPermissions("email");
        accountController=new AccountController(AccountActivity.this);

        accountController.setTIL(txtsodienthoai,TILsodienthoai,txtpassword,TILpassword);
        sharedPreferences = this.getSharedPreferences("USER_FILE", MODE_PRIVATE);

        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();
       // facebookController=new FacebookController(AccountActivity.this,sharedPreferences,sharedPreferences.edit());

    }



}