package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by sudhanshut on 11/6/15.
 */
@Data
@Getter
public class BeanGetCurrentBalanceResponse extends BeanGenericResponse {

    @Expose
    private Result result;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public class Result extends GenericBean {
        @SerializedName("current_balance")
        @Expose
        private String currentBalance;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
