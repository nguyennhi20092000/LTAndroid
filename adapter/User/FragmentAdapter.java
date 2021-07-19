package com.example.appbanhangnew.adapter.User;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appbanhangnew.action.Admin.FragmentAdmin.HomeAdminFragment;
import com.example.appbanhangnew.action.User.Fragment.HomeFragment;
import com.example.appbanhangnew.action.User.Fragment.LienHeFragment;
import com.example.appbanhangnew.action.User.Fragment.MeFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    Context context;


    public FragmentAdapter(@NonNull FragmentManager fm, int behavior, Context context) {

        super(fm, behavior);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment(context);
            case 1:
                return new LienHeFragment();
            case 2:
                return new MeFragment(context);

        }
        return new HomeFragment(context);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
