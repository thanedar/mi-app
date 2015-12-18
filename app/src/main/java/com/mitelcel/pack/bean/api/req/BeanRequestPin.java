package com.mitelcel.pack.bean.api.req;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 12/15/15.
 */
public class BeanRequestPin extends BeanGenericApi {

    public static final String NAME = "request_pin";
    @Expose
    private Params params;

    public BeanRequestPin(String msisdn) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(msisdn);
    }

    public BeanRequestPin(String msisdn, String reason) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(msisdn, reason);
    }

    public class Params extends GenericBean {
        @SerializedName("app_token")
        @Expose
        private String appToken;
        @Expose
        private String msisdn;
        @Expose
        private String reason = "register";

        public Params(String msisdn) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.msisdn = MiUtils.getCleanMsisdn(msisdn);
        }

        public Params(String msisdn, String reason) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.msisdn = MiUtils.getCleanMsisdn(msisdn);
            this.reason = reason;
        }
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
