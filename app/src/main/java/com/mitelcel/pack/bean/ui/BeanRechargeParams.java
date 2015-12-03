package com.mitelcel.pack.bean.ui;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 11/24/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanRechargeParams extends GenericBean {
    @Expose
    private float amount;

    @SerializedName("app_token")
    @Expose
    private String appToken;

    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public BeanRechargeParams(String token, String sessionId, float amount) {
        this.amount = amount;
        this.appToken = token;
        this.sessionId = sessionId;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
