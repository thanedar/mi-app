package com.mitelcel.pack.bean.api.request;

import com.google.gson.annotations.Expose;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/5/15.
 */
public class BeanCredentials extends GenericBean {
    @Expose
    private String msisdn;
    @Expose
    private String password;

    public BeanCredentials() {
        this.msisdn = MiUtils.MiAppPreferences.getMsisdn();
        this.password = MiUtils.MiAppPreferences.getAuthPass();
    }

    public BeanCredentials(String msisdn, String password) {
        this.msisdn = MiUtils.getCleanMsisdn(msisdn);
        this.password = password;
    }
}
