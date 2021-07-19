package com.example.appbanhangnew.action.User.SanPham;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.User.VanChuyenAdapter;
import com.example.appbanhangnew.model.QuanHuyen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DiaChiActivity extends AppCompatActivity {

    Toolbar toolbarvanchuyen;
    ListView listViewvanchuyen;
    VanChuyenAdapter vanChuyenAdapter;
    ArrayList<QuanHuyen> listVanchuyen;
    QuanHuyen quanHuyen;
    String jsonText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);
        AnhXa();
        ActionBar();
        listvanchuyen();
    }


    public void AnhXa(){
        toolbarvanchuyen=(Toolbar) findViewById(R.id.toolbar_vanchuyen);
        listViewvanchuyen=(ListView) findViewById(R.id.listviewvanchuyen);
    }
    public void ActionBar(){
        setSupportActionBar(toolbarvanchuyen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarvanchuyen.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);



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

    public void listvanchuyen(){


        listVanchuyen = new ArrayList<>();

        try {
            jsonText = readText(DiaChiActivity.this,R.raw.quan_huyen_tp);
            JSONArray jsonArray = new JSONArray(jsonText);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.length()>0){
                    String name=jsonObject.getString("name_with_type");
                    String type=jsonObject.getString("type");
                    int code= jsonObject.getInt("code");
                    quanHuyen= new QuanHuyen(name,type,code);
                    listVanchuyen.add(quanHuyen);
                }

            }
            Toast.makeText(getApplicationContext(),listVanchuyen.size()+"",Toast.LENGTH_LONG).show();
            vanChuyenAdapter=new VanChuyenAdapter(listVanchuyen,getApplicationContext());
            listViewvanchuyen.setAdapter(vanChuyenAdapter);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
            Log.d("json",e.getMessage().toString());
        }


        listViewvanchuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*Intent intent= new Intent(DiaChiActivity.this,PopVanChuyenActivity.class);
                intent.putExtra("quan",listVanchuyen.get(position).getName());
                intent.putExtra("type",listVanchuyen.get(position).getType());
                startActivity(intent);
                finish();*/
                PopVanChuyenActivity.setVanChuyen=true;
                PopVanChuyenActivity.toolbarDiaChi.setTitle(listVanchuyen.get(position).getName());
                if(listVanchuyen.get(position).getType().equals("quan")){
                    PopVanChuyenActivity.textViewGHTK.setText("22.000");
                }else {
                    PopVanChuyenActivity.textViewGHTK.setText("35.000");
                }
                finish();
                //Toast.makeText(DiaChiActivity.this,listVanchuyen.get(position)+"", Toast.LENGTH_LONG).show();
            }
        });

    }

}