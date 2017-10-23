package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10953 on 2017/10/12.
 */

public class WelFareAdapter extends RecyclerView.Adapter<WelFareAdapter.ViewHolder>{
    private Context context;
    private List<JSONObject> jsonObjectList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView welfare_item_w_pic;
        TextView welfare_item_w_title;
        TextView welfare_item_w_jiage;
        TextView welfare_item_danwei;

        public ViewHolder(View view) {
            super(view);
            welfare_item_w_pic = (ImageView) view.findViewById(R.id.welfare_item_w_pic);
            welfare_item_w_title = (TextView) view.findViewById(R.id.welfare_item_w_title);
            welfare_item_w_jiage = (TextView)view.findViewById(R.id.welfare_item_w_jiage);
            welfare_item_danwei = (TextView)view.findViewById(R.id.welfare_item_danwei);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject welFareResponse = jsonObjectList.get(position);
        try {
            holder.welfare_item_w_jiage.setText((CharSequence) welFareResponse.getString("w_jiage"));
            holder.welfare_item_w_title.setText((CharSequence) welFareResponse.getString("w_title"));
            holder.welfare_item_danwei.setText((CharSequence) welFareResponse.getString("danwei"));
            Glide.with(context).load(welFareResponse.getString("w_pic")).into(holder.welfare_item_w_pic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonObjectList != null && jsonObjectList.size() > 0 ? jsonObjectList.size() : 0;
    }

    public WelFareAdapter(Context context){
        this.context = context;
    }

    public void setDate(List<JSONObject> jsonObjectList){
        this.jsonObjectList = jsonObjectList;
        notifyDataSetChanged();
    }

}
