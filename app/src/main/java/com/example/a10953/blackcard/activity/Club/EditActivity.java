package com.example.a10953.blackcard.activity.Club;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.a10953.blackcard.Listener.HttpCallBackListener;
import com.example.a10953.blackcard.MyApplication;
import com.example.a10953.blackcard.R;
import com.example.a10953.blackcard.Util.HttpUtil;
import com.example.a10953.blackcard.activity.Club.shootvideo.TCVideoRecordActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "EditActivity";
    private String url = "http://api.qing-hei.com/index.php/Index/Api?type=publish_find";

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int PAISHE_WINDOW = 3;

    private ImageView back;
    private TextView posted;
    private EditText edit;
    private ImageView jiahao;
    private RelativeLayout activity_edit,relativelayout_2,relativelayout_3,relativelayout_4,relativelayout_5;
    private LinearLayout linearlayout_1;
    private View gray_layout;
    private TextView whom_visible,allPeople,myself,dizhi_show,huati_show;

    //话题列表
    private TextView gaoxiaoquwen,yingpingzhixiao,titanyaowen,shupingkanpin,qinghuadashi,qingheiriqian,qinzhishenghuo;

    //发布用到的控件
    private RelativeLayout relativelayout_6;
    private TextView paisheshipin,paishezhaopian,xiangce,cancel;

    //拍摄之后现实的缩略图
    private ImageView showphoto;
    private String edit_text;
    private Uri imageUrl;
    private boolean isPhoto = true;

    //小视频缩略图
    private String mSmallVideo_ImagepPath;
    private String mSmallVideo_Path;
    private String mSmallVideoUrl;
    private String mLoadSmallVideoUrl;
    private String mSmallVideo_ImageUrl;
    private String mLoadSmallVideo_ImageUrl;

    //上传用到的参数
    private String uid;
    private String token;
    private String content;
    private String images;//小图
    private String imageb;//大图
    private String location;
    private String video_url;
    private String qx;
    private String to_id;

    //返回的参数
    private String find_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        initData();
        back.setOnClickListener(this);
        posted.setOnClickListener(this);
        jiahao.setOnClickListener(this);
        relativelayout_2.setOnClickListener(this);
        relativelayout_3.setOnClickListener(this);
        relativelayout_4.setOnClickListener(this);
        gray_layout.setOnClickListener(this);
        allPeople.setOnClickListener(this);
        myself.setOnClickListener(this);

        gaoxiaoquwen.setOnClickListener(this);
        yingpingzhixiao.setOnClickListener(this);
        titanyaowen.setOnClickListener(this);
        shupingkanpin.setOnClickListener(this);
        qinghuadashi.setOnClickListener(this);
        qingheiriqian.setOnClickListener(this);
        qinzhishenghuo.setOnClickListener(this);

        paisheshipin.setOnClickListener(this);
        paishezhaopian.setOnClickListener(this);
        xiangce.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    private void initData() {
        Intent i = getIntent();
        uid = i.getStringExtra("uid");
        token = i.getStringExtra("token");

        Log.e(TAG,"uid 是" + uid);
        Log.e(TAG,"token 是" + token);
        content = "";
        images = "";
        imageb = "";
        location = "";
        video_url = "";
        qx = "1";
        to_id = "0";

    }


    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        posted = (TextView) findViewById(R.id.posted);
        edit = (EditText) findViewById(R.id.edit);
        jiahao = (ImageView) findViewById(R.id.jiahao);
        activity_edit = (RelativeLayout) findViewById(R.id.activity_edit);
        relativelayout_2 = (RelativeLayout) findViewById(R.id.relativelayout_2);
        relativelayout_3 = (RelativeLayout) findViewById(R.id.relativelayout_3);
        relativelayout_4 = (RelativeLayout) findViewById(R.id.relativelayout_4);
        relativelayout_5 = (RelativeLayout) findViewById(R.id.relativeLayout_5);
        linearlayout_1 = (LinearLayout) findViewById(R.id.linearlayout_1);
        gray_layout = findViewById(R.id.gray_layout);
        whom_visible = (TextView) findViewById(R.id.whom_visible);
        allPeople = (TextView) findViewById(R.id.allPeople);
        myself = (TextView) findViewById(R.id.myself);
        dizhi_show = (TextView) findViewById(R.id.dizhi_show);
        huati_show = (TextView) findViewById(R.id.huati_show);

        gaoxiaoquwen = (TextView) findViewById(R.id.gaoxiaoquwen);
        yingpingzhixiao = (TextView) findViewById(R.id.yingpingzhixiao);
        titanyaowen = (TextView) findViewById(R.id.titanyaowen);
        shupingkanpin = (TextView) findViewById(R.id.shupingkanpin);
        qinghuadashi = (TextView) findViewById(R.id.qinghuadashi);
        qingheiriqian = (TextView) findViewById(R.id.qingheiriqian);
        qinzhishenghuo = (TextView) findViewById(R.id.pinzhishenghuo);

        relativelayout_6 = (RelativeLayout) findViewById(R.id.relativelayout_6);
        paisheshipin = (TextView) findViewById(R.id.paisheshipin);
        paishezhaopian = (TextView) findViewById(R.id.paishezhaopian);
        xiangce = (TextView) findViewById(R.id.xiangce);
        cancel = (TextView) findViewById(R.id.cancel);

        showphoto = (ImageView) findViewById(R.id.showphoto);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                Toast.makeText(this,"点击了返回按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.posted:
                edit_text = edit.getText().toString();
                content = edit_text;
                if(TextUtils.isEmpty(edit_text) && isPhoto){
                    Toast.makeText(this,"请输入分享内容或上传照片",Toast.LENGTH_SHORT).show();
                }else {
                    upLoadFile_OSS();
                    upLoadFile_BlackCard();
                }
                break;
            case R.id.jiahao:
                tianjiazhaopianInto();
                break;
            case R.id.relativelayout_2:
                Toast.makeText(this,"调整地理位置",Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativelayout_3:
                tianjiahuatiInto();
                break;
            case R.id.relativelayout_4:
                quanxianshezhiInto();
                break;
            case R.id.gray_layout:
                quanxianshezhiOut();
                tianjiahuatiOut();
                tianjiazhaopianOut();
                break;
            case R.id.allPeople:
                whom_visible.setText("所有人可见");
                qx = "0";
                quanxianshezhiOut();
                break;
            case R.id.myself:
                whom_visible.setText("仅自己可见");
                qx = "1";
                quanxianshezhiOut();
                break;
            case R.id.gaoxiaoquwen:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#搞笑趣闻#");
                to_id = "0";
                tianjiahuatiOut();
                break;
            case R.id.yingpingzhixiao:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#影评知晓#");
                to_id = "1";
                tianjiahuatiOut();
                break;
            case R.id.titanyaowen:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#体坛要闻#");
                to_id = "2";
                tianjiahuatiOut();
                break;
            case R.id.shupingkanpin:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#书评看品#");
                to_id = "3";
                tianjiahuatiOut();
                break;
            case R.id.qinghuadashi:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#情话大师#");
                to_id = "4";
                tianjiahuatiOut();
                break;
            case R.id.qingheiriqian:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#青黑日签#");
                to_id = "5";
                tianjiahuatiOut();
                break;
            case R.id.pinzhishenghuo:
                huati_show.setVisibility(View.VISIBLE);
                huati_show.setText("#品质生活#");
                to_id = "6";
                tianjiahuatiOut();
                break;
            case R.id.paisheshipin:
                tianjiazhaopianOut();
                Intent intent = new Intent(EditActivity.this, TCVideoRecordActivity.class);
                startActivityForResult(intent,PAISHE_WINDOW);
                break;
            case R.id.paishezhaopian:
                tianjiazhaopianOut();
                sttartPaisheZhaopian();
                break;
            case R.id.xiangce:
                Toast.makeText(this,"从相册选择",Toast.LENGTH_SHORT).show();
                if(ContextCompat.checkSelfPermission(EditActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(EditActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                break;
            case R.id.cancel:
                Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show();
                tianjiazhaopianOut();
                break;
            default:
                break;
        }

    }

    private void upLoadFile_BlackCard() {
        HashMap<String, String> map = new HashMap<>();
        //post参数，UID是会员ID，token会变，通过Intent传递过来的
        map.put("uid", uid);
        map.put("token", token);
        map.put("content", content);
        map.put("images", images);
        map.put("imageb", imageb);
        map.put("location", location);
        map.put("video_url", video_url);
        map.put("qx", qx);
        map.put("to_id", to_id);



        HttpUtil.sendStringRequestByPost(url, map, new HttpCallBackListener() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    find_id = data.getString("find_id");
                    Log.e(TAG,"find_id 为： " + find_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(VolleyError volleyError) {
                Log.e(TAG,"网络请求失败");
            }
        });
    }


    public void quanxianshezhiInto(){
        linearlayout_1.setVisibility(View.VISIBLE);
        gray_layout.setVisibility(View.VISIBLE);
        gray_layout.setAlpha(0f);
        gray_layout.animate().alpha(1f).setDuration(1000).setListener(null);
        linearlayout_1.setAlpha(0f);
        linearlayout_1.animate().alpha(1f).setDuration(1000).setListener(null);

    }

    public void quanxianshezhiOut(){
        gray_layout.animate().alpha(0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gray_layout.setVisibility(View.GONE);
            }
        });
        linearlayout_1.animate().alpha(0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                linearlayout_1.setVisibility(View.GONE);
            }
        });
    }

    public void tianjiahuatiInto(){
        relativelayout_5.setVisibility(View.VISIBLE);
        gray_layout.setVisibility(View.VISIBLE);
        gray_layout.setAlpha(0f);
        gray_layout.animate().alpha(1f).setDuration(1000).setListener(null);
        relativelayout_5.setAlpha(0f);
        relativelayout_5.animate().alpha(1f).setDuration(1000).setListener(null);
    }

    public void tianjiahuatiOut(){
        gray_layout.animate().alpha(0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gray_layout.setVisibility(View.GONE);
            }
        });

        relativelayout_5.animate().alpha(0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                relativelayout_5.setVisibility(View.GONE);
            }
        });
    }

    public void tianjiazhaopianInto(){
        relativelayout_6.setVisibility(View.VISIBLE);
        gray_layout.setVisibility(View.VISIBLE);
        gray_layout.setAlpha(0f);
        gray_layout.animate().alpha(1f).setDuration(1000).setListener(null);
        relativelayout_6.setAlpha(0f);
        relativelayout_6.animate().alpha(1f).setDuration(1000).setListener(null);
    }

    public void tianjiazhaopianOut(){
        gray_layout.animate().alpha(0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gray_layout.setVisibility(View.GONE);
            }
        });

        relativelayout_6.animate().alpha(0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                relativelayout_6.setVisibility(View.GONE);
            }
        });
    }

    public void sttartPaisheZhaopian(){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        File outputImage = new File(getExternalCacheDir(),"BLACK_CARD_" + time + ".jpg");
        try{
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUrl = FileProvider.getUriForFile(EditActivity.this,"com.example.a10953.blackcard.activity.Club",outputImage);
        }else {
            imageUrl = Uri.fromFile(outputImage);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    public void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap;
                        long currentTime = System.currentTimeMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
                        String time = sdf.format(new Date(currentTime));

                        FileOutputStream b = null;
                        File file = new File("/sdcard/DCIM/BlackCard/");
                        file.mkdirs();// 创建文件夹
                        String fileName = "/sdcard/DCIM/BlackCard/" + "BLACK_CARD_" + time + ".jpg";

                        //存入文件
                        try {
                            b = new FileOutputStream(fileName);
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                b.flush();
                                b.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //把拍摄的照片显示出来
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                        jiahao.setVisibility(View.INVISIBLE);
                        showphoto.setVisibility(View.VISIBLE);
                        isPhoto = false;

                        showphoto.setImageBitmap(bitmap);
                        relativelayout_6.setVisibility(View.GONE);
                        gray_layout.setVisibility(View.GONE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(EditActivity.this, "拍照取消", Toast.LENGTH_SHORT).show();
                }

                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case PAISHE_WINDOW:
                mSmallVideo_ImagepPath = data.getStringExtra("imagePath");
                mSmallVideo_Path = data.getStringExtra("videoPath");
                Log.e(TAG,"封面路径为：" + mSmallVideo_ImagepPath + "视频路径为：" + mSmallVideo_Path);
                jiahao.setVisibility(View.INVISIBLE);
                showphoto.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(mSmallVideo_ImagepPath)
                        .centerCrop()
                        .into(showphoto);
                isPhoto = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            jiahao.setVisibility(View.INVISIBLE);
            showphoto.setVisibility(View.VISIBLE);
            showphoto.setImageBitmap(bitmap);
            relativelayout_6.setVisibility(View.GONE);
            gray_layout.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }


    private void upLoadFile_OSS() {
        mSmallVideoUrl = "BlackCard/SmallVideo/Video/" + getObjectKey("Black_card") + ".mp4";
        //上传以后，外网获取的URL地址
        mLoadSmallVideoUrl = MyApplication.OSS_BUCKET + ".oss-cn-beijing.aliyuncs.com/" + mSmallVideoUrl;
        Log.e(TAG,mLoadSmallVideoUrl);
        PutObjectRequest videoPut = new PutObjectRequest(MyApplication.OSS_BUCKET,mSmallVideoUrl,mSmallVideo_Path);

        // 异步上传时可以设置进度回调
        videoPut.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if(currentSize/totalSize == 1) {
                    Log.e(TAG, "视频上传完成");
                }
            }
        });

        MyApplication.oss.asyncPutObject(videoPut, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.e("PutObject", "UploadSuccess");
                Log.e("ETag", result.getETag());
                Log.e("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }

            }
        });
        video_url = mLoadSmallVideoUrl;


        //imageUrl = "BlackCard/SmallVideo/Cover sheet/" + getObjectKey("Black_card") + ".jpg";
        mSmallVideo_ImageUrl = mSmallVideoUrl.replace(".mp4",".jpg");
        mLoadSmallVideo_ImageUrl = MyApplication.OSS_BUCKET + ".oss-cn-beijing.aliyuncs.com/" + mSmallVideo_ImageUrl;
        Log.e(TAG,mLoadSmallVideo_ImageUrl);
        PutObjectRequest imagePut = new PutObjectRequest(MyApplication.OSS_BUCKET, mSmallVideo_ImageUrl, mSmallVideo_ImagepPath);

        // 异步上传时可以设置进度回调
        imagePut.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if(currentSize/totalSize == 1) {
                    Log.e(TAG, "图片上传完成");
                }
            }
        });

        MyApplication.oss.asyncPutObject(imagePut, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.e("PutObject", "UploadSuccess");
                Log.e("ETag", result.getETag());
                Log.e("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    //通过UserCode 加上日期组装 OSS路径
    private String getObjectKey(String strUserCode){
        Date date = new Date();
        return new SimpleDateFormat("yyyy-M-d").format(date)+"/"+strUserCode+new SimpleDateFormat("yyyyMMddssSSS").format(date);
    }

}
