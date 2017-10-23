package com.example.a10953.blackcard.bean;

import org.json.JSONObject;

/**
 * Created by 10953 on 2017/10/10.
 */

public class LoginResponse{

    /**
     * result : 1
     * msg :
     * data : {"uid":"26269","token":"69a8100b0103727009f59252559a7e6d","is_bind":"1","user_tel":"18698717416"}
     */

    private int result;
    private String msg;
    private DataBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 26269
         * token : 69a8100b0103727009f59252559a7e6d
         * is_bind : 1
         * user_tel : 18698717416
         */

        private String uid;
        private String token;
        private String is_bind;
        private String user_tel;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIs_bind() {
            return is_bind;
        }

        public void setIs_bind(String is_bind) {
            this.is_bind = is_bind;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }
    }
}
