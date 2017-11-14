package com.example.a10953.blackcard.activity.Club;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10953.blackcard.R;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private TextView posted;
    private EditText edit;
    private ImageView jiahao;
    private RelativeLayout activity_edit;
    private LinearLayout linearlayout_1;
    private LinearLayout linearlayout_2;
    private LinearLayout linearlayout_3;
    private LinearLayout linearlayout_4;
    private TextView allPeople;
    private TextView myself;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();

        back.setOnClickListener(this);
        posted.setOnClickListener(this);
        jiahao.setOnClickListener(this);
        activity_edit.setOnClickListener(this);
        linearlayout_1.setOnClickListener(this);
        linearlayout_2.setOnClickListener(this);
        linearlayout_3.setOnClickListener(this);

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        posted = (TextView) findViewById(R.id.posted);
        edit = (EditText) findViewById(R.id.edit);
        jiahao = (ImageView) findViewById(R.id.jiahao);
        activity_edit = (RelativeLayout) findViewById(R.id.activity_edit);
        linearlayout_1 = (LinearLayout) findViewById(R.id.linearlayout_1);
        linearlayout_2 = (LinearLayout) findViewById(R.id.linearlayout_2);
        linearlayout_3 = (LinearLayout) findViewById(R.id.linearlayout_3);
        linearlayout_4 = (LinearLayout) findViewById(R.id.linearlayout_4);
        allPeople = (TextView) findViewById(R.id.allPeople);
        myself = (TextView) findViewById(R.id.myself);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                Toast.makeText(this,"点击了返回按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.posted:
                Toast.makeText(this,"点击了发布按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.jiahao:
                Toast.makeText(this,"点击了加号（添加照片或者小视频）",Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_edit:
                Toast.makeText(this,"点击了Activity_edit",Toast.LENGTH_SHORT).show();
                linearlayout_4.setVisibility(View.GONE);
                break;
            case R.id.linearlayout_1:
                Toast.makeText(this,"调整地理位置",Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearlayout_2:
                Toast.makeText(this,"添加话题",Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearlayout_3:
                Toast.makeText(this,"调整对谁可见", Toast.LENGTH_SHORT).show();
                linearlayout_4.setVisibility(View.VISIBLE);
                activity_edit.setBackgroundColor(Color.parseColor("#F4F4F4"));
                break;
        }

    }
}
