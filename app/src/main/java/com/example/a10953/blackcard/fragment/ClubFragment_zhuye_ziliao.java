package com.example.a10953.blackcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by 10953 on 2017/10/26.
 */

public class ClubFragment_zhuye_ziliao extends Fragment{

    private String TAG = "ClubFragment_zhuye_ziliao";
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=user_info";
    private HashMap<String,String> map;

    private String uid;
    private String token;
    private String user_id;

    private Intent intent;

    private TextView age;
    private String age_text;
    private TextView birth;
    private String birth_text;
    private TextView location;
    private String location_text;
    private TextView business;
    private String business_text;
    private TextView intro;
    private String intro_text;
    private TextView hobby;
    private StringBuffer hobby_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_black_club_fragment_zhuye_ziliao, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intent = getActivity().getIntent();
        uid = intent.getStringExtra("uid");
        token = intent.getStringExtra("token");

        initView(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {

        user_id = "21334";

        map = new HashMap<>();
        map.put("uid",uid);
        map.put("token",token);
        map.put("user_id",user_id);


        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");

                    Log.e(TAG,response.toString());

                    Log.e(TAG,"数据为" + data.getString("age"));
                    Log.e(TAG,"数据为" + data.getString("birth"));
                    Log.e(TAG,"数据为" + data.getString("location"));
                    Log.e(TAG,"数据为" + data.getString("business"));
                    Log.e(TAG,"数据为" + data.getString("intro"));

                    if(TextUtils.isEmpty(data.getString("age"))){
                        age_text = "保密";
                    }else {
                        age_text = data.getString("age");
                    }

                    if (TextUtils.isEmpty(data.getString("birth"))) {
                        birth_text = "保密";
                    } else {
                        birth_text = data.getString("birth");
                    }

                    if (TextUtils.isEmpty(data.getString("location"))) {
                        location_text = "保密";
                    } else {
                        location_text = data.getString("location");
                    }

                    if (TextUtils.isEmpty(data.getString("business"))) {
                        business_text = "保密";
                    } else {
                        business_text = data.getString("business");
                    }
                    if (TextUtils.isEmpty("intro")) {
                        intro_text = "保密";
                    } else {
                        intro_text = data.getString("intro");
                    }

                    JSONArray array = data.getJSONArray("hobby");
                    ArrayList<JSONObject> hobbylist = new ArrayList();

                    Log.e(TAG,"array大小为：" + array.length());


                    if (array.length() == 0) {
                        hobby_text.append("保密");
                    } else {
                        for (int i = 0; i < array.length(); i++){
                            hobbylist.add(array.getJSONObject(i));
                        }

                        for(int i = 0; i< hobbylist.size(); i++){
                            hobby_text.append(hobbylist.get(i).getString("ho_name") + ",");
                        }
                    }


                    age.setText(age_text);
                    birth.setText(birth_text);
                    location.setText(location_text);
                    business.setText(business_text);
                    intro.setText(intro_text);
                    hobby.setText(hobby_text);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {

            }
        });

    }

    private void initView(View view) {
        age = (TextView) view.findViewById(R.id.age);
        birth = (TextView) view.findViewById(R.id.birth);
        location = (TextView) view.findViewById(R.id.location);
        business = (TextView) view.findViewById(R.id.business);
        intro = (TextView) view.findViewById(R.id.intro);
        hobby = (TextView) view.findViewById(R.id.hobby);

        hobby_text = new StringBuffer();

    }
}
