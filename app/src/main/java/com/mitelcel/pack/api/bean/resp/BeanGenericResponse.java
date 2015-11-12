package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/3/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class BeanGenericResponse extends GenericBean {

    @Expose
    protected String id;
    @Expose
    protected Error error;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public class Error extends GenericBean {

        @Expose
        protected String message;
        @Expose
        protected Integer code;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
