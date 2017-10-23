package com.example.a10953.blackcard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a10953.blackcard.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 10953 on 2017/10/10.
 */

public class BlackCardActivity extends Activity implements OnItemClickListener {
    private RecyclerView RecyclerView;
    private GridLayoutManager manager;
    private blackAdapter adapter;
    private ArrayList<JSONObject> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_black_card);
        manager = new GridLayoutManager(this, 2, 2, false);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView.setLayoutManager(manager);
        adapter = new blackAdapter(this);
        //请求过程 data=?
        adapter.setData(data);
        adapter.setOnItemClick(this);
    }

    @Override
    public void onItemClick(int postion) {

    }
}
