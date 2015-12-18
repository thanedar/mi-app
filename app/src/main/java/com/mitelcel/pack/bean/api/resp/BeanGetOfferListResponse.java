package com.mitelcel.pack.bean.api.resp;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BeanGetOfferListResponse extends BeanGenericResponse {

    @Expose
    private List <Offer> result = new ArrayList<>();

    @Data
    public static class Offer {
        @SerializedName("offer_id")
        @Expose
        private long offerId;
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
