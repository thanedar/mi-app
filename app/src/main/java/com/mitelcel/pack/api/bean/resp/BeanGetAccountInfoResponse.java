package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;

/**
 * Created by sudhanshut on 11/3/15.
 */
@Data
public class BeanGetAccountInfoResponse extends BeanGenericResponse {

    @Expose
    private Result result;

    @Data
    public class Result extends GenericBean {
        @SerializedName("account_type")
        @Expose
        private String accountType;
        @SerializedName("remaining_balance")
        @Expose
        private String remainingBalance;
        @SerializedName("used_minutes")
        @Expose
        private String usedMinutes;
        @SerializedName("used_sms")
        @Expose
        private String usedSms;
        @SerializedName("used_data")
        @Expose
        private String usedData;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
