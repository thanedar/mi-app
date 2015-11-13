package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.module.SharedPrefModule;
import com.mitelcel.pack.ui.ProcessPhoenix;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class CustomDrawerLayout extends DrawerLayout implements SharedPreferences.OnSharedPreferenceChangeListener{

    protected static final int NAVDRAWER_ITEM_HOME              = 0;
    protected static final int NAVDRAWER_ITEM_RECENT            = 1;
    protected static final int NAVDRAWER_ITEM_FREQUENT_NUMBERS  = 2;
    protected static final int NAVDRAWER_ITEM_SERVICES          = 3;
    protected static final int NAVDRAWER_ITEM_RECHARGE          = 4;
    protected static final int NAVDRAWER_ITEM_PASTIME           = 5;
    protected static final int NAVDRAWER_ITEM_OFFERS            = 6;
    protected static final int NAVDRAWER_ITEM_COMMUNICATE       = 7;
    protected static final int NAVDRAWER_ITEM_HELP              = 8;
    protected static final int NAVDRAWER_ITEM_TUTORIAL          = 9;
    protected static final int NAVDRAWER_ITEM_TERMS             = 10;
    protected static final int NAVDRAWER_ITEM_CONTACTUS         = 11;
    protected static final int NAVDRAWER_ITEM_LOGOUT            = 12;

    protected static final int NAVDRAWER_ITEM_DEBUG             = 13;
    protected static final int NAVDRAWER_ITEM_ERROR_CALL        = 14;

    protected static final int NAVDRAWER_ITEM_SEPARATOR = -2;

    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_item_home,
            R.string.navdrawer_item_recent,
            R.string.navdrawer_item_frequent_numbers,
            R.string.navdrawer_item_services,
            R.string.navdrawer_item_recharge,
            R.string.navdrawer_item_pastime,
            R.string.navdrawer_item_offers,
            R.string.navdrawer_item_communicate,
            R.string.navdrawer_item_help,
            R.string.navdrawer_item_tutorial,
            R.string.navdrawer_item_terms,
            R.string.navdrawer_item_contact,
            R.string.navdrawer_item_logout,
            R.string.navdrawer_item_debug,
            R.string.navdrawer_item_error_call
    };

    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_menu_home,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings,
            R.drawable.ic_menu_settings
    };

    private static final int[] NAVDRAWER_LAYOUT_RES_ID = new int[]{
            R.id.navdrawer_item_home,
            R.id.navdrawer_item_recent,
            R.id.navdrawer_item_frequent_numbers,
            R.id.navdrawer_item_services,
            R.id.navdrawer_item_recharge,
            R.id.navdrawer_item_pastime,
            R.id.navdrawer_item_offers,
            R.id.navdrawer_item_communicate,
            R.id.navdrawer_item_help,
            R.id.navdrawer_item_tutorial,
            R.id.navdrawer_item_terms,
            R.id.navdrawer_item_contact,
            R.id.navdrawer_item_logout,
            R.id.navdrawer_item_debug,
            R.id.navdrawer_item_error_call
    };

    SwitchCompat switchCompatErrorCall = null;

    SharedPreferences sharedPreferences;

    public CustomDrawerLayout(Context context) {
        super(context);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setUpNavigationDrawer();
    }

    public void init(){
        sharedPreferences = MiApp.getInstance().getAppComponent().sharedPreferences();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(SharedPrefModule.HAS_CALL_ERROR) && switchCompatErrorCall != null){
            switchCompatErrorCall.setChecked(sharedPreferences.getBoolean(SharedPrefModule.HAS_CALL_ERROR, false));
        }

        if(key.equals(SharedPrefModule.HAS_MOCK) && switchCompatErrorCall != null){
            switchCompatErrorCall.setEnabled(sharedPreferences.getBoolean(SharedPrefModule.HAS_MOCK, false));
        }
    }

    private void configureSwitch() {

        SwitchCompat switchCompat = ((SwitchCompat)findViewById(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_DEBUG]).findViewById(R.id.switch1));
        switchCompat.setChecked(sharedPreferences.getBoolean(SharedPrefModule.HAS_MOCK, false));
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(), "" + isChecked, Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean(SharedPrefModule.HAS_MOCK, isChecked).commit();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProcessPhoenix.triggerRebirth(getContext());
                    }
                }, 500);
            }
        });

        switchCompatErrorCall = ((SwitchCompat)findViewById(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_ERROR_CALL]).findViewById(R.id.switch1));
        switchCompatErrorCall.setChecked(sharedPreferences.getBoolean(SharedPrefModule.HAS_CALL_ERROR, false));
        switchCompatErrorCall.setEnabled(sharedPreferences.getBoolean(SharedPrefModule.HAS_MOCK, false));
        switchCompatErrorCall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(), "Has error call? " + isChecked, Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean(SharedPrefModule.HAS_CALL_ERROR, isChecked).apply();
            }
        });
    }

    private void fillItem(int layout, int icon, int text) {
        ((TextView)findViewById(layout).findViewById(R.id.title)).setText(getContext().getString(text));
        ((ImageView)findViewById(layout).findViewById(R.id.icon)).setImageResource(icon);
    }

    void setUpNavigationDrawer(){
        // HOME
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_HOME], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_HOME], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_HOME]);
        // RECENT
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_RECENT], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_RECENT], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_RECENT]);
        // FREQUENT NUMBERS
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_FREQUENT_NUMBERS], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_FREQUENT_NUMBERS], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_FREQUENT_NUMBERS]);
        // SERVICES
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_SERVICES], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_SERVICES], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_SERVICES]);
        // RECHARGE
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_RECHARGE], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_RECHARGE], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_RECHARGE]);
        // PASTIME
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_PASTIME], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_PASTIME], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_PASTIME]);
        // OFFERS
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_OFFERS], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_OFFERS], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_OFFERS]);
        // COMMUNICATE
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_COMMUNICATE], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_COMMUNICATE], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_COMMUNICATE]);
        // HELP
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_HELP], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_HELP], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_HELP]);
        // TUTORIAL
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_TUTORIAL], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_TUTORIAL], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_TUTORIAL]);
        // TERMS
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_TERMS], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_TERMS], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_TERMS]);
        // CONTACT US
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_CONTACTUS], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_CONTACTUS], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_CONTACTUS]);
        // LOGOUT
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_LOGOUT], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_LOGOUT], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_LOGOUT]);
        // SET ENVIRONMENT
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_DEBUG], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_DEBUG], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_DEBUG]);
        // SET ERROR CALL
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_ERROR_CALL], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_ERROR_CALL], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_ERROR_CALL]);

        configureSwitch();
    }

}
