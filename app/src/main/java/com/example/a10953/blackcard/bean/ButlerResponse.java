package com.example.a10953.blackcard.bean;

import java.util.List;
import java.util.UUID;

/**
 * Created by 10953 on 2017/10/10.
 */

public class ButlerResponse {

    private String butler_item_text;
    private String imageViewUrl;
//    private UUID mId;
//    private Integer imageView;
//    public ButlerResponse(){
//        mId = UUID.randomUUID();
//    }

    public String getButler_item_text(){
        return butler_item_text;
    }

    public String setButler_item_text(String butler_item_text){
        return this.butler_item_text = butler_item_text;
    }

    public String getImageViewUrl() {
        return imageViewUrl;
    }

    public void setImageViewUrl(String imageViewUrl) {
        this.imageViewUrl = imageViewUrl;
    }


}
