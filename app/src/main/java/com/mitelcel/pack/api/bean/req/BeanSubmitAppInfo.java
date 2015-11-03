package com.mitelcel.pack.api.bean.req;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.BuildConfig;
import com.mitelcel.pack.api.bean.BeanGenericApi;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

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

        @SerializedName("os_type")
        @Expose
        private String osType;
        @SerializedName("os_version")
        @Expose
        private String osVersion;
        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @Expose
        private String imei;
        @Expose
        private String imsi;
        @SerializedName("android_id")
        @Expose
        private String androidId;

        public Params(Context context) {
            this.osType = MiUtils.MiAppPreferences.getDeviceType(context);
            this.osVersion = Build.VERSION.RELEASE;
            this.appVersion = BuildConfig.VERSION_NAME;

            this.imei = MiUtils.Info.getDeviceId(context);
            this.imsi = MiUtils.Info.getSubscriberId(context);
            this.androidId = MiUtils.Info.getAndroidId(context);
        }
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
