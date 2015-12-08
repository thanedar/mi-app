package com.mitelcel.pack.bean.ui;

import com.google.gson.annotations.Expose;
import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 12/7/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanContactInfo extends GenericBean{
    @Expose
    private String key;

    @Expose
    private String name;

    @Expose
    private String phone;

    @Expose
    private String photo;

}
