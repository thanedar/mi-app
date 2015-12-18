package com.mitelcel.pack.bean.api.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.mitelcel.pack.bean.GenericBean;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public abstract class BeanGenericApi extends GenericBean {

    @Expose
    protected long id;
    @Expose
    protected String method;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
