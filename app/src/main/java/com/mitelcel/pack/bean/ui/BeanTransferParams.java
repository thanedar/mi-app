package com.mitelcel.pack.bean.ui;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 11/24/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanTransferParams extends GenericBean {

    @SerializedName("app_token")
    @Expose
    private String appToken;

    @SerializedName("session_id")
    @Expose
    private String sessionId;

    @SerializedName("target_msisdn")
    @Expose
    private String target;

    @SerializedName("transfer_amount")
    @Expose
    private float amount;

    public BeanTransferParams(String token, String sessionId, String target, float amount) {
        this.amount = amount;
        this.appToken = token;
        this.sessionId = sessionId;
        this.target = target;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
