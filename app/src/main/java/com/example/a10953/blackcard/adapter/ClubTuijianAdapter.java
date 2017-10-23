package com.example.a10953.blackcard.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.RoundTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 10953 on 2017/10/16.
 */

public class ClubTuijianAdapter extends RecyclerView.Adapter{

    private String TAG = "ClubTuijianAdapter";
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private Context context;

    //图片总数
    private int scannum;

    private String name_text;
    private String temperature_text;
    private String text_text;
    private String user_nick_text;
    private String user_upimg_text;

    private List<JSONObject> myflowlist = new ArrayList<>();

    private ArrayList<JSONObject> headDates = new ArrayList<>();

    private List<String> urlList=new ArrayList<>();

    private View mHeaderView;


    public void setContext(Context context){
        this.context = context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
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

    public void setDate(List<JSONObject> myflowlist) {
        this.myflowlist = myflowlist;
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
        return mHeaderView == null ? myflowlist.size() : myflowlist.size() + 1;
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
                viewHolder.temperature.setText(temperature_text);
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

                    viewHolder.myflow_name.setText(myflowlist.get(pos).getString("name"));
                    viewHolder.content.setText(myflowlist.get(pos).getString("content"));
                    Picasso.with(context)
                            .load(myflowlist.get(pos).getString("head"))
                            .transform(new RoundTransform())
                            .into(viewHolder.head);
                    viewHolder.praisenum.setText(myflowlist.get(pos).getString("praisenum"));

                    Pattern pattern = Pattern.compile("(http.+?\\.jpg)");
                    Matcher matcher = pattern.matcher(myflowlist.get(pos).getString("image_urls"));
                    while(matcher.find()) {
                        urlList.add(matcher.group());
                    }
                    scannum = urlList.size();

                    Log.i(TAG,"图片数量为" + scannum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (scannum == 0) {
                    return;
                }else if(scannum == 1){
                    Glide.with(context)
                            .load(urlList.get(0))
                            .override(width,width)
                            .centerCrop()
                            .into(viewHolder.image1);
                }else if (scannum == 2){
                    Picasso.with(context)
                            .load(urlList.get(0))
                            .resize((width-80)/2, (width-80)/2)
                            .into(viewHolder.image1);

                    Picasso.with(context)
                            .load(urlList.get(1))
                            .resize((width-80)/2, (width-80)/2)
                            .into(viewHolder.image2);
                }else if(scannum == 3){
                    Picasso.with(context)
                            .load(urlList.get(0))
                            .resize((width-90)/3, (width-90)/3)
                            .into(viewHolder.image1);

                    Picasso.with(context)
                            .load(urlList.get(1))
                            .resize((width-90)/3, (width-90)/3)
                            .into(viewHolder.image2);

                    Picasso.with(context)
                            .load(urlList.get(2))
                            .resize((width-90)/3, (width-90)/3)
                            .into(viewHolder.image3);
                }else if(scannum == 4){
                    Picasso.with(context)
                            .load(urlList.get(0))
                            .resize((width-80)/2, (width-80)/2)
                            .into(viewHolder.image1);

                    Picasso.with(context)
                            .load(urlList.get(1))
                            .resize((width-80)/2, (width - 80)/2)
                            .into(viewHolder.image2);
                    Picasso.with(context)
                            .load(urlList.get(2))
                            .resize((width-80)/2, (width-80)/2)
                            .into(viewHolder.image4);

                    Picasso.with(context)
                            .load(urlList.get(3))
                            .resize((width-80)/2, (width - 80)/2)
                            .into(viewHolder.image5);
                }else if(scannum >= 5){
                    Picasso.with(context)
                            .load(urlList.get(0))
                            .resize((width-90)/3, (width-90)/3)
                            .into(viewHolder.image1);

                    Picasso.with(context)
                            .load(urlList.get(1))
                            .resize((width-90)/3, (width-90)/3)
                            .into(viewHolder.image2);

                    Picasso.with(context)
                            .load(urlList.get(2))
                            .resize((width-90)/3, (width-90)/3)
                            .into(viewHolder.image3);
                    Picasso.with(context)
                            .load(urlList.get(3))
                            .resize((width-80)/2, (width-80)/2)
                            .into(viewHolder.image4);

                    Picasso.with(context)
                            .load(urlList.get(4))
                            .resize((width-80)/2, (width - 80)/2)
                            .into(viewHolder.image5);

                }
                urlList.clear();

            }

        }
    }
}
