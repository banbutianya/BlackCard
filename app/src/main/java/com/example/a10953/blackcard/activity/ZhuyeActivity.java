package com.example.a10953.blackcard.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.GlideCircleTransform;
import com.example.a10953.blackcard.Util.HttpUtil;
import com.example.a10953.blackcard.fragment.ClubFragment_tuijian;
import com.example.a10953.blackcard.fragment.ClubFragment_zhuye_ziliao;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ZhuyeActivity extends AppCompatActivity {
    private String TAG = "ZhuyeActivity";
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=user_findlist";

    //登录ID
    private String uid;
    //登录Token
    private String token;
    //所查看的用户id
    private String user_uid;
    //页数
    private int page;

    private TabLayout zhuye_tablayout;
    private ViewPager zhuye_viewPager;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> fragmentList;
    private MyAdapter adapter;

    //背景图片地址
    private ImageView app_bg;
    private String app_bg_url;
    //返回按钮
    private ImageView allback;
    //头像
    private ImageView user_upimg;
    private String user_upimg_url;
    //昵称
    private TextView user_nick;
    private String user_nick_text;
    //性别标识
    private ImageView usersexicon;
    //个人简介
    private TextView introinfo;
    private String introinfo_text;
    //粉丝数
    private TextView fans;
    private String fans_text;
    //关注数
    private TextView follow;
    private String follow_text;
    //发布
    private TextView findlist_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuye);

        initView();
        initData();

    }

    private void initData() {
        //网络请求数据，
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");
        user_uid = "138901";
        //user_uid = intent.getStringExtra("user_uid");
        page = 1;
        Log.e(TAG,"uid是：" + uid + "，token是：" + token + "  user_uid是：" + user_uid);

        HashMap<String,String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("token",token);
        map.put("user_uid",user_uid);
        map.put("page",String.valueOf(page));

        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {

                Log.e(TAG,"网络请求成功");
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray data = object.getJSONArray("data");

                    Log.e(TAG,"response  为：" + response.toString());

                    ArrayList<JSONObject> datalist = new ArrayList<JSONObject>();
                    datalist.add(data.getJSONObject(0));

                    Log.e(TAG,"datalist " + datalist.toString());

                    JSONObject dataobject = datalist.get(0);
                    user_upimg_url = dataobject.getString("user_upimg");
                    user_nick_text = dataobject.getString("user_nick");
                    app_bg_url = dataobject.getString("app_bg");
                    fans_text = dataobject.getString("fans");
                    follow_text = dataobject.getString("follow");


                    Glide.with(ZhuyeActivity.this).load(user_upimg_url).transform(new GlideCircleTransform(ZhuyeActivity.this)).into(user_upimg);
                    Glide.with(ZhuyeActivity.this).load(app_bg_url).into(app_bg);
                    user_nick.setText(user_nick_text);
                    fans.setText(fans_text);
                    follow.setText(follow_text);
                    if(TextUtils.isEmpty(dataobject.getString("introinfo"))){
                        introinfo.setText("啥都没写...");
                    }

                    JSONArray find = dataobject.getJSONArray("findlist");
                    ArrayList<JSONObject> findlist = new ArrayList<>();
                    for (int i = 0; i < find.length(); i++){
                        findlist.add( find.getJSONObject(i));
                    }
                    Log.e(TAG, "findlist 的大小为：" + findlist.size() + "\n" + "findlist内容为：" + findlist.toString());

//                    if(findlist.size() != 0) {
//                        findlist_size.setText(findlist.size());
//                    }else {
//                        findlist_size.setText(0);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {
                Log.e(TAG,"网络请求失败");
            }
        });

    }

    private void initView() {
        app_bg = (ImageView) findViewById(R.id.app_bg);
        allback = (ImageView) findViewById(R.id.allback);
        user_upimg = (ImageView) findViewById(R.id.user_upimg);
        user_nick = (TextView) findViewById(R.id.user_nick);
        usersexicon = (ImageView) findViewById(R.id.usersexicon);
        introinfo = (TextView) findViewById(R.id.introinfo);
        fans = (TextView) findViewById(R.id.fans);
        follow = (TextView) findViewById(R.id.follow);
        findlist_size = (TextView) findViewById(R.id.findlist_size);


        Log.e(TAG,"user_nick_text  =  " +  user_nick_text);


        zhuye_tablayout = (TabLayout) findViewById(R.id.zhuye_tablayout);
        zhuye_viewPager = (ViewPager) findViewById(R.id.zhuye_viewPager);
        mTitles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        mTitles = new ArrayList<>();
        mTitles.add("动态");
        mTitles.add("资料");

        fragmentList = new ArrayList<>();
        fragmentList.add(new ClubFragment_tuijian());
        fragmentList.add(new ClubFragment_zhuye_ziliao());

        //getSupportFragmentManager()和getFragmentManager(),getSupportFragmentManager兼容3.0之前
        adapter = new MyAdapter(getSupportFragmentManager());
        zhuye_viewPager.setAdapter(adapter);

        //绑定
        zhuye_tablayout.setupWithViewPager(zhuye_viewPager);




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
