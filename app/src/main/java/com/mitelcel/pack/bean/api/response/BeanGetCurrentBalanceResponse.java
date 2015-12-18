package com.mitelcel.pack.bean.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/6/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BeanGetCurrentBalanceResponse extends BeanGenericResponse {

    @Expose
    private Result result;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public class Result extends GenericBean {
        @SerializedName("current_balance")
        @Expose
        private String currentBalance;
    }
}
