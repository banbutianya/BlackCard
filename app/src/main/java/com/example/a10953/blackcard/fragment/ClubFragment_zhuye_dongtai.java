package com.example.a10953.blackcard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.adapter.ClubTuijianAdapter;

/**
 * Created by 10953 on 2017/10/26.
 */

public class ClubFragment_zhuye_dongtai extends Fragment{

    private RecyclerView recycler_view_dongtai;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_black_club_fragment_zhuye_dongtai, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        recycler_view_dongtai = (RecyclerView) view.findViewById(R.id.recycler_view_dongtai);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
    }
}
