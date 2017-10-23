package com.example.a10953.blackcard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.MyApplication;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.adapter.CarouselAdapter;
import com.example.a10953.blackcard.adapter.WelFareAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private String TAG = "MainActivity";
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=guide";

    private Context context;
    //传给adapter的数据
    private List<Map<String, Object>> mapList = new ArrayList<>();

    //网络抓取的数据列表
    private ArrayList<JSONObject> arrayList = new ArrayList<>();

    private ViewPager viewPager;
    private LinearLayout pointGroup;
    private Button mApplyFor;
    private Button mMembershipLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel);

        this.context = this;
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pointGroup = (LinearLayout) findViewById(R.id.pointgroup_1);
        mApplyFor = (Button) findViewById(R.id.apply_for);
        mMembershipLogin = (Button) findViewById(R.id.membership_login);

        new Thread(runnable).start();

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

        mApplyFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击了申请办理",Toast.LENGTH_SHORT).show();
            }
        });

        mMembershipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //新线程进行网络请求
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            // TODO: http request,请求Butler数据
            StringRequest stringRequestWelFare = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject object = null;

                    //解析Json数据
                    try {
                        object = new JSONObject(response);
                        JSONArray array = object.getJSONArray("data");

                        Log.i(TAG,"json数据" + response.toString() );

                        ArrayList<JSONObject> datas = new ArrayList<JSONObject>();
                        for (int i = 0; i < array.length(); i++) {
                            datas.add(array.getJSONObject(i));
                            Log.i(TAG,"请求的第几个数据" + i + array.getJSONObject(i));
                        }
                        arrayList = datas;

                        mapList = getUrlData();

                        for(int j=0;j<mapList.size();j++){
                            Log.i(TAG,"mapList中的数据为 ：" + mapList.get(j));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CarouselAdapter carouselAdapter = new CarouselAdapter(context);
                    carouselAdapter.setData(mapList);
                    viewPager.setAdapter(carouselAdapter);

                    Log.i(TAG,"mapList.size() = " + mapList.size());

                    for (int i = 0; i < mapList.size(); i++) {
                        // 制作底部小圆点
                        ImageView pointImage = new ImageView(context);
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

            };

            MyApplication.getHttpQueue().add(stringRequestWelFare);

        }
    };


    public List<Map<String, Object>> getUrlData() {
        List<Map<String, Object>> mdata = new ArrayList<Map<String, Object>>();
        JSONObject jsonObject = new JSONObject();

        for (int i=0;i<arrayList.size();i++){
            jsonObject = arrayList.get(i);

            Map<String, Object> map = new HashMap<String, Object>();
            try {
                map.put("url", jsonObject.getString("g_img").toString());
                map.put("view", new ImageView(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mdata.add(map);
        }
        for(int j=0;j<mdata.size();j++) {
            Log.i(TAG, "添加进去的数据为：" + mdata.get(j));
        }
        return  mdata;
    }

}
