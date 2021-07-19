package com.example.appbanhangnew.action.User.DonHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.DeleteActivity;
import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.SanPham.DiaChiActivity;
import com.example.appbanhangnew.action.User.SanPham.PopVanChuyenActivity;
import com.example.appbanhangnew.adapter.User.VanChuyenAdapter;
import com.example.appbanhangnew.model.DonHang.DonHang;
import com.example.appbanhangnew.model.QuanHuyen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class VanChuyenDonHangActivity extends AppCompatActivity {
    Toolbar toolbarVanChuyenDonHang;
    EditText txttenNguoinhan, txtsdtnguoinhan,txtdiachinhanhang;
    Spinner spinnerPhuong, spinnerQuan;
    ArrayList<QuanHuyen> listquanhuyen;
    ArrayList<String> listxaphuong;
    ArrayList<String> nameQuanhuyen;
    String jsonText="";
    QuanHuyen quanHuyen;
    Button btnedit;
    int vanchuyen=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_chuyen_don_hang);
        AnhXa();
        getData();
        listvanchuyen();
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHangActivity.setVanChuyen=true;
                //PopVanChuyenActivity.toolbarDiaChi.setTitle(listVanchuyen.get(position).getName());
                DonHangActivity.txttennguoinhan.setText(txttenNguoinhan.getText().toString());
                DonHangActivity.txtdiachinhanhang.setText(txtdiachinhanhang.getText().toString()+", "+spinnerPhuong.getSelectedItem().toString()+", "+spinnerQuan.getSelectedItem().toString());

                DonHangActivity.txtsodienthoainguoinhan.setText(txtsdtnguoinhan.getText().toString());
                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                DonHangActivity.Tongtien=DonHangActivity.Tongtien+vanchuyen;
                DonHangActivity.txtTongtien.setText("Tổng: "+decimalFormat.format(DonHangActivity.Tongtien)+" Đ");

                finish();
            }
        });


    }

    public void AnhXa(){

        txttenNguoinhan=(EditText) findViewById(R.id.txtTenNguoiNhanhang);
        txtdiachinhanhang=(EditText) findViewById(R.id.txtdiachinhanhang);
        txtsdtnguoinhan=(EditText) findViewById(R.id.txtSDTNguoiNhanhang);
        spinnerPhuong=(Spinner) findViewById(R.id.spinnerWard);
        spinnerQuan=(Spinner) findViewById(R.id.spinnerDistrict);
        btnedit=(Button) findViewById(R.id.btnEditDiaChi);

        listquanhuyen=new ArrayList<>();
        listxaphuong=new ArrayList<>();
        nameQuanhuyen=new ArrayList<>();




    }

    public void getData(){
        txttenNguoinhan.setText(getIntent().getStringExtra("tennguoinhan").trim());
        txtsdtnguoinhan.setText(getIntent().getStringExtra("sodienthoai").trim());
        txtdiachinhanhang.setText(getIntent().getStringExtra("diachi").trim());
    }

    public void listvanchuyen(){




        try {
            jsonText = readText(VanChuyenDonHangActivity.this,R.raw.quan_huyen_tp);
            JSONArray jsonArray = new JSONArray(jsonText);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.length()>0){
                    String name=jsonObject.getString("name_with_type");
                    String type=jsonObject.getString("type");
                    int code=jsonObject.getInt("code");
                    quanHuyen= new QuanHuyen(name,type,code);
                    listquanhuyen.add(quanHuyen);
                    nameQuanhuyen.add(quanHuyen.getName());
                }

            }
            //Toast.makeText(getApplicationContext(),listVanchuyen.size()+"",Toast.LENGTH_LONG).show();
            //vanChuyenAdapter=new VanChuyenAdapter(listVanchuyen,getApplicationContext());
            //listViewvanchuyen.setAdapter(vanChuyenAdapter);
            ArrayAdapter<String> quanHuyenArrayAdapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nameQuanhuyen);
            spinnerQuan.setAdapter(quanHuyenArrayAdapter);
            spinnerQuan.setSelection(0);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
            Log.d("json",e.getMessage().toString());
        }

        spinnerQuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String namejson="q_"+listquanhuyen.get(position).getCode()+".json";
                listxaphuong.clear();
                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                if(listquanhuyen.get(position).getType().equals("quan")){
                    vanchuyen=22000;
                    DonHangActivity.txtphivanchuyen.setText("Phí vận chuyển: "+decimalFormat.format(22000)+" Đ");
                }else {
                    vanchuyen=35000;
                    DonHangActivity.txtphivanchuyen.setText("Phí vận chuyển: "+decimalFormat.format(35000)+" Đ");
                }
                try {
                    jsonText = readText(VanChuyenDonHangActivity.this,R.raw.xa_phuong);
                    JSONArray jsonArray = new JSONArray(jsonText);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        if(jsonObject.length()>0){
                            int code=jsonObject.getInt("parent_code");
                            if(code==listquanhuyen.get(position).getCode()){
                                String name=jsonObject.getString("name_with_type");
                                listxaphuong.add(name);
                            }


                        }

                    }

                    ArrayAdapter<String> XaPhuongnArrayAdapter= new ArrayAdapter<String>(VanChuyenDonHangActivity.this,R.layout.support_simple_spinner_dropdown_item,listxaphuong);
                    spinnerPhuong.setAdapter(XaPhuongnArrayAdapter);
                    spinnerPhuong.setSelection(0);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
                    Log.d("json",e.getMessage().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
    public static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}