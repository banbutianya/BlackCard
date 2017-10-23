package com.example.a10953.blackcard.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10953.blackcard.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 10953 on 2017/10/10.
 */

public class blackAdapter extends RecyclerView.Adapter<blackAdapter.blackHolder> {
    private LayoutInflater inflater;
    private ArrayList<JSONObject> data = new ArrayList<>();
    private OnItemClickListener listener;

    @Override
    public blackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new blackHolder(inflater.inflate(R.layout.activity_main, null));
    }

    public blackAdapter (Context context) {

    }

    public void setOnItemClick(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<JSONObject> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(blackHolder holder, int position) {
        holder.position = position;
        try {
            holder.imageView.setTag(data.get(position).getString("url"));
            if (holder.imageView.getTag().equals(data.get(position).getString("url"))) {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data != null && data.size() > 0 ? data.size() : 0;
    }

    class blackHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private int position;
        public blackHolder(View itemView) {
            super(itemView);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageView:
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                    break;
            }
        }
    }
}


