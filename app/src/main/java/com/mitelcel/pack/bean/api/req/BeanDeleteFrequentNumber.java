package com.mitelcel.pack.bean.api.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/25/15.
 */
public class BeanDeleteFrequentNumber extends BeanGenericApi {

    public static final String NAME = "delete_frequent_number";
    @Expose
    private Params params;

    public BeanDeleteFrequentNumber(int order) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(order);
    }

    public class Params extends GenericBean {
        @SerializedName("app_token")
        @Expose
        private String appToken;

        @SerializedName("session_id")
        @Expose
        private String sessionId;

        @Expose
        private int order;

        public Params(int order) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();

            this.order = order;
        }
    }
}
