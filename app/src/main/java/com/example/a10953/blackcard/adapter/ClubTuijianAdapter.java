package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.GlideCircleTransform;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by 10953 on 2017/10/16.
 */

public class ClubTuijianAdapter extends RecyclerView.Adapter{

    private String TAG = "ClubTuijianAdapter";
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    //登录ID
    private String uid;
    //登录Token
    private String token;
    //访问网络的URL
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=newfind";
    private int page = 1;

    private Context context;

    //图片总数
    private int scannum;

    private String name_text;
    private String temperature_text;
    private String text_text;
    private String user_nick_text;
    private String user_upimg_text;

    private ArrayList<JSONObject> myflowlist = new ArrayList<>();
    private ArrayList<JSONObject> creamlist = new ArrayList<>();

    private ArrayList<Map<String,Object>> mapList = new ArrayList<>();

    private ArrayList<JSONObject> headDates = new ArrayList<>();

    private ArrayList<String> urlList=new ArrayList<>();

    private View mHeaderView;

    //显示图片的ViewPager
    private ViewPager viewPager;
    //ViewPager下方的小圆点
    private LinearLayout pointGroup;


    public void setContext(Context context){
        this.context = context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        //notifyItemInserted(int position);列表position位置添加一条数据时可以调用，有动画效果？？？
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeadDates(ArrayList<JSONObject> headDates){
        try {
            name_text = headDates.get(1).getJSONObject("location").getString("name");
            temperature_text = headDates.get(1).getJSONObject("now").getString("temperature");
            text_text = headDates.get(1).getJSONObject("now").getString("text");
            user_nick_text = headDates.get(0).getString("user_nick");
            user_upimg_text = headDates.get(0).getString("user_upimg");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void setDate(ArrayList<JSONObject> creamlist, String uid, String token) {
        this.creamlist = creamlist;
        this.uid = uid;
        this.token = token;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if(position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolders(mHeaderView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_black_club_fragment_tuijian_item, parent, false);
        return new ViewHolders_test(layout);
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? creamlist.size() : creamlist.size() + 1;
    }

    class ViewHolders extends RecyclerView.ViewHolder {
        //地点
        TextView name;
        //温度
        TextView temperature;
        //天气情况，（雾）
        TextView text;
        //用户名
        TextView user_nick;
        //头像
        ImageView user_upimg;
        //当前时间
        TextView now_date;

        public ViewHolders(View itemView) {
            super(itemView);
            if(itemView == mHeaderView){
                name = (TextView) itemView.findViewById(R.id.name);
                temperature = (TextView) itemView.findViewById(R.id.temperature);
                text = (TextView) itemView.findViewById(R.id.text);
                user_nick = (TextView) itemView.findViewById(R.id.user_nick);
                user_upimg = (ImageView)itemView.findViewById(R.id.user_upimg);
                now_date = (TextView) itemView.findViewById(R.id.now_date);
                return;
            }
        }
    }


    class ViewHolders_test extends RecyclerView.ViewHolder {
        ImageView head;
        TextView myflow_name;
        TextView content;
        TextView praisenum;
        ImageView levlevel_ico;
        TextView location;
        ImageView image6;
        LinearLayout linearlayout_5;
        TextView scannumtext;

        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView image4;
        ImageView image5;

        public ViewHolders_test(View itemView) {
            super(itemView);
            head = (ImageView) itemView.findViewById(R.id.head);
            myflow_name = (TextView) itemView.findViewById(R.id.myflow_name);
            content = (TextView) itemView.findViewById(R.id.content);
            praisenum = (TextView) itemView.findViewById(R.id.praisenum);
            levlevel_ico = (ImageView) itemView.findViewById(R.id.level_ico);
            location = (TextView) itemView.findViewById(R.id.location);
            image6 = (ImageView) itemView.findViewById(R.id.image6);
            linearlayout_5 = (LinearLayout) itemView.findViewById(R.id.linearlayout_5);
            scannumtext = (TextView) itemView.findViewById(R.id.scannumtext);

            //ViewPage和PointGroup绑定对应的资源ID
            viewPager = (ViewPager) itemView.findViewById(R.id.head_viewpager);
            pointGroup = (LinearLayout) itemView.findViewById(R.id.pointgroup_2);

            image1 = (ImageView) itemView.findViewById(R.id.image1);
            image2 = (ImageView) itemView.findViewById(R.id.image2);
            image3 = (ImageView) itemView.findViewById(R.id.image3);
            image4 = (ImageView) itemView.findViewById(R.id.image4);
            image5 = (ImageView) itemView.findViewById(R.id.image5);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position) == TYPE_HEADER){
            if (holder instanceof ViewHolders) {
                ViewHolders viewHolder = (ViewHolders) holder;
                viewHolder.name.setText(name_text);
                viewHolder.temperature.setText(temperature_text + "℃");
                viewHolder.text.setText(text_text);
                viewHolder.user_nick.setText(user_nick_text);

                Glide.with(context).load(user_upimg_text).into(viewHolder.user_upimg);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                StringBuffer date = new StringBuffer();
                date.append(sdf.format(new Date()));
                SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");

                String mWay = dateFm.format(new Date());
                Log.i(TAG,"今天是" + mWay);

                if("星期日".equals(mWay)){
                    mWay ="Sunday";
                }else if("星期一".equals(mWay)){
                    mWay ="Monday";
                }else if("星期二".equals(mWay)){
                    mWay ="Tuesday";
                }else if("星期三".equals(mWay)){
                    mWay ="Wednesday";
                }else if("星期四".equals(mWay)){
                    mWay ="Thursday";
                }else if("星期五".equals(mWay)){
                    mWay ="Friday";
                }else if("星期六".equals(mWay)){
                    mWay ="Saturday";
                }

                date.append("  "+ mWay);
                viewHolder.now_date.setText(date);
            }
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            if(holder instanceof ViewHolders_test){
                ViewHolders_test viewHolder = (ViewHolders_test) holder;

                //获取屏幕宽度
                Resources resources = context.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                float density = dm.density;
                int width = dm.widthPixels;
                int height = dm.heightPixels;

                final int pos = getRealPosition(holder);

                //添加具体数据
                try {
                    if(creamlist != null) {
                        viewHolder.myflow_name.setText(creamlist.get(pos).getString("name"));
                        viewHolder.content.setText(creamlist.get(pos).getString("content"));

                        //判断地点为空不为空，为空的话继续隐藏，不为空的话显示图片和位置
                        if(TextUtils.isEmpty(creamlist.get(pos).getString("location"))){
                            viewHolder.location.setVisibility(View.GONE);
                            viewHolder.image6.setVisibility(View.GONE);
                        }else {
                            viewHolder.location.setText(creamlist.get(pos).getString("location"));
                        }

                        Log.e(TAG,"用户名为：" + creamlist.get(pos).getString("name"));

                        Log.e(TAG,"content的数据为：" + creamlist.get(pos).getString("content"));
                        //用Picasso会出错
                        Glide.with(context)
                                .load(creamlist.get(pos).getString("head"))
                                .transform(new GlideCircleTransform(context))
                                .into(viewHolder.head);
                        Picasso.with(context)
                                .load(creamlist.get(pos).getString("level_ico"))
                                .into(viewHolder.levlevel_ico);
                        viewHolder.praisenum.setText(creamlist.get(pos).getString("praisenum"));

                        Pattern pattern = Pattern.compile("(http.+?\\.jpg)");
                        Matcher matcher = pattern.matcher(creamlist.get(pos).getString("image_urls"));
                        while (matcher.find()) {
                            urlList.add(matcher.group());
                        }
                        scannum = urlList.size();
                        Log.e(TAG,"scannum 大小为：" + scannum);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (scannum == 0) {
                    viewHolder.image1.setVisibility(View.GONE);
                    viewHolder.image2.setVisibility(View.GONE);
                    viewHolder.image3.setVisibility(View.GONE);
                    viewHolder.image4.setVisibility(View.GONE);
                    viewHolder.image5.setVisibility(View.GONE);
                    viewHolder.scannumtext.setVisibility(View.GONE);
                }else if(scannum == 1){
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.GONE);
                    viewHolder.image3.setVisibility(View.GONE);
                    viewHolder.image4.setVisibility(View.GONE);
                    viewHolder.image5.setVisibility(View.GONE);
                    viewHolder.scannumtext.setVisibility(View.GONE);

                    Picasso.with(context)
                            .load(urlList.get(0))
                            .centerCrop()
                            .resize(width, width)
                            .into(viewHolder.image1);
                    Log.e(TAG,"图片的URL为：" + urlList.get(0));

                    urlList.clear();
                }else if(scannum == 2){
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.GONE);
                    viewHolder.image4.setVisibility(View.GONE);
                    viewHolder.image5.setVisibility(View.GONE);
                    viewHolder.scannumtext.setVisibility(View.GONE);

                    Picasso.with(context)
                            .load(urlList.get(0))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image1);
                    Picasso.with(context)
                            .load(urlList.get(1))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image2);
                    Log.e(TAG,"图片的URL为：" + urlList.get(0));
                    Log.e(TAG,"图片的URL为：" + urlList.get(1));

                    urlList.clear();
                }else if(scannum == 3){
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.VISIBLE);
                    viewHolder.image4.setVisibility(View.GONE);
                    viewHolder.image5.setVisibility(View.GONE);
                    viewHolder.scannumtext.setVisibility(View.GONE);
                    Picasso.with(context)
                            .load(urlList.get(0))
                            .centerCrop()
                            .resize((width - 90) / 3, (width - 90) / 3)
                            .into(viewHolder.image1);
                    Picasso.with(context)
                            .load(urlList.get(1))
                            .centerCrop()
                            .resize((width - 90) / 3, (width - 90) / 3)
                            .into(viewHolder.image2);
                    Picasso.with(context)
                            .load(urlList.get(2))
                            .centerCrop()
                            .resize((width - 90) / 3, (width - 90) / 3)
                            .into(viewHolder.image3);
                    Log.e(TAG,"图片的URL为：" + urlList.get(0));
                    Log.e(TAG,"图片的URL为：" + urlList.get(1));
                    Log.e(TAG,"图片的URL为：" + urlList.get(2));

                    urlList.clear();
                }else if(scannum == 4){
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.VISIBLE);
                    viewHolder.image4.setVisibility(View.VISIBLE);
                    viewHolder.image5.setVisibility(View.GONE);
                    viewHolder.scannumtext.setVisibility(View.GONE);

                    Picasso.with(context)
                            .load(urlList.get(0))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image1);
                    Picasso.with(context)
                            .load(urlList.get(1))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image2);
                    Picasso.with(context)
                            .load(urlList.get(2))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image4);
                    Picasso.with(context)
                            .load(urlList.get(3))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image5);
                    Log.e(TAG,"图片的URL为：" + urlList.get(0));
                    Log.e(TAG,"图片的URL为：" + urlList.get(1));
                    Log.e(TAG,"图片的URL为：" + urlList.get(2));
                    Log.e(TAG,"图片的URL为：" + urlList.get(3));

                    urlList.clear();
                }else if(scannum >= 5){
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.VISIBLE);
                    viewHolder.image4.setVisibility(View.VISIBLE);
                    viewHolder.image5.setVisibility(View.VISIBLE);
                    viewHolder.scannumtext.setVisibility(View.VISIBLE);

                    viewHolder.scannumtext.setText(scannum + "张图");

                    Picasso.with(context)
                            .load(urlList.get(0))
                            .centerCrop()
                            .resize((width - 90) / 3, (width - 90) / 3)
                            .into(viewHolder.image1);
                    Picasso.with(context)
                            .load(urlList.get(1))
                            .centerCrop()
                            .resize((width - 90) / 3, (width - 90) / 3)
                            .into(viewHolder.image2);
                    Picasso.with(context)
                            .load(urlList.get(2))
                            .centerCrop()
                            .resize((width - 90) / 3, (width - 90) / 3)
                            .into(viewHolder.image3);
                    Picasso.with(context)
                            .load(urlList.get(3))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image4);
                    Picasso.with(context)
                            .load(urlList.get(4))
                            .centerCrop()
                            .resize((width - 80) / 2, (width - 80) / 2)
                            .into(viewHolder.image5);
                    Log.e(TAG,"图片的URL为：" + urlList.get(0));
                    Log.e(TAG,"图片的URL为：" + urlList.get(1));
                    Log.e(TAG,"图片的URL为：" + urlList.get(2));
                    Log.e(TAG,"图片的URL为：" + urlList.get(3));
                    Log.e(TAG,"图片的URL为：" + urlList.get(4));

                    urlList.clear();
                }

            }

        }


    }
}
