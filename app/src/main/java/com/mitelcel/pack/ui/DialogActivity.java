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
    public static final int APP_API_CALL      = 54;

    public static final int REQ_SIGN_UP                 = 56;
    public static final int REQ_SIGN_IN                 = 59;

    public static final int DIALOG_HIDDEN_ICO      = -1;

    public static final String DIALOG_CONTENT       = "dialog_content";
    public static final String DIALOG_POS_BTN_TEXT  = "dialog_pos_btn_text";
    public static final String DIALOG_NEG_BTN_TEXT  = "dialog_neg_btn_text";
    public static final String DIALOG_RES_ID        = "dialog_int_res_id";
    public static final String DIALOG_RES_URL       = "dialog_int_res_url";
    public static final String DIALOG_RES_TITLE     = "dialog_int_res_title";

    @InjectView(R.id.tv_dialog_title)
    TextView tvDialogTitle;

    @InjectView(R.id.tv_dialog_content)
    TextView tvDialogContent;

    @InjectView(R.id.btn_dialog_positive)
    Button btnDialogPos;

    @InjectView(R.id.btn_dialog_negative)
    Button btnDialogNeg;

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

        if(bundle.containsKey(DIALOG_POS_BTN_TEXT)){
            btnDialogPos.setText(bundle.getString(DIALOG_POS_BTN_TEXT));
        }
        if(bundle.containsKey(DIALOG_NEG_BTN_TEXT)){
            btnDialogNeg.setVisibility(View.VISIBLE);
            btnDialogNeg.setText(bundle.getString(DIALOG_NEG_BTN_TEXT));
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

    @OnClick(R.id.btn_dialog_positive)
    public void posBtnClick(){
        setResult(APP_REFRESH);
        finish();
    }

    @OnClick(R.id.btn_dialog_negative)
    public void negBtnClick(){
        setResult(APP_RES);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(APP_RES);
        finish();
    }
}
