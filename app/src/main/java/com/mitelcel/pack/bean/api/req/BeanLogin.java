package com.mitelcel.pack.bean.api.req;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/5/15.
 */
public class BeanLogin extends BeanGenericApi {

    public static final String NAME = "login";
    @Expose
    private Params params;

    public BeanLogin (Context context) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(context);
    }

    public BeanLogin (Context context, String msisdn, String password) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(context, msisdn, password);
    }

    public class Params extends GenericBean {

        @SerializedName("app_info")
        @Expose
        private BeanAppInfo appInfo;

        @SerializedName("app_token")
        @Expose
        private String appToken;

        @Expose
        private BeanCredentials credentials;

        public Params(Context context) {
            this.appInfo = new BeanAppInfo(context);
            String token = MiUtils.MiAppPreferences.getToken();
            if(token != "")
                this.appToken = token;

            this.credentials = new BeanCredentials();
        }

        public Params(Context context, String msisdn, String password) {
            this.appInfo = new BeanAppInfo(context);

            String token = MiUtils.MiAppPreferences.getToken();
            if(!token.equals(""))
                this.appToken = token;

            this.credentials = new BeanCredentials(msisdn, password);
        }
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
