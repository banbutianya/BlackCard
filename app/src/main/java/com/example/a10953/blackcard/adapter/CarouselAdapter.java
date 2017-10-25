package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 10953 on 2017/10/9.
 */

public class CarouselAdapter extends PagerAdapter{
    private String TAG = "CarouselAdapter";

    private List<Map<String, Object>> mapList = new ArrayList<>();
    private Context context;
    private ImageView imageView;

    public CarouselAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Map<String,Object>> mapList){
        this.mapList = mapList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mapList != null && mapList.size() > 0 ? mapList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        imageView = (ImageView)mapList.get(position).get("view");
        //设置图片显示方式
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Picasso加载图片
        Picasso.with(context)
                .load((String) mapList.get(position).get("url"))
                .into(imageView);
        //将动态产生的View添加到排版View中，添加ImageView,
        container.addView(imageView);
        //返回这个View
        return mapList.get(position).get("view");

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
