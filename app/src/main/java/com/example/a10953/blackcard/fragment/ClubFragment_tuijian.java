package com.example.a10953.blackcard.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a10953.blackcard.MyApplication;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.adapter.CarouselcopyAdapter;
import com.example.a10953.blackcard.adapter.ClubTuijianAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClubFragment_tuijian extends Fragment {
    private String TAG = "ClubFragment_tuijian";
    //访问网络的URL
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=newfind";

    //登录ID
    private String uid;
    //登录Token
    private String token;
    //contex
    private Context context;

    //topicList列表，从网络上获取下来的
    private ArrayList<JSONObject> topiclist;
    //
    private ArrayList<JSONObject> myflowlist;
    //传给ViewPager适配器的数据，包含一个URL和一个ImageView
    private ArrayList<Map<String,Object>> mapList;

    //显示图片的ViewPager
    private ViewPager viewPager;
    //ViewPager下方的小圆点
    private LinearLayout pointGroup;

    //推荐页面显示的RcyclerView
    private RecyclerView recycler_view_tuijian;
    //ClubTuijianAdapter适配器
    private ClubTuijianAdapter clubTuijianAdapter;
    //LinearLayoutManager，管理布局和滑动
    private LinearLayoutManager layoutManager;

    private ArrayList<JSONObject> headArray = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //获取Intent传递过来的uid和token
        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");

        //获取context
        this.context = getActivity();
        //绑定fragment_black_club_fragment_tuijian布局，
        View view = inflater.inflate(R.layout.fragment_black_club_fragment_tuijian, container, false);
        //为Recyclerview绑定布局
        recycler_view_tuijian = (RecyclerView)view.findViewById(R.id.recycler_view_tuijian);
        //实例化layoutmanager
        layoutManager = new LinearLayoutManager(getActivity());
        //设置Recyclerview竖直显示
        recycler_view_tuijian.setLayoutManager(layoutManager);
        //管理Item动画效果
        recycler_view_tuijian.setItemAnimator(new DefaultItemAnimator());

        //clubTuijianAdapter初始化
        clubTuijianAdapter = new ClubTuijianAdapter();
        clubTuijianAdapter.setContext(context);
        recycler_view_tuijian.setAdapter(clubTuijianAdapter);


        //通过setDate方法，向Recyclerview中添加数据
        //clubTuijianAdapter.setDate(myflowlist,context);

        //设置头布局，header
        setHeader(recycler_view_tuijian);

        //开启线程进行网络请求
        new Thread(runnable).start();

        return view;
    }

    private void setHeader(RecyclerView view) {
        /**
         * header布局实现
         */
        //获取头布局的xml布局
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_black_club_fragment_tuijian_head, view, false);

        //ViewPage和PointGroup绑定对应的资源ID
        viewPager = (ViewPager)header.findViewById(R.id.head_viewpager);
        pointGroup = (LinearLayout)header.findViewById(R.id.pointgroup_2);

        // 对ViewPager设置滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            int lastPosition;
            @Override
            public void onPageSelected(int position) {
                // 设置当前页面选中
                pointGroup.getChildAt(position).setSelected(true);
                // 设置前一页不选中
                pointGroup.getChildAt(lastPosition).setSelected(false);
                // 替换位置
                lastPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //添加头布局
        clubTuijianAdapter.setHeaderView(header);
    }


    //新线程进行网络请求
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            /**
             *  TODO: http request,请求Butler数据
             *  StringRequest（1：请求方式，2：请求成功回调，3：请求失败回调）{
             *                              重写getParams方法传POST参数
             *                  }
             */
            StringRequest stringRequestButler = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    JSONObject object = null;
                    try {
                        //把response转换为JSONObject赋值给object
                        object = new JSONObject(response);
                        //把"data"字段的数据取出来
                        JSONObject data = object.getJSONObject("data");
                        //把"topic"字段的数据取出来，这是一个JSONArray
                        JSONArray topicarray = data.getJSONArray("topic");

                        //把“myflow”字段的数据取出来，这也是一个JSonArray
                        JSONArray myflow = data.getJSONArray("myflow");
                        Log.i(TAG,"myfllow字段的数据为 " + myflow.toString());
                        //新建一个JSONObject类型的列表datass，将myflow列表中的数据解析出来
                        ArrayList<JSONObject> datass = new ArrayList<>();
                        for(int i=0;i<myflow.length();i++){
                            datass.add(myflow.getJSONObject(i));
                        }

                        //新建一个JSONObject类型的列表datas，将toicarray列表中的数据解析出来
                        ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
                        for (int i = 0; i < topicarray.length(); i++) {
                            //按顺序将topicarray中的JSONObject赋给datas，datas里面就有了需要的信息
                            datas.add(topicarray.getJSONObject(i));
                        }

                        JSONObject myinfo = data.getJSONObject("myinfo");
                        headArray.add(myinfo);
                        JSONObject weather = data.getJSONObject("weather");
                        JSONArray results = weather.getJSONArray("results");
                        ArrayList<JSONObject> headArrayCopy = new ArrayList<>();
                        for(int i = 0; i < results.length(); i++){
                            headArrayCopy.add(results.getJSONObject(i));
                            headArray.add(headArrayCopy.get(i));
                        }

                        //初始化myflowlist，并将datass赋给myflowlist，
                        myflowlist = new ArrayList<>();
                        myflowlist = datass;
                        //myflowlist.addAll(datass);

                        //初始化topilist，并将datas赋给topiclist。（应该可以不用datas，直接将topiclist赋值，一会试一下）
                        topiclist = new ArrayList<>();
                        topiclist = datas;
                        //mapList是传给CarouselcopyAdapter的List<Map<String,object>>
                        mapList = getUrlData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    clubTuijianAdapter.setHeadDates(headArray);
                    clubTuijianAdapter.setDate(myflowlist);

                    //新建一个CarouselcopyAdapter，并通过构造函数将context传递过去
                    CarouselcopyAdapter carouselcopyAdapter = new CarouselcopyAdapter(context);
                    //通过setData方法将mapList传递过去
                    carouselcopyAdapter.setData(mapList);
                    viewPager.setAdapter(carouselcopyAdapter);

                    // 准备显示的图片集合
                    for (int i = 0; i < mapList.size(); i++) {
                        // 制作底部小圆点
                        ImageView pointImage = new ImageView(getActivity());
                        pointImage.setImageResource(R.drawable.shape_point_selector);
                        // 设置小圆点的布局参数
                        int PointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PointSize, PointSize);

                        if (i > 0) {
                            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.point_margin);
                            pointImage.setSelected(false);
                        } else {
                            pointImage.setSelected(true);
                        }
                        pointImage.setLayoutParams(params);
                        // 添加到容器里
                        pointGroup.addView(pointImage);
                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "post请求失败" + error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    //post参数，UID是会员ID，token会变，通过Intent传递过来的
                    map.put("uid", uid);
                    map.put("token", token);
                    map.put("page","1");
                    return map;
                }
            };
            //添加到RequestQueue中
            MyApplication.getHttpQueue().add(stringRequestButler);
        }
    };

    public ArrayList<Map<String, Object>> getUrlData() {
        //传递给CarouselcopyAdapter的数据，是一个包含Map的列表，
        ArrayList<Map<String, Object>> mdata = new ArrayList<Map<String, Object>>();
        JSONObject jsonObject = new JSONObject();

        for (int i=0;i<topiclist.size();i++){
            //topiclist中有每一张图片的URL
            jsonObject = topiclist.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            try {
                //向map中添加数据，键值对
                map.put("url", jsonObject.getString("to_pic").toString());
                map.put("view", new ImageView(getActivity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mdata.add(map);
        }
        return  mdata;
    }



}
