package com.example.a10953.blackcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.Util.HttpUtil;
import com.example.a10953.blackcard.adapter.ButlerAdapter;
import com.example.a10953.blackcard.adapter.WelFareAdapter;
import com.example.a10953.blackcard.bean.ButlerResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 10953 on 2017/10/10.
 */

public class CardFragment extends Fragment{
    private String TAG = "CardFragment";

    //API网址
    private String urlButler = "http://api.qing-hei.com/index.php/Index/Api?type=blackcard";
    private String urlWelFare = "http://api.qing-hei.com/index.php/Index/Api?type=welfare";

    //ButlerResponse实体类对象
    private RecyclerView recycler_view_butler;
    private ButlerAdapter butlerAdapter;
    private ButlerResponse butlerResponse;
    private int count = 0;
    //item显示项列表
    private ArrayList<ButlerResponse> butlerResponseList = new ArrayList<>();

    //Welfare数据
    private WelFareAdapter welfareAdapter;
    private RecyclerView recycler_view_welfare;
    private List<JSONObject> welfareArray = new ArrayList<>();


    private LinearLayoutManager layoutManagerButler;
    private LinearLayoutManager layoutManagerWelfare;

    private String uid;
    private String token;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");
        page = 1;


        View view = inflater.inflate(R.layout.fragment_black_card, container, false);
        recycler_view_butler = (RecyclerView)view.findViewById(R.id.recycler_view_butler);
        layoutManagerButler = new LinearLayoutManager(getActivity());
        layoutManagerButler.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view_butler.setLayoutManager(layoutManagerButler);

        recycler_view_welfare = (RecyclerView)view.findViewById(R.id.recycler_view_welfare);
        layoutManagerWelfare = new LinearLayoutManager(getActivity());
        layoutManagerWelfare.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view_welfare.setLayoutManager(layoutManagerWelfare);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ButlerPost请求
        ButlerPost();
        //WelFare请求
        WelFare(uid, token, String.valueOf(page));
    }


    private void ButlerPost() {
        HashMap<String, String> map = new HashMap<>();
        //post参数，UID是会员ID，token会变。
        map.put("uid", uid);
        map.put("token", token);
        HttpUtil.sendStringRequestByPost(urlButler, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    if (data.getString("privilegenum") != null && data.getString("privilegenum").length() > 0) {
                        count = Integer.parseInt(data.getString("privilegenum"));
                    }

                    //JSONArray 解析json数组
                    JSONArray privilege = data.getJSONArray("privilege");
                    ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
                    for (int i = 0; i < privilege.length(); i++) {
                        datas.add(privilege.getJSONObject(i));
                    }

                    //添加数据？
                    for (int i=0;i<count;i++){
                        butlerResponse = new ButlerResponse();
                        butlerResponse.setButler_item_text(datas.get(i).getString("tq_name"));
                        butlerResponse.setImageViewUrl(datas.get(i).getString("tq_img"));
                        if(butlerResponseList.size() < count) {
                            butlerResponseList.add(butlerResponse);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //显示butler数据
                butlerAdapter = new ButlerAdapter(getActivity());
                recycler_view_butler.setAdapter(butlerAdapter);
                butlerAdapter.setData(butlerResponseList);
            }

            @Override
            public void onFail(VolleyError volleyError) {
                Log.i(TAG, "post请求失败" + volleyError.toString());
            }
        });
    }

    private void WelFare(String uid, String token, String page) {
        HashMap<String, String> mapwelfare = new HashMap<>();
        //post参数，UID是会员ID，token会变。
        mapwelfare.put("uid", uid);
        mapwelfare.put("token", token);
        mapwelfare.put("page",page);
        HttpUtil.sendStringRequestByPost(urlWelFare, mapwelfare, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;

                //解析Welfare  Json数据
                try {
                    object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");

                    Log.i(TAG,"json数据" + response.toString() );

                    ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
                    for (int i = 0; i < array.length(); i++) {
                        datas.add(array.getJSONObject(i));
                        Log.i(TAG,"请求的第几个数据" + i );
                    }
                    welfareArray = datas;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //显示welfare数据
                welfareAdapter = new WelFareAdapter(getActivity());
                recycler_view_welfare.setAdapter(welfareAdapter);
                welfareAdapter.setDate(welfareArray);
            }

            @Override
            public void onFail(VolleyError volleyError) {
                Log.i(TAG, "post请求失败" + volleyError.toString());
            }
        });
    }

}
