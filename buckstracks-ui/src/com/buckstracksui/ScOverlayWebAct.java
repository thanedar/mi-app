package com.buckstracksui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.tatssense.core.retargeting.controller.AbLayerScreenActivity;
import com.tatssense.core.retargeting.controller.ILayerScreenListner;
import com.tatssense.core.retargeting.controller.databean.DataOverlayWebBean;



public class ScOverlayWebAct extends AbLayerScreenActivity implements ILayerScreenListner.ScreenOverlayWebListner{

    RelativeLayout linLayout = null;
    ViewGroup.LayoutParams lpView = null;
    String url=null;

    WebView myWebView= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.overlay_web);


    }

    @Override
    protected void onResume() {
        super.onResume();

        myWebView = (WebView)findViewById(R.id.webview);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.clearCache(true);


        if (this.url!=null)
            this.loadUrl(this.url);

    }

    public boolean loadUrl(String url) {


        if (url == null || url.equals("")) {
            myWebView.setVisibility(View.INVISIBLE);

            Log.i(this.getClass().getName(), "url[" + url + "]");
            return false;
        } else {
            myWebView.loadUrl(url);

            myWebView.setVisibility(View.VISIBLE);

            Log.i(this.getClass().getName(), "url[" + url + "]");
            return true;
        }

    }


    @Override
    public void onReceive(DataOverlayWebBean bean) {
        this.url = bean.getOverlayWeb().getUrl();


    }
    public void closeView(View v){
        this.finish();
    }
}
