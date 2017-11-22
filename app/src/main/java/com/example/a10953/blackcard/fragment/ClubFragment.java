package com.example.a10953.blackcard.fragment;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.activity.Club.EditActivity;

import java.util.ArrayList;

/**
 * Created by 10953 on 2017/10/10.
 */

public class ClubFragment extends Fragment implements View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton fabuanniu;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> fragmentList;
    private MyAdapter adapter;

    private String uid;
    private String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_black_club, container,false);
        return view;
    }

    private void initView(View view) {
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        fabuanniu = (ImageButton)view.findViewById(R.id.fabuanniu);
    }

    private void initData() {
        Intent i = getActivity().getIntent();
        uid = i.getStringExtra("uid");
        token = i.getStringExtra("token");

        mTitles = new ArrayList<>();
        mTitles.add("推荐");
        mTitles.add("关注");

        fragmentList = new ArrayList<>();
        fragmentList.add(new ClubFragment_tuijian());
        fragmentList.add(new ClubFragment_guanzhu());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

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

        fabuanniu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabuanniu:
                Toast.makeText(getActivity(),"点击了发布按钮",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("token",token);
                startActivity(intent);
                break;
        }
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
