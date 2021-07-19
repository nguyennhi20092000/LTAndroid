package com.example.appbanhangnew.action.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.adapter.User.FragmentAdapter;
import com.example.appbanhangnew.adapter.Admin.FragmentAdminAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    public  static BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    int id_per=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        AnhXa();
        setUpViewPage();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        viewPager.setCurrentItem(0);

                        break;
                    case R.id.lienhe:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.Me:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }

    private void setUpViewPage() {
        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        id_per= sharedPreferences.getInt("id_per",0);
        //Toast.makeText(getApplicationContext(),id_per+"",Toast.LENGTH_LONG).show();
        Log.d("ID_Per",id_per+"");
        if(id_per==1){

            FragmentAdminAdapter fragmentAdminAdapter= new FragmentAdminAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,MainActivity.this);
            viewPager.setAdapter(fragmentAdminAdapter);
        }else {
            FragmentAdapter fragmentAdapter= new FragmentAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,MainActivity.this);
            viewPager.setAdapter(fragmentAdapter);
        }
        //FragmentAdapter fragmentAdapter= new FragmentAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,MainActivity.this);

    }

    private void AnhXa() {
        viewPager=(ViewPager) findViewById(R.id.ViewPage);
        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottomnavigation);
        sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        id_per= sharedPreferences.getInt("id_per",0);

    }




}