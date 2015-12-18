package com.mitelcel.pack.bean.api.request;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshu.thanedar on 12/16/15.
 */
public class BeanUpdateUserInfo extends BeanGenericApi {

    public static final String NAME = "update_user_info";
    @Expose
    private Params params;

    public BeanUpdateUserInfo(String new_password) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(new_password);
    }

    public BeanUpdateUserInfo(String old_password, String new_password) {
        this.id = System.currentTimeMillis();
        this.method = NAME;
        this.params = new Params(old_password, new_password);
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

        @Expose
        private BeanCredentials credentials;

        @SerializedName("new_password")
        @Expose
        private String newPassword;

        public Params(String new_password) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();

            this.newPassword = new_password;
        }

        public Params(String old_password, String new_password) {
            this.appToken = MiUtils.MiAppPreferences.getToken();
            this.sessionId = MiUtils.MiAppPreferences.getSessionId();

            this.credentials = new BeanCredentials(MiUtils.MiAppPreferences.getMsisdn(), old_password);
            this.newPassword = new_password;
        }
    }
}
