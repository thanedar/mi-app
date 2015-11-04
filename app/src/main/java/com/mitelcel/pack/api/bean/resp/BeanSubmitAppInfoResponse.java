package com.mitelcel.pack.api.bean.resp;

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
@EqualsAndHashCode(callSuper=false)
public class BeanSubmitAppInfoResponse extends BeanGenericResponse {

    @Expose
    private Result result;

    @Data
    public class Result extends GenericBean {
        @SerializedName("app_token")
        @Expose
        private String appToken;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
