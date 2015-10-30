package com.buckstracksui;

import android.app.Activity;
import android.content.Context;

import com.tatssense.core.ScreenMaster;


/**
 * Created by antonino.orlando on 27/01/2015.
 */

/*
 * TODO: SINGLETON!
 */
public class ScreensUI extends ScreenMaster {

    Context con;
    static ScreensUI screensUIInstance=null;
    /*private ScreensUI(Context con, String campaignId, String screenType){
        super(con, campaignId, screenType);
        this.con = con;

    }*/

//    public ScreensUI(Activity con, String tag, String t){
//        super(con, tag, t);
//
//    }

    private ScreensUI(Activity con){
        super(con);
        this.con = con;

    }
    private ScreensUI(Activity con, String tag){
        super(con, tag);
        this.con = con;

    }

    public static ScreensUI getInstance(Activity con, String tag){

            screensUIInstance = new ScreensUI( con,  tag);

        return screensUIInstance;
    }
    public static ScreensUI getInstance(Activity con){

            screensUIInstance = new ScreensUI( con);

        return screensUIInstance;
    }

}
