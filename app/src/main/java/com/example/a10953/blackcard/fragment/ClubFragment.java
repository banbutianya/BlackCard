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
import java.util.List;

/**
 * Created by 10953 on 2017/10/10.
 */

public class ClubFragment extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> mTitles;
    private List<Fragment> fragmentList;
    private List<Class> classList;
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTitles = new ArrayList<>();
        mTitles.add("推荐");
        mTitles.add("关注");

        fragmentList = new ArrayList<>();
        fragmentList.add(new ClubFragment_tuijian());
        fragmentList.add(new ClubFragment_guanzhu());

        View view = inflater.inflate(R.layout.fragment_black_club, container,false);
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);

        //ViewPager的适配器
        /**
         *  getFragmentManager()是所在fragment 父容器的碎片管理，
         *  getChildFragmentManager()是在fragment 里面子容器的碎片管理。
         */
        //adapter = new MyAdapter(getFragmentManager());
        adapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    class MyAdapter extends FragmentPagerAdapter{

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
