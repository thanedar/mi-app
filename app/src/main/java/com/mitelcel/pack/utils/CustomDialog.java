package com.mitelcel.pack.utils;

import android.app.Activity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.R;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class CustomDialog {

    public static MaterialDialog getNoInternetConnectin(Activity context){
        return new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_layout, false)
                .build();
    }

}
