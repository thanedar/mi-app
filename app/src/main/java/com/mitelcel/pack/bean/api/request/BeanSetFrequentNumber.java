package com.mitelcel.pack.bean.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/25/15.
 */
public class BeanSetFrequentNumber extends BeanGenericApi {

    public static final String NAME = "set_frequent_number";
    @Expose
    private Params params;

    public BeanSetFrequentNumber(String msisdn, int order) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(msisdn, order);
    }

    public class Params extends GenericBean {
        @SerializedName("app_token")
        @Expose
        private String appToken;

        @SerializedName("session_id")
        @Expose
        private String sessionId;

        @Expose
        private String msisdn;

        @Expose
        private int order;

        public Params(String msisdn, int order) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();

            this.msisdn = msisdn;
            this.order = order;
        }
    }
}
