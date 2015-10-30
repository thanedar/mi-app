package com.buckstracksui;

import android.app.Activity;

public class ScIntroSlideAct {

    Activity activity = null;

    private ScIntroSlideAct(Activity activity) {
        this.activity = activity;
    }

    public static class ScIntroSlideBuilder{

        public static  ScIntroSlideAct build(Activity act) {
            ScIntroSlideAct instance = new ScIntroSlideAct(act);
            return  instance;
        }
    }
}
