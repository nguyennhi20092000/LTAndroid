package com.example.appbanhangnew.action.User_Admin.ThietLapTaiKhoan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.User_Admin.DoiMatKhauController;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DoiMatKhauActivity extends AppCompatActivity {
    Button btnCheck,btnSave;
    TextInputLayout TILpasswordold,TILpasswordnew,TILnhaplaipassword;
    TextInputEditText txtpasswordold,txtpasswordnew,txtnhaplaipassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String password="";
    int ID_USER=0;
    DoiMatKhauController doiMatKhauController;
    Toolbar toolbarDoiPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        AnhXa();
        ActionBar();
    }
    public void AnhXa(){
        btnCheck=(Button) findViewById(R.id.btnCheck);
        btnSave=(Button) findViewById(R.id.btnSave);
        TILpasswordold=(TextInputLayout) findViewById(R.id.TILpasswordold);
        TILpasswordnew=(TextInputLayout) findViewById(R.id.TILpasswordnew);
        TILnhaplaipassword=(TextInputLayout) findViewById(R.id.TILnhaplaipasswordnew);
        txtpasswordold=(TextInputEditText)findViewById(R.id.txtpasswordold);
        txtpasswordnew=(TextInputEditText)findViewById(R.id.txtpasswordnew);
        txtnhaplaipassword=(TextInputEditText)findViewById(R.id.txtnhaplaipasswordnew);
        toolbarDoiPass=(androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarpassword);
        TILnhaplaipassword.setEnabled(false);
        TILpasswordnew.setEnabled(false);
        btnSave.setEnabled(false);
        txtnhaplaipassword.setEnabled(false);
        txtpasswordnew.setEnabled(false);
        sharedPreferences=getSharedPreferences("USER_FILE", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        password=sharedPreferences.getString("password", "");
        ID_USER=sharedPreferences.getInt("iduser",0);
        doiMatKhauController=new DoiMatKhauController(DoiMatKhauActivity.this);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnCheck:

                if(txtpasswordold.getText().toString().trim()!=null){
                    if(txtpasswordold.getText().toString().trim().equals(password)){
                        TILnhaplaipassword.setEnabled(true);
                        TILpasswordnew.setEnabled(true);
                        btnSave.setEnabled(true);
                        txtnhaplaipassword.setEnabled(true);
                        txtpasswordnew.setEnabled(true);
                        TILpasswordold.setEnabled(false);
                        txtpasswordold.setEnabled(false);
                        btnCheck.setEnabled(false);
                    }else {
                        Toast.makeText(getApplicationContext(),"Bạn đã nhập sai mật khẩu",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btnSave:
                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                if(txtpasswordnew.getText().toString().trim().equals(txtnhaplaipassword.getText().toString().trim())){
                    doiMatKhauController.EditPassword(ID_USER,txtpasswordnew.getText().toString().trim());
                }else {
                    Toast.makeText(getApplicationContext(),"Bạn nhập password chưa trùng khớp",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
    public void ActionBar() {
        setSupportActionBar(toolbarDoiPass);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarDoiPass.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
}