package com.buckstracksui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.tatssense.core.retargeting.controller.AbLayerScreenActivity;
import com.tatssense.core.retargeting.controller.ILayerScreenListner;
import com.tatssense.core.retargeting.controller.databean.DataIntroWebBean;
import com.tatssense.core.retargeting.screenclasses.SingleWVIntroWeb;


public class ScIntroWebAct extends AbLayerScreenActivity implements  ILayerScreenListner.ScreenIntroWebListner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro_web_landing);
    }

    @Override
    protected void onResume() {
        super.onResume();

        WebView vw = SingleWVIntroWeb.getInstance().getVw();
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


        int wvId = getResources().getIdentifier (getClass().getSimpleName(),"id",getApplicationContext().getPackageName());
        vw.setId( wvId);
        ((RelativeLayout)findViewById(R.id.lin)).removeView(findViewById(wvId));
        ((RelativeLayout)findViewById(R.id.lin)).addView(SingleWVIntroWeb.getInstance().getVw(), layoutParam);

    }

    @Override
    public void onReceive(DataIntroWebBean bean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO could be better to put this line the superclass????
        ((RelativeLayout)findViewById(R.id.lin)).removeAllViews();

    }

    public void btnClick(View view){

        if(view.getId() == R.id.btn_close)

            // Advance to the next step in the wizard. If there is no next step, setCurrentItem
            // will do nothing.
            finish();

    }

}
