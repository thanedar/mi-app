package com.mitelcel.pack.api.bean.resp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 11/17/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanRechargeAccountResponse extends BeanGenericResponse {

    @Expose
    private Result result;

    @Data
    @EqualsAndHashCode(callSuper=true)
    public class Result extends GenericBean {
        @SerializedName("session_id")
        @Expose
        private String sessionId;
    }

}
