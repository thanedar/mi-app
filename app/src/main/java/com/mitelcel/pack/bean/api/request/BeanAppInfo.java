package com.mitelcel.pack.bean.api.request;

import android.content.Context;
import android.os.Build;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.BuildConfig;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/5/15.
 */
public class BeanAppInfo extends GenericBean {

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

    public BeanAppInfo(Context context) {
        this.osType = MiUtils.MiAppPreferences.getDeviceType();
        this.osVersion = Build.VERSION.RELEASE;
        this.appVersion = BuildConfig.VERSION_NAME;

        this.imei = MiUtils.Info.getDeviceId(context);
        this.imsi = MiUtils.Info.getSubscriberId(context);
        this.androidId = MiUtils.Info.getAndroidId(context);
    }
}

