package com.mitelcel.pack.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitelcel.pack.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DialogActivity extends Activity {

    public static final int APP_REQ             = 50;
    public static final int APP_RES             = 51;
    public static final int APP_REFRESH         = 52;
    public static final int APP_ERROR_CALL      = 53;

    public static final int REQ_REWARDS_PRIZE           = 54;
    public static final int REQ_REWARDS_EMAIL           = 55;
    public static final int REQ_SIGN_UP                 = 56;
    public static final int REQ_LOGIN_OR_SIGN_UP        = 58;
    public static final int REQ_SIGN_IN                 = 59;

    public static final int DIALOG_HIDDEN_ICO      = -1;

    public static final String DIALOG_CONTENT       = "dialog_content";
    public static final String DIALOG_BTN_TEXT      = "dialog_btn_text";
    public static final String DIALOG_RES_ID        = "dialog_int_res_id";
    public static final String DIALOG_RES_URL       = "dialog_int_res_url";
    public static final String DIALOG_RES_TITLE     = "dialog_int_res_title";

    @InjectView(R.id.tv_dialog_title)
    TextView tvDialogTitle;

    @InjectView(R.id.tv_dialog_content)
    TextView tvDialogContent;

    @InjectView(R.id.btn_dialog)
    Button btnDialog;

    @InjectView(R.id.iv_dialog)
    ImageView dialogIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        ButterKnife.inject(this);
        initComponent();
    }

    private void initComponent() {
        Bundle bundle = getIntent().getExtras();

        if(bundle == null) return;

        if(bundle.containsKey(DIALOG_BTN_TEXT)){
            btnDialog.setText(bundle.getString(DIALOG_BTN_TEXT));
        }
        if(bundle.containsKey(DIALOG_CONTENT)){
            tvDialogContent.setText(bundle.getString(DIALOG_CONTENT));
        }
        if(bundle.containsKey(DIALOG_RES_TITLE) && bundle.getString(DIALOG_RES_TITLE) != ""){
            tvDialogTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText(bundle.getString(DIALOG_RES_TITLE));
        }
        if(bundle.containsKey(DIALOG_RES_ID)){
                dialogIcon.setVisibility(View.VISIBLE);
                dialogIcon.setImageResource(bundle.getInt(DIALOG_RES_ID));
        }
        if(bundle.containsKey(DIALOG_RES_URL)){
            ImageLoader.getInstance()
                    .displayImage(bundle.getString(DIALOG_RES_URL), dialogIcon, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            dialogIcon.setImageBitmap(loadedImage);
                        }
                    });
            dialogIcon.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_dialog)
    public void click(){
        setResult(APP_REFRESH);
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        setResult(APP_RES);
        finish();
    }
}
