package com.example.a10953.blackcard.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.fragment.CardFragment;
import com.example.a10953.blackcard.fragment.ClubFragment;
import com.example.a10953.blackcard.fragment.EnjoyFragment;
import com.example.a10953.blackcard.fragment.MineFragment;
import com.example.a10953.blackcard.fragment.StewardsFragment;

import java.util.ArrayList;

public class HomeActivity extends FragmentActivity{
    private static final String TAG = "HomeActivity";

    private LayoutInflater layoutInflater;
    //TabHost
    private FragmentTabHost tabhost;
    //Fragment列表
    private ArrayList<Class> fragmentArrayList = new ArrayList<>();
    //ImageView列表
    private ArrayList<Integer> imageViewArrayList = new ArrayList<>();
    //tabhost标题字符串
    private ArrayList<String> textViewArrayList = new ArrayList<>();

    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentArrayList.add(CardFragment.class);
        fragmentArrayList.add(ClubFragment.class);
        fragmentArrayList.add(StewardsFragment.class);
        fragmentArrayList.add(EnjoyFragment.class);
        fragmentArrayList.add(MineFragment.class);

        imageViewArrayList.add(R.drawable.selector_bcard);
        imageViewArrayList.add(R.drawable.selector_find);
        imageViewArrayList.add(R.drawable.guanjia);
        imageViewArrayList.add(R.drawable.selector_order);
        imageViewArrayList.add(R.drawable.selector_mine);

        textViewArrayList.add("黑卡");
        textViewArrayList.add("CLUB");
        textViewArrayList.add("管家");
        textViewArrayList.add("专享");
        textViewArrayList.add("我的");

        initView();

    }

    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.container_fragment);

        //得到fragment的个数
        //int count = fragmentArrayList.size();
        int count = fragmentArrayList != null && fragmentArrayList.size() > 0 ? fragmentArrayList.size() : 0;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(textViewArrayList.get(i)).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabhost.addTab(tabSpec, fragmentArrayList.get(i), null);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        mImageView = (ImageView) view.findViewById(R.id.tab_icon);
        mImageView.setImageResource(imageViewArrayList.get(index));

        mTextView = (TextView) view.findViewById(R.id.tab_text);
        mTextView.setText(textViewArrayList.get(index));

        return view;
    }

}
