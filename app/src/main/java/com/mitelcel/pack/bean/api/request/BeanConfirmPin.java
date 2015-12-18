package com.mitelcel.pack.bean.api.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 12/15/15.
 */
public class BeanConfirmPin extends BeanGenericApi {

    public static final String NAME = "confirm_pin";
    @Expose
    private Params params;

    public BeanConfirmPin(String pin) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(pin);
    }

    public BeanConfirmPin(String msisdn, String pin) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(msisdn, pin);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public class Params extends GenericBean {
        @SerializedName("app_token")
        @Expose
        private String appToken;
        @Expose
        private String msisdn;
        @Expose
        private String pin = "register";

        public Params(String pin) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.msisdn = MiUtils.MiAppPreferences.getMsisdn();
            this.pin = pin;
        }

        public Params(String msisdn, String pin) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.msisdn = MiUtils.getCleanMsisdn(msisdn);
            this.pin = pin;
        }
    }
}
