package com.example.a10953.blackcard.Util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 10953 on 2017/10/13.
 */


public class MaopaoUtil {

    //从小到大
    ArrayList<JSONObject> maopaominTest(ArrayList<JSONObject> list){
        JSONObject temp = null;
        int value1 = 0;
        int value2 = 0;
        for(int i=0;i<list.size()-1;i++){
            for(int j=0;j<list.size()-1-i;j++){
                try {
                    //key，取得那个字段q
                    if (list.get(j).getString("key") != null && list.get(j).getString("key").length() > 0
                            && list.get(j+1).getString("key") != null && list.get(j+1).getString("key").length() > 0 ) {
                        value1 = Integer.parseInt(list.get(j).getString("key"));
                        value2 = Integer.parseInt(list.get(j+1).getString("key"));
                        if(value1 > value2){
                            temp = list.get(j);
                            //list.get(j) = list.get(j+1);
                            list.set(j,list.get(j+1));
                            list.set(j+1,temp);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //从小到大
    int[] maopaoMin(int[] a){
        int temp=0;
        for(int i=0;i<a.length-1;i++){
            for(int j=0;j<a.length-1-i;j++){
                if(a[j]>a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        return a;
    }

    //从大到小
    int[] maopaoMax(int[] a){
        int temp=0;
        for(int i=0;i<a.length-1;i++){
            for(int j=0;j<a.length-1-i;j++){
                if(a[j]<a[j+1]){
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        return a;
    }
}
