package com.mitelcel.pack.api.bean.req;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

/**
 * Created by sudhanshut on 11/3/15.
 */
public class BeanSubmitAppInfo extends BeanGenericApi {

    public static final String NAME = "submit_app_info";
    @Expose
    private Params params;

    public BeanSubmitAppInfo (Context context) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(context);
    }

    public class Params extends GenericBean {

        @SerializedName("app_info")
        @Expose
        private BeanAppInfo appInfo;

        public Params(Context context) {
            this.appInfo = new BeanAppInfo(context);
        }
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
