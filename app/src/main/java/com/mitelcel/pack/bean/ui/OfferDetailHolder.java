package com.mitelcel.pack.bean.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.mitelcel.pack.bean.GenericBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sudhanshu.thanedar on 20/11/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OfferDetailHolder extends GenericBean {

    //thumb
    int thumbnailTop;//
    int thumbnailLeft;//
    int thumbnailWidth;//
    int thumbnailHeight;//
    //btn install
    int btnInstallPlayTop;//
    int btnInstallPlayLeft;//
    int btnInstallPlayWidth;//
    int btnInstallPlayHeight;//

    String urlThumb;
    String urlBackground;
    String description;
    String btnText;
    String title;
    String packageId;

    public static final String LEFT  = ".left";
    public static final String TOP  = ".top";
    public static final String WIDTH = ".width";
    public static final String HEIGHT  = ".height";
    public static final String URL_THUMB = ".thumb";
    public static final String URL_GAME = ".game";
    public static final String URL_BACKGROUND = ".background";
    public static final String DESCRIPTION = ".description";
    public static final String TITLE = ".title";
    public static final String PACKAGE = ".package";
    public static final String BTN_LEFT  = ".btn_left";
    public static final String BTN_TOP  = ".btn_top";
    public static final String BTN_WIDTH = ".btn_width";
    public static final String BTN_HEIGHT  = ".btn_height";
    public static final String BTN_TEXT  = ".btn_text";

    public static void configureIntent(
            Intent gameDetails,
            String PACKAGE,
            int thumbnailTop,
            int thumbnailLeft,
            int thumbnailWidth,
            int thumbnailHeight,
            String urlIcon,
            String urlCard,
            String btnText,
            String description,
            int btnInstallPlayTop,
            int btnInstallPlayLeft,
            int btnInstallPlayWidth,
            int btnInstallPlayHeight){

        gameDetails.
                putExtra(PACKAGE + LEFT, thumbnailLeft).
                putExtra(PACKAGE + TOP, thumbnailTop).
                putExtra(PACKAGE + WIDTH, thumbnailWidth).
                putExtra(PACKAGE + HEIGHT, thumbnailHeight).
                putExtra(PACKAGE + URL_THUMB, urlIcon).
                putExtra(PACKAGE + URL_BACKGROUND, urlCard).
                putExtra(PACKAGE + BTN_TEXT, btnText).
                putExtra(PACKAGE + DESCRIPTION, description).
                putExtra(PACKAGE + BTN_LEFT, btnInstallPlayLeft).
                putExtra(PACKAGE + BTN_TOP, btnInstallPlayTop).
                putExtra(PACKAGE + BTN_WIDTH, btnInstallPlayWidth).
                putExtra(PACKAGE + BTN_HEIGHT, btnInstallPlayHeight);
    }

    public static OfferDetailHolder createObject(Bundle bundle, String PACKAGE){
        return new OfferDetailHolder(bundle, PACKAGE);
    }

    public OfferDetailHolder(Bundle bundle, String PACKAGE) {
        this.thumbnailTop = bundle.getInt(PACKAGE + TOP);
        this.thumbnailLeft = bundle.getInt(PACKAGE + LEFT);
        this.thumbnailWidth = bundle.getInt(PACKAGE + WIDTH);
        this.thumbnailHeight = bundle.getInt(PACKAGE + HEIGHT);
        this.btnText = bundle.getString(PACKAGE + BTN_TEXT);
        this.description = bundle.getString(PACKAGE + DESCRIPTION);
        this.title = bundle.getString(PACKAGE + TITLE);
        this.packageId = bundle.getString(PACKAGE + PACKAGE);
        this.urlThumb = bundle.getString(PACKAGE + URL_THUMB);
        this.urlBackground = bundle.getString(PACKAGE + URL_BACKGROUND);
        this.btnInstallPlayTop = bundle.getInt(PACKAGE + BTN_TOP);
        this.btnInstallPlayLeft = bundle.getInt(PACKAGE + BTN_LEFT);
        this.btnInstallPlayWidth = bundle.getInt(PACKAGE + BTN_WIDTH);
        this.btnInstallPlayHeight = bundle.getInt(PACKAGE + BTN_HEIGHT);
    }
}
