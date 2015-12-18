package com.mitelcel.pack.bean.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/6/15.
 */
public class BeanLogout extends BeanGenericApi {

    public static final String NAME = "logout";

    @Expose
    private Params params;

    public BeanLogout() {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params();
    }

    public class Params extends GenericBean {

        @SerializedName("session_id")
        @Expose
        private String sessionId;

        public Params() {
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
        }
    }
}
