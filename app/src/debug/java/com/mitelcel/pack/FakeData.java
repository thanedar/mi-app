package com.mitelcel.pack;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class FakeData{

    public static final String RESP_GET_ACCOUNT_INFO = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0,\"message\": \"\"},\"result\": {\"current_balance\": \"123.45\",\"used_minutes\": 0,\"used_data\": \"0.00\",\"used_sms\": 0,\"user_email\": null,\"account_type\": 1}}";

    public static final String RESP_GET_OFFER_LIST  = "{ \"id\": \"1446760808058\", \"error\": { \"code\": 0, \"message\": \"\" }, \"result\": [ { \"offer_id\": \"3\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/robojet_banner.jpg\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/robojet_icon.png\", \"description\": \"Mission game with amazing 3D futuristic graphics that will keep you hooked!\", \"action_button_text\": \"Get it!\" }, { \"offer_id\": \"4\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/jumpjumpmonster_banner.jpg\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/jumpjumpmonster_icon.png\", \"description\": \"Jump Jump Monster is one of the cutest and most addicting arcade games ever!\", \"action_button_text\": \"Install\" }, { \"offer_id\": \"5\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/trafficAdventures_banner.png\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/trafficAdventures_icon.png\", \"description\": \"Addicting cross the road game that keeps you entertained for hours!\", \"action_button_text\": \"Get it!\" } ] }";

    public static final String RESP_GET_BEST_OFFER  = "{ \"id\": \"1446760808058\", \"error\": { \"code\": 0, \"message\": \"\" }, \"result\": [ { \"offer_id\": \"3\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/robojet_banner.jpg\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/robojet_icon.png\", \"description\": \"Mission game with amazing 3D futuristic graphics that will keep you hooked!\", \"action_button_text\": \"Get it!\" } ] }";
    public static final String RESP_GET_FREQUENT_NUMBERS = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0,\"message\": \"\"},\"result\": [{\"msisdn\": \"5200000000\",\"order\": 1}]}";
    public static final String RESP_GET_RECENT_ACTIVITY = "{\"result\":[{\"activity_datetime\":\"2015-12-17 20:35:51\",\"response\":\"{\\\"session_id\\\":\\\"code-review-56730ac4af6eb2.64479102\\\"}\",\"request\":\"{\\\"app_token\\\":\\\"beta-ce9ddbac6074e2a726f76d7694a217e45e361b43\\\",\\\"session_id\\\":\\\"code-review-56730ac4af6eb2.64479102\\\",\\\"amount\\\":0.25}\",\"app_display_text\":\"Recharged account with $ 0.25\",\"api_method\":\"recharge_account\",\"activity_type\":10,\"activity_timestamp\":1450384551,\"activity_id\":1085,\"activity_status\":2}],\"error\":{\"code\":0,\"message\":\"\"},\"id\":\"1450460354719\"}";

    public static final String RESP_LOGIN = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"session_id\":\"code-review-563c0248101646.95925520\"}}";

    public static final String RESP_LOGOUT = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"}}";

    public static final String RESP_CURRENT_BALANCE = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"current_balance\":\"250.00\"}}";

    public static final String RESP_RECHARGE_ACCOUNT = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"session_id\":\"code-review-563c0248101646.95925520\"}}";

    public static final String RESP_SUBMIT_APP_INFO = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0, \"message\": \"\"},\"result\": {\"app_key\": \"beta-63be2793729b\"}}";

}
