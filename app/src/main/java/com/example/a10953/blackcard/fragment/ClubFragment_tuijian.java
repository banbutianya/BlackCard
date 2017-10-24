package com.example.a10953.blackcard.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.a10953.blackcard.Listener.EndLessOnScrollListener;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.Util.HttpUtil;
import com.example.a10953.blackcard.adapter.CarouselcopyAdapter;
import com.example.a10953.blackcard.adapter.ClubTuijianAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClubFragment_tuijian extends Fragment{
    private String TAG = "ClubFragment_tuijian";
    //访问网络的URL
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=newfind";
    //设置分页
    private int page = 1;

    //登录ID
    private String uid;
    //登录Token
    private String token;
    //contex
    private Context context;

    //topicList列表，从网络上获取下来的
    private ArrayList<JSONObject> topiclist;
    //我的关注列表
    private ArrayList<JSONObject> myflowlist;
    //推荐列表
    private ArrayList<JSONObject> creamlist = new ArrayList<>();
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
    //RefreshLayout
    private RefreshLayout refreshLayout;

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

        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);

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



        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1250);
                Toast.makeText(context,"下拉刷新。。。",Toast.LENGTH_SHORT).show();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                loadMoreData(page + 1,clubTuijianAdapter);
                page++;
            }
        });

        //设置头布局，header
        setHeader(recycler_view_tuijian);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //推荐网络请求
        tuijianPost();
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


    private void tuijianPost() {
        HashMap<String, String> map = new HashMap<>();
        //post参数，UID是会员ID，token会变，通过Intent传递过来的
        map.put("uid", uid);
        map.put("token", token);
        map.put("page",String.valueOf(page));

        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;
                try {
                    //把response转换为JSONObject赋值给object
                    object = new JSONObject(response);
                    //把"data"字段的数据取出来
                    JSONObject data = object.getJSONObject("data");
                    //把"topic"字段的数据取出来，这是一个JSONArray
                    JSONArray topicarray = data.getJSONArray("topic");

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

                    //初始化topilist，并将datas赋给topiclist。（应该可以不用datas，直接将topiclist赋值，一会试一下）
                    topiclist = new ArrayList<>();
                    topiclist = datas;

                    //把“myflow”字段的数据取出来，这也是一个JSonArray
                    JSONArray myflow = data.getJSONArray("myflow");
                    Log.i(TAG,"myfllow字段的数据为 " + myflow.toString());
                    //新建一个JSONObject类型的列表datass，将myflow列表中的数据解析出来
                    ArrayList<JSONObject> datass = new ArrayList<>();
                    for(int i=0;i<myflow.length();i++){
                        datass.add(myflow.getJSONObject(i));
                    }

                    //初始化myflowlist，并将datass赋给myflowlist，
                    myflowlist = new ArrayList<>();
                    myflowlist = datass;

                    //把“cream”字段取出来，这也是一个JsonArray
                    JSONArray cream = data.getJSONArray("cream");
                    ArrayList<JSONObject> datasss = new ArrayList<>();
                    for(int i = 0; i < cream.length(); i++){
                        datasss.add(cream.getJSONObject(i));
                    }

                    creamlist = datasss;


                    //mapList是传给CarouselcopyAdapter的List<Map<String,object>>
                    mapList = getUrlData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                clubTuijianAdapter.setHeadDates(headArray);
                clubTuijianAdapter.setDate(creamlist,uid,token);

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

            @Override
            public void onFail(VolleyError volleyError) {
                Log.i(TAG, "post请求失败" + volleyError.toString());
            }
        });
    }

    public void loadMoreData(int nowpage,final ClubTuijianAdapter clubTuijianAdapter){
        final HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("token", token);
        map.put("page", String.valueOf(nowpage));
        final ArrayList<JSONObject> datasss = new ArrayList<>();

        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray cream = data.getJSONArray("cream");

                    Log.e(TAG,"cream大小为：" + cream.length());

                    if(cream .length() == 0){
                        Toast.makeText(context,"没有更多数据了",Toast.LENGTH_SHORT).show();
                    }else {
                        for (int i = 0; i < cream.length(); i++) {
                            datasss.add(cream.getJSONObject(i));
                            Log.e(TAG, "datasss的大小为：" + datasss.size());
                        }

                        creamlist.addAll(datasss);
                        cream = null;
                        datasss.clear();
                    }
                    Log.e(TAG,"creamlist的大小为：" + creamlist.size());
                    clubTuijianAdapter.setDate(creamlist,uid,token);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {
                Log.i(TAG, "post请求失败" + volleyError.toString());
            }
        });
    }

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
