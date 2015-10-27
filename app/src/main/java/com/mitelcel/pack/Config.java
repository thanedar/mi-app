package com.mitelcel.pack;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class Config {

    public static String GAME_TYPE_EXTERNAL = "external";
    public static String GAME_TYPE_INTERNAL = "internal";

    public static final int GAME_ACTION_TYPE_INSTALL = 50;
    public static final int GAME_ACTION_TYPE_PLAY = 51;
    public static final int GAME_ACTION_TYPE_REWARDS = 52;
    public static final int GAME_ACTION_TYPE_REFERRALCODE = 53;
    public static final int GAME_ACTION_TYPE_BONUS = 54;
    public static String GAME_REWARDS_COINS = "rewards_coins";
    public static String GAME_REWARDS_CASH = "rewards_cash";
    public static final int GAME_SOURCE = 6;
    public static final int GAME_ID_VALUE = 99;
    public static final int ERROR_CODE_TOKEN_EXPIRED = -2;
    public static final int ERROR_NETWORK = -3;
    public static final int SUCCESS = 1;

    /**
     * CASH/COINS
     */
    public static final String REWARD_CURRENCY = "reward_currency";
    public static final String COINS = "coins";
    public static final String CASH = "cash";

    public static final int REFERRAL_LENGTH = 6;
    public static final int MSISDN_LENGTH = 8;
    public static final int PASS_LENGTH = 4;

    /**
     * HTML
     */
    public static final String HTML_FAQ = "faq.html";
    public static final String HTML_HELP = "help.html";
    public static final String HTML_RULES = "rules.html";
    public static final String HTML_PRIVACY = "privacy.html";
    public static final String HTML_TERM_CONDITION = "terms_and_condition.html";

    /**
     * Channel
     */
    public static final int CHANNEL_TOPUP   = 1;
    public static final int CHANNEL_2       = 2;

    /**
     * Adapter games
     */
    public static final String MAGIC_WORD = "[*rewards_value*]";

    /**
     * Rewards
     */
    public static final int CHANNEL_ID	             = 1;

    /**
     * Custom Event
     *
     * 115	D7dzaWAb
     116	bVGnKw04
     117	eZd84rdP
     118	lPAZ8ZGM
     119	Z4dRODG3
     120	6Od1xKAj
     121	YEAoEBGL
     122	VLdKMl07
     123	xpAYDx0m
     124	oVAL7J0r
     125	v6GmD1d5
     126	YVAJgedM
     *
     */
    public static final String EVENT_NEW_USER_ID	             = "D7dzaWAb";
    public static final String EVENT_LOGIN_ID                    = "bVGnKw04";
    public static final String EVENT_BUNDLE_INSTALL_ID           = "eZd84rdP";
    public static final String EVENT_BUNDLE_PLAY_ID              = "lPAZ8ZGM";
    public static final String EVENT_REWARDS_CHANNEL_ID          = "Z4dRODG3";
    public static final String EVENT_REWARDS_OFFER_ID            = "6Od1xKAj";
    public static final String EVENT_REWARDS_REQUEST_ID          = "YEAoEBGL";
    public static final String EVENT_INVITE_FRIEND_SENT_ID       = "VLdKMl07";
    public static final String EVENT_LOGOUT_ID                   = "xpAYDx0m";
    public static final String EVENT_USER_UPDATE_ID              = "oVAL7J0r";
    public static final String EVENT_REWARDS_REQUEST_SUCCESS_ID  = "v6GmD1d5";
    public static final String EVENT_REWARDS_REQUEST_FAIL_ID     = "YVAJgedM";

    public static final String EVENT_NEW_USER_REGISTER	            = "register" ;
    public static final String EVENT_NEW_USER_GUEST	                = "guest" ;
    public static final String EVENT_LOGIN_REGISTER	                = "register" ;
    public static final String EVENT_LOGIN_GUEST	                = "guest" ;


    public static final String EVENT_INVITE_SMS	                    = "sms" ;
    public static final String EVENT_INVITE_EMAIL	                = "email" ;
    public static final String EVENT_INVITE_WHATSAPP                = "WhatsApp" ;
    public static final String EVENT_INVITE_FBMESSENGER             = "fbmessenger" ;

}
