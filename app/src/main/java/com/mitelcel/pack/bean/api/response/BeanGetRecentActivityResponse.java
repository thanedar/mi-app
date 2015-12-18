package com.mitelcel.pack.bean.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/10/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BeanGetRecentActivityResponse extends BeanGenericResponse {

    @Expose
    private List<UserActivity> result = new ArrayList<>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Data
    public static class UserActivity {
        @SerializedName("activity_id")
        @Expose
        private long activityId;
        @SerializedName("activity_type")
        @Expose
        private int activityType;
        @SerializedName("api_method")
        @Expose
        private String apiMethod;
        @SerializedName("activity_status")
        @Expose
        private int activityStatus;
        @SerializedName("activity_datetime")
        @Expose
        private String activityDatetime;
        @SerializedName("activity_timestamp")
        @Expose
        private long activityTimestamp;
        @Expose
        private String request;
        @Expose
        private String response;
        @SerializedName("app_display_text")
        @Expose
        private String appDisplayText;
    }
}
