package com.example.a10953.blackcard.fragment;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
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
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.HttpUtil;
import com.example.a10953.blackcard.activity.ZhuyeActivity;
import com.example.a10953.blackcard.adapter.ClubTuijianAdapter;
import com.example.a10953.blackcard.adapter.ZhuyeDongtaiAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by 10953 on 2017/10/26.
 */

public class ClubFragment_zhuye_dongtai extends Fragment{

    private String TAG = "ClubFragment_zhuye_dongtai";
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=user_findlist";
    private RecyclerView recycler_view_dongtai;
    private LinearLayoutManager layoutManager;
    private ZhuyeDongtaiAdapter zhuyeDongtaiAdapter;
    private ArrayList<JSONObject> findlist = new ArrayList<>();

    private String uid;
    private String token;
    private String user_uid;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_black_club_fragment_zhuye_dongtai, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");

        initView(view);
        dongtaiPost();
    }

    private void initView(View view) {
        layoutManager = new LinearLayoutManager(getContext());
        zhuyeDongtaiAdapter = new ZhuyeDongtaiAdapter();
        recycler_view_dongtai = (RecyclerView) view.findViewById(R.id.recycler_view_dongtai);
        recycler_view_dongtai.setLayoutManager(layoutManager);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void dongtaiPost(){

        user_uid = "42403";

        HashMap<String,String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("token",token);
        map.put("user_uid",user_uid);
        map.put("page",String.valueOf(page));

        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray data = object.getJSONArray("data");


                    JSONObject dataobject  = data.getJSONObject(0);

                    String user_upimg_url = dataobject.getString("user_upimg");
                    String user_nick = dataobject.getString("user_nick");

                    JSONArray find = dataobject.getJSONArray("findlist");
                    findlist = new ArrayList<>();
                    for (int i = 0; i < find.length(); i++){
                        findlist.add( find.getJSONObject(i));
                        Log.e(TAG,"findlist.get(i).toString() 为: " + findlist.get(i).toString());
                    }

                    Log.e(TAG,"findlist大小为：" + findlist.size());

                    zhuyeDongtaiAdapter.setData(findlist,getContext(),user_upimg_url,user_nick);
                    recycler_view_dongtai.setAdapter(zhuyeDongtaiAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {

            }
        });
    }

}
