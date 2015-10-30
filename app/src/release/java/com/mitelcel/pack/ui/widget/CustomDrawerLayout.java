package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitelcel.pack.R;
/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class CustomDrawerLayout extends DrawerLayout {

    protected static final int NAVDRAWER_ITEM_HOME              = 0;
    protected static final int NAVDRAWER_ITEM_RECENT            = 1;
    protected static final int NAVDRAWER_ITEM_FREQUENT_NUMBERS  = 2;
    protected static final int NAVDRAWER_ITEM_SERVICES          = 3;
    protected static final int NAVDRAWER_ITEM_RECHARGE          = 4;
    protected static final int NAVDRAWER_ITEM_PASTIME           = 5;
    protected static final int NAVDRAWER_ITEM_OFFERS            = 6;
    protected static final int NAVDRAWER_ITEM_COMMUNICATE       = 7;
    protected static final int NAVDRAWER_ITEM_HELP              = 8;
    protected static final int NAVDRAWER_ITEM_TERMS             = 9;
    protected static final int NAVDRAWER_ITEM_CONTACTUS         = 10;
    protected static final int NAVDRAWER_ITEM_LOGOUT            = 11;

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
            R.string.navdrawer_item_terms,
            R.string.navdrawer_item_contact,
            R.string.navdrawer_item_logout
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
            R.id.navdrawer_item_terms,
            R.id.navdrawer_item_contact,
            R.id.navdrawer_item_logout
    };

    public CustomDrawerLayout(Context context) {
        super(context);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setUpNavigationDrawer();
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
        // TERMS
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_TERMS], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_TERMS], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_TERMS]);
        // CONTACT US
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_CONTACTUS], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_CONTACTUS], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_CONTACTUS]);
        // LOGOUT
        fillItem(NAVDRAWER_LAYOUT_RES_ID[NAVDRAWER_ITEM_LOGOUT], NAVDRAWER_ICON_RES_ID[NAVDRAWER_ITEM_LOGOUT], NAVDRAWER_TITLE_RES_ID[NAVDRAWER_ITEM_LOGOUT]);
    }

}
