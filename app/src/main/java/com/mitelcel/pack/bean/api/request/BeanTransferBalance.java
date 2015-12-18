package com.mitelcel.pack.bean.api.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshu.thanedar on 11/17/15.
 */
public class BeanTransferBalance extends BeanGenericApi {

    public static final String NAME = "transfer_balance";
    @Expose
    private Params params;

    public BeanTransferBalance(String target, float amount) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(target, amount);
    }

    public BeanTransferBalance(String target, float amount, String password) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(target, amount, password);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public class Params extends GenericBean {

        @SerializedName("app_token")
        @Expose
        private String appToken;

        @SerializedName("session_id")
        @Expose
        private String sessionId;

        @SerializedName("target_msisdn")
        @Expose
        private String target;

        @SerializedName("transfer_amount")
        @Expose
        private float amount;

        @Expose
        private BeanCredentials credentials;

        public Params(String target, float amount) {
            this.amount = amount;
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
            this.target = target;

            this.credentials = new BeanCredentials();
        }

        public Params(String target, float amount, String password) {
            this.amount = amount;
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
            this.target = target;

            String msisdn = MiUtils.MiAppPreferences.getMsisdn();
            this.credentials = new BeanCredentials(msisdn, password);
        }
    }
}
