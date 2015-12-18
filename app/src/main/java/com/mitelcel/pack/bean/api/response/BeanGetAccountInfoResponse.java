package com.mitelcel.pack.bean.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/3/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BeanGetAccountInfoResponse extends BeanGenericResponse {

    @Expose
    private Result result;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
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
}
