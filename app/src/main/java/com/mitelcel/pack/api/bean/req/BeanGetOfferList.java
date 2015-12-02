package com.mitelcel.pack.api.bean.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class BeanGetOfferList extends BeanGenericApi {

    public static final String NAME = "get_offer_list";
    @Expose
    private Params params;

    public BeanGetOfferList () {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params();
    }

    public BeanGetOfferList (int page, int limit) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(limit);
    }

    public class Params extends GenericBean {

        @SerializedName("session_id")
        @Expose
        private String sessionId;

        @Expose
        private int limit;

        public Params() {
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
        }

        public Params(int limit) {
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();
            this.limit = limit;
        }
    }
}
