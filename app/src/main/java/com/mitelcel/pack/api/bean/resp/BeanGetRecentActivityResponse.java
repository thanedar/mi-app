package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mitelcel.pack.bean.GenericBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshut on 11/10/15.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanGetRecentActivityResponse extends BeanGenericResponse {

    @Expose
    private List <UserActivity> result = new ArrayList<>();

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
        private String activityStatus;
        @SerializedName("activity_datetime")
        @Expose
        private String activityDatetime;
        @Expose
        private String request;
        @Expose
        private String response;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }}
