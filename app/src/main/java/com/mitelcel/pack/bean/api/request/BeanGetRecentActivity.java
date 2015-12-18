package com.mitelcel.pack.bean.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshut on 11/10/15.
 */
public class BeanGetRecentActivity extends BeanGenericApi {

    public static final String NAME = "get_recent_activity";
    @Expose
    private Params params;

    public BeanGetRecentActivity() {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params();
    }

    public BeanGetRecentActivity(int limit) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(limit);
    }

    public BeanGetRecentActivity(int status, int limit) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(status, limit);
    }

    public class Params extends GenericBean {

        @SerializedName("session_id")
        @Expose
        private String sessionId;

        @SerializedName("activity_status")
        @Expose
        private int activityStatus;

        @Expose
        private int limit;

        public Params() {
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
            this.activityStatus = 0; //  By default successful activity list will be returned.
            this.limit = 5;
        }

        public Params(int limit) {
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
            this.activityStatus = 0; //  By default successful activity list will be returned.
            this.limit = limit;
        }

        public Params(int status, int limit) {
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
            this.activityStatus = status;
            this.limit = limit;
        }
    }
}
