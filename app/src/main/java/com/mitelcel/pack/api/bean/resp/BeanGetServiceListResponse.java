package com.mitelcel.pack.api.bean.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 12/02/2015.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanGetServiceListResponse extends BeanGenericResponse {

    @Expose
    private List <Service> result = new ArrayList<>();

    @Data
    public static class Service {
        @SerializedName("service_id")
        @Expose
        private long serviceId;
        @SerializedName("url_image")
        @Expose
        private String urlImage;
        @SerializedName("url_icon")
        @Expose
        private String urlIcon;
        @Expose
        private String description;
        @SerializedName("action_button_text")
        @Expose
        private String buttonText;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }}
