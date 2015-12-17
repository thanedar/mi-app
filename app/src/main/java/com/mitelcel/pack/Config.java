package com.mitelcel.pack;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class Config {

    public static final int SUCCESS = 0;

    public static final int INVALID_JSON     = -32700;
    public static final int INVALID_REQUEST  = -32600;
    public static final int METHOD_NOT_FOUND = -32601;
    public static final int INVALID_PARAMS   = -32602;
    public static final int INTERNAL_ERROR   = -32603;

    public static final int GENERIC_ERROR   = -32001;
    public static final int NO_CONNECTION   = -32002;
    public static final int LOST_CONNECTION = -32003;

    public static final int RECORD_EXISTS   = -32010;
    public static final int PARAM_NOT_SET   = -32011;
    public static final int PARAM_VALUE     = -32012;
    public static final int PARAM_TYPE      = -32013;
    public static final int PARAM_UNKNOWN   = -32014;
    public static final int PARAM_NO_RECORD = -32015;

    public static final int INVALID_CREDENTIALS = -32030;
    public static final int INVALID_SESSION_ID  = -32031;

    public static final int LOW_BALANCE = -32050;

    public static final int INVALID_PIN          = -32060;
    public static final int USER_EXISTS          = -32061;
    public static final int USER_DOES_NOT_EXISTS = -32062;

    public static final int ACTION_TYPE_TRANSFER = 9;
    public static final int ACTION_TYPE_RECHARGE = 10;
    public static final int ACTION_TYPE_BONUS    = 5;

    public static final String MSISDN_PREFIX = "52";
    public static final int MSISDN_LENGTH = 10;
    public static final int PASS_LENGTH = 3;
    public static final int PASS_TIMEOUT = 20;

    public static final int PICK_CONTACT = 99;

    public static final String ADCOLONY_APP_ID = "app5ee323996bd043da8c";
    public static final String ADCOLONY_ZONE_ID = "vz8d327906cf784b51a5";

    /**
     * HTML
     */
    public static final String HTML_FAQ = "file:///android_asset/faq.html";
    public static final String HTML_HELP = "file:///android_asset/help.html";
    public static final String HTML_PRIVACY = "file:///android_asset/privacy.html";
    public static final String HTML_TERMS = "file:///android_asset/terms_and_condition.html";

    /**
     * Custom Event
     *
     * 115	D7dzaWAb
     *
     */
    public static final String EVENT_NEW_USER_ID	             = "D7dzaWAb";
}
