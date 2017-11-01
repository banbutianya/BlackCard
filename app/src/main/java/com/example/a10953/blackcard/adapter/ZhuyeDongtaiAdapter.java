package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.nfc.Tag;
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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 10953 on 2017/10/30.
 */

public class ZhuyeDongtaiAdapter extends RecyclerView.Adapter<ZhuyeDongtaiAdapter.ViewHolder>{

    private String TAG = "ZhuyeDongtaiAdapter";
    private ArrayList<JSONObject> findlist = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    private int scannum;
    private Context context;
    private String user_nick;
    private String user_upimg_url;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView user_upimg;
        TextView user_nick;
        TextView content;
        TextView praisenum;
        TextView location;
        ImageView image6;
        LinearLayout linearlayout_5;
        TextView scannumtext;

        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView image4;
        ImageView image5;
        public ViewHolder(View itemView) {
            super(itemView);

            user_upimg = (ImageView) itemView.findViewById(R.id.user_upimg);
            user_nick = (TextView) itemView.findViewById(R.id.user_nick);
            content = (TextView) itemView.findViewById(R.id.content);
            praisenum = (TextView) itemView.findViewById(R.id.praisenum);

            location = (TextView) itemView.findViewById(R.id.location);
            image6 = (ImageView) itemView.findViewById(R.id.image6);
            linearlayout_5 = (LinearLayout) itemView.findViewById(R.id.linearlayout_5);
            scannumtext = (TextView) itemView.findViewById(R.id.scannumtext);

            image1 = (ImageView) itemView.findViewById(R.id.image1);
            image2 = (ImageView) itemView.findViewById(R.id.image2);
            image3 = (ImageView) itemView.findViewById(R.id.image3);
            image4 = (ImageView) itemView.findViewById(R.id.image4);
            image5 = (ImageView) itemView.findViewById(R.id.image5);
        }
    }


    public void setData(ArrayList<JSONObject> findlist,Context context,String user_upimg_url, String user_nick){
        this.findlist = findlist;
        this.context = context;
        this.user_nick = user_nick;
        this.user_upimg_url = user_upimg_url;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_black_club_fragment_zhuye_dongtai_item, parent, false);
        return new ViewHolder(view);
    }



    public void onBindViewHolder(ViewHolder holder, int position) {

        //获取屏幕宽度
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;


        //添加具体数据
        try {
            if(findlist != null) {
                holder.user_nick.setText(user_nick);
                holder.content.setText(findlist.get(position).getString("content"));

                //判断地点为空不为空，为空的话继续隐藏，不为空的话显示图片和位置
                if(TextUtils.isEmpty(findlist.get(position).getString("location"))){
                    holder.location.setVisibility(View.GONE);
                    holder.image6.setVisibility(View.GONE);
                }else {
                    holder.location.setText(findlist.get(position).getString("location"));
                }

                Log.e(TAG,"content的数据为：" + findlist.get(position).getString("content"));
                //用Picasso会出错
                Glide.with(context)
                        .load(user_upimg_url)
                        .transform(new GlideCircleTransform(context))
                        .into(holder.user_upimg);

                if(TextUtils.isEmpty(findlist.get(position).getString("praisenum"))){
                    holder.praisenum.setText("0");
                }else {
                    holder.praisenum.setText(findlist.get(position).getString("praisenum"));
                }



                Pattern pattern = Pattern.compile("(http.+?\\.jpg)");
                Matcher matcher = pattern.matcher(findlist.get(position).getString("image_urls"));
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
            //全都隐藏
            holder.image1.setVisibility(View.GONE);
            holder.image2.setVisibility(View.GONE);
            holder.image3.setVisibility(View.GONE);
            holder.image4.setVisibility(View.GONE);
            holder.image5.setVisibility(View.GONE);
            holder.scannumtext.setVisibility(View.GONE);
        }else if(scannum == 1){
            holder.image1.setVisibility(View.VISIBLE);
            //隐藏图片
            holder.image2.setVisibility(View.GONE);
            holder.image3.setVisibility(View.GONE);
            holder.image4.setVisibility(View.GONE);
            holder.image5.setVisibility(View.GONE);
            //隐藏图片总数
            holder.scannumtext.setVisibility(View.GONE);

            Picasso.with(context)
                    .load(urlList.get(0))
                    .centerCrop()
                    .resize(width, width)
                    .into(holder.image1);
            Log.e(TAG,"图片的URL为：" + urlList.get(0));

            urlList.clear();
        }else if(scannum == 2){
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            //隐藏图片
            holder.image3.setVisibility(View.GONE);
            holder.image4.setVisibility(View.GONE);
            holder.image5.setVisibility(View.GONE);
            //隐藏图片总数
            holder.scannumtext.setVisibility(View.GONE);

            Picasso.with(context)
                    .load(urlList.get(0))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image1);
            Picasso.with(context)
                    .load(urlList.get(1))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image2);
            Log.e(TAG,"图片的URL为：" + urlList.get(0));
            Log.e(TAG,"图片的URL为：" + urlList.get(1));

            urlList.clear();
        }else if(scannum == 3){
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image3.setVisibility(View.VISIBLE);
            //隐藏图片
            holder.image4.setVisibility(View.GONE);
            holder.image5.setVisibility(View.GONE);
            //隐藏图片总数
            holder.scannumtext.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(urlList.get(0))
                    .centerCrop()
                    .resize((width - 90) / 3, (width - 90) / 3)
                    .into(holder.image1);
            Picasso.with(context)
                    .load(urlList.get(1))
                    .centerCrop()
                    .resize((width - 90) / 3, (width - 90) / 3)
                    .into(holder.image2);
            Picasso.with(context)
                    .load(urlList.get(2))
                    .centerCrop()
                    .resize((width - 90) / 3, (width - 90) / 3)
                    .into(holder.image3);
            Log.e(TAG,"图片的URL为：" + urlList.get(0));
            Log.e(TAG,"图片的URL为：" + urlList.get(1));
            Log.e(TAG,"图片的URL为：" + urlList.get(2));

            urlList.clear();
        }else if(scannum == 4){
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image4.setVisibility(View.VISIBLE);
            holder.image5.setVisibility(View.VISIBLE);

            //显示四张图片，第三张图片隐藏
            holder.image3.setVisibility(View.GONE);
            //图片总数隐藏
            holder.scannumtext.setVisibility(View.GONE);

            Picasso.with(context)
                    .load(urlList.get(0))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image1);
            Picasso.with(context)
                    .load(urlList.get(1))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image2);
            Picasso.with(context)
                    .load(urlList.get(2))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image4);
            Picasso.with(context)
                    .load(urlList.get(3))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image5);
            Log.e(TAG,"图片的URL为：" + urlList.get(0));
            Log.e(TAG,"图片的URL为：" + urlList.get(1));
            Log.e(TAG,"图片的URL为：" + urlList.get(2));
            Log.e(TAG,"图片的URL为：" + urlList.get(3));

            urlList.clear();
        }else if(scannum >= 5){
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image3.setVisibility(View.VISIBLE);
            holder.image4.setVisibility(View.VISIBLE);
            holder.image5.setVisibility(View.VISIBLE);
            holder.scannumtext.setVisibility(View.VISIBLE);

            holder.scannumtext.setText(scannum + "张图");

            Picasso.with(context)
                    .load(urlList.get(0))
                    .centerCrop()
                    .resize((width - 90) / 3, (width - 90) / 3)
                    .into(holder.image1);
            Picasso.with(context)
                    .load(urlList.get(1))
                    .centerCrop()
                    .resize((width - 90) / 3, (width - 90) / 3)
                    .into(holder.image2);
            Picasso.with(context)
                    .load(urlList.get(2))
                    .centerCrop()
                    .resize((width - 90) / 3, (width - 90) / 3)
                    .into(holder.image3);
            Picasso.with(context)
                    .load(urlList.get(3))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image4);
            Picasso.with(context)
                    .load(urlList.get(4))
                    .centerCrop()
                    .resize((width - 80) / 2, (width - 80) / 2)
                    .into(holder.image5);
            Log.e(TAG,"图片的URL为：" + urlList.get(0));
            Log.e(TAG,"图片的URL为：" + urlList.get(1));
            Log.e(TAG,"图片的URL为：" + urlList.get(2));
            Log.e(TAG,"图片的URL为：" + urlList.get(3));
            Log.e(TAG,"图片的URL为：" + urlList.get(4));

            urlList.clear();
        }


    }

    @Override
    public int getItemCount() {
        return findlist != null && findlist.size() > 0 ? findlist.size() : 0;
    }




}
