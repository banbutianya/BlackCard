package com.example.a10953.blackcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RedirectError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a10953.blackcard.MyApplication;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.adapter.ButlerAdapter;
import com.example.a10953.blackcard.adapter.WelFareAdapter;
import com.example.a10953.blackcard.bean.ButlerResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");


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

        //开启子线程进行网络请求，通过handler更新界面。返回一个json字符串
        //启动子线程
        new Thread(runnable).start();
        new Thread(runnable2).start();

    }

    //新线程进行网络请求
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            //
            // TODO: http request,请求Butler数据
            //
            StringRequest stringRequestButler = new StringRequest(com.android.volley.Request.Method.POST, urlButler, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "post请求失败" + error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        //post参数，UID是会员ID，token会变。
                        map.put("uid", uid);
                        map.put("token", token);
                        return map;
                    }
                };

                MyApplication.getHttpQueue().add(stringRequestButler);

        }
    };


    //新线程进行网络请求
    Runnable runnable2 = new Runnable(){
        @Override
        public void run() {
            //
            // TODO: http request,请求Butler数据
            //
            StringRequest stringRequestWelFare = new StringRequest(com.android.volley.Request.Method.POST, urlWelFare, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "post请求失败" + error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> mapwelfare = new HashMap<>();
                    //post参数，UID是会员ID，token会变。
                    mapwelfare.put("uid", uid);
                    mapwelfare.put("token", token);
                    mapwelfare.put("page","1");
                    return mapwelfare;
                }
            };
            MyApplication.getHttpQueue().add(stringRequestWelFare);

        }
    };


}
