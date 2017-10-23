package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 10953 on 2017/10/9.
 */

public class CarouselcopyAdapter extends PagerAdapter{

    private List<Map<String, Object>> mapList = new ArrayList<>();
    private Context context;
    private ImageView imageView;

    //构造函数，传递context
    public CarouselcopyAdapter(Context context){
        this.context = context;
    }
    //fragment调用SetData通过传递数据
    public void setData(List<Map<String,Object>> mapList){
        this.mapList = mapList;
        //不用刷新整个Recyclerview，只刷新改变的Item
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //返回图片数量
        return mapList !=null && mapList.size() > 0 ? mapList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //imageView初始化为mpList中传递过来的ImageView
        imageView = (ImageView)mapList.get(position).get("view");
        //设置图片显示方式，  CENTER_CROP：充满
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Glide加载图片
        Glide.with(context)
                //load(URL地址，也是通过mapList传递过来的)
                .load(mapList.get(position).get("url"))
                //into(imageView)，设置显示到那个ImageView中
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
