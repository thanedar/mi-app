package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;

/**
 * Created by sudhanshut on 11/3/15.
 */
@Data
public class BeanGenericResponse extends GenericBean {

    public static final int PARSE_ERROR =       -32700;
    public static final int INVALID_REQUEST =   -32600;
    public static final int METHOD_NOT_FOUND =  -32601;
    public static final int INVALID_PARAMS =    -32602;
    public static final int INTERNAL_ERROR =    -32603;

    @Expose
    protected String id;
    @Expose
    protected Error error;

    @Data
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
