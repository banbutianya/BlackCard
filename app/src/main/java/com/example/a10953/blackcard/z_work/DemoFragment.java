package com.example.a10953.blackcard.z_work;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 10953 on 2017/10/19.
 */

public class DemoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //返回视图，返回哪个View，不要进行耗时操作
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //同样不要进项耗时操作，进项一些加载布局的操作
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //可以进行一些稍稍费时的操作，加载数据和点击事件在这个里面进行
        super.onActivityCreated(savedInstanceState);
        initData();
        initClick();
    }

    private void initClick() {
    }

    private void initData() {
    }
}
