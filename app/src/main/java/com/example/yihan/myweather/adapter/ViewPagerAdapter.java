package com.example.yihan.myweather.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>fragmentList=new ArrayList<>();
    private  List<Integer>itemList=new ArrayList<>();

    private FragmentManager FM;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.itemList = itemList;
        this.FM = FM;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    @Override

    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
