package com.buckstracksui;


import android.os.Bundle;

import com.tatssense.core.retargeting.controller.AbLayerScreenActivity;
import com.tatssense.core.retargeting.controller.ILayerScreenListner;
import com.tatssense.core.retargeting.controller.databean.DataIntroVideoBean;


public class ScIntroVidAct extends AbLayerScreenActivity implements ILayerScreenListner.ScreenIntroVideoListner{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.overlay_pink);


    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void onReceive(DataIntroVideoBean bean) {

            /*
            we must get related activity data, as layout, image.. etc
             */


//            String layout = "overlay_pink";
      /*  String layout =  bean.getLayout_name();
        int resID = getApplicationContext().getResources().getIdentifier(layout, "layout", getApplicationContext().getPackageName());
        setContentView(resID);
        */

    }
}