package com.example.a10953.blackcard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10953.blackcard.R;

import java.util.ArrayList;

/**
 * Created by 10953 on 2017/10/26.
 */

public class ClubFragment_zhuye extends Fragment{

    private TabLayout zhuye_tablayout;
    private ViewPager zhuye_viewPager;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> fragmentList;
    private MyAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTitles = new ArrayList<>();
        mTitles.add("动态");
        mTitles.add("资料");

        fragmentList = new ArrayList<>();
        fragmentList.add(new ClubFragment_tuijian());
        fragmentList.add(new ClubFragment_zhuye_ziliao());

        View view = inflater.inflate(R.layout.fragment_black_club_fragment_zhuye, container, false);
        zhuye_tablayout = (TabLayout) view.findViewById(R.id.zhuye_tablayout);
        zhuye_viewPager = (ViewPager) view.findViewById(R.id.zhuye_viewPager);

        adapter = new MyAdapter(getChildFragmentManager());
        zhuye_viewPager.setAdapter(adapter);
        //绑定
        zhuye_tablayout.setupWithViewPager(zhuye_viewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList != null && fragmentList.size() != 0 ? fragmentList.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mTitles.get(position);
        }
    }



}
