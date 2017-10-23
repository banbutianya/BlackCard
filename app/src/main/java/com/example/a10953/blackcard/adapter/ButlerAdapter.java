package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.MyApplication;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.activity.OnItemClickListener;
import com.example.a10953.blackcard.bean.ButlerResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 10953 on 2017/10/10.
 */

public class ButlerAdapter extends RecyclerView.Adapter<ButlerAdapter.ViewHolder>{
    private String TAG = "ButlerAdapter";

    private List<ButlerResponse> mButlerList = new ArrayList<>();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView butler_item_text;
        ImageView butler_item_image;

        public ViewHolder(View view) {
            super(view);
            butler_item_text = (TextView) view.findViewById(R.id.butler_item_text);
            butler_item_image = (ImageView) view.findViewById(R.id.butler_item_image);
        }
    }

    public ButlerAdapter(Context context){
        this.context = context;
    }

    public void setData(List<ButlerResponse> butlerResponseList) {
        mButlerList = butlerResponseList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.butler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ButlerResponse butlerResponse = mButlerList.get(position);
        holder.butler_item_text.setText(butlerResponse.getButler_item_text());
//        Log.i(TAG, "图片URL地址为   Adapter：" + butlerResponse.getImageViewUrl());
        Glide.with(context).load(butlerResponse.getImageViewUrl()).into(holder.butler_item_image);
    }

    @Override
    public int getItemCount() {
//        if (mButlerList != null) {
//            Log.i("ceshi", "size=" + mButlerList.size());
//        }
        return mButlerList != null && mButlerList.size() > 0 ? mButlerList.size() : 0;
    }

}
