package com.mitelcel.pack.api.bean;

import com.mitelcel.pack.bean.GenericBean;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public abstract class BeanGenericApi extends GenericBean {

    public static final String ACTION_CREATE_USER = "actionCreateUser";
    public static final String AUTHENTICATE = "authenticate";
    public static final String CREATE_TOKEN_IF_EXPIRED = "createTokenIfExpired";
    public static final String USER_VALIDATE = "userValid";
    public static final String USER_VALIDATE_MOBILE = "userValidMobile";
    public static final String USER_UPDATE = "userUpdate";
    public static final String USER_HISTORY = "userHistory";
    public static final String USER_WALLET = "userWallet";
    public static final String REWARDS_CATALOG = "rewardsCatalog";
    public static final String REWARDS_CHANNEL_LIST = "rewardsChannelList";
    public static final String GAME_GET_CONFIG = "rewardsGameConfig";
    public static final String ACTION_USER_UPDATE = "actionUserUpdate";
    public static final String REWARDS_REDIRECT = "rewardsRedirect";
    public static final String REWARDS_PRIZE = "rewardPrize";
}
