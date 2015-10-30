package com.buckstracksui;

import android.os.Bundle;

import com.tatssense.core.retargeting.controller.AbLayerScreenActivity;
import com.tatssense.core.retargeting.controller.ILayerScreenListner;
import com.tatssense.core.retargeting.controller.databean.DataOverlayNativeBean;


public class ScOverlayNatAct extends AbLayerScreenActivity implements ILayerScreenListner.ScreenOverlayNativeListner {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onReceive(DataOverlayNativeBean bean) {


    }


}