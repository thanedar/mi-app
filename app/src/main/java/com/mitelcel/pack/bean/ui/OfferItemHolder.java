package com.mitelcel.pack.bean.ui;

import com.google.gson.Gson;
import com.mitelcel.pack.bean.api.response.BeanGetOfferListResponse;
import com.mitelcel.pack.bean.GenericBean;
import com.mitelcel.pack.utils.MiLog;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */

public class OfferItemHolder extends GenericBean{

    public String description;
    public String urlIcon;
    public String urlCard;
    public String buttonText;

    public BeanGetOfferListResponse.Offer responseBean;

    public OfferItemHolder(BeanGetOfferListResponse.Offer responseBean) {
        MiLog.i("OfferItemHolder", "Response bean [ " + responseBean.toString() + " ]");

        this.description = responseBean.getDescription();
        this.urlIcon = responseBean.getUrlIcon();
        this.urlCard = responseBean.getUrlImage();
        this.buttonText = responseBean.getButtonText();
        MiLog.i("OfferItemHolder", "set [ " + responseBean.toString() + " ]");
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}