package com.mitelcel.pack;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class Config {

    public static final int ERROR_CODE_TOKEN_EXPIRED = -2;
    public static final int ERROR_NETWORK = -3;
    public static final int SUCCESS = 0;

    public static final int PARSE_ERROR =       -32700;
    public static final int INVALID_REQUEST =   -32600;
    public static final int METHOD_NOT_FOUND =  -32601;
    public static final int INVALID_PARAMS =    -32602;
    public static final int INTERNAL_ERROR =    -32603;

    public static final int ACTION_TYPE_RECHARGE = 9;
    public static final int ACTION_TYPE_TRANSFER = 10;
    public static final int ACTION_TYPE_BONUS = 5;

    public static final int MSISDN_LENGTH = 10;
    public static final int PASS_LENGTH = 4;

    /**
     * HTML
     */
    public static final String HTML_FAQ = "file:///android_asset/faq.html";
    public static final String HTML_HELP = "file:///android_asset/help.html";
    public static final String HTML_RULES = "file:///android_asset/rules.html";
    public static final String HTML_PRIVACY = "file:///android_asset/privacy.html";
    public static final String HTML_TERM_CONDITION = "file:///android_asset/terms_and_condition.html";

    /**
     * Custom Event
     *
     * 115	D7dzaWAb
     *
     */
    public static final String EVENT_NEW_USER_ID	             = "D7dzaWAb";
}
