package com.mitelcel.pack.bean.api.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/3/15.
 */
public class BeanGetAccountInfo extends BeanGenericApi {

    public static final String NAME = "get_account_info";
    @Expose
    private Params params;

    public BeanGetAccountInfo () {
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
