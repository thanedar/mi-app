package com.mitelcel.pack;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class FakeData{

    public static final String RESP_GET_ACCOUNT_INFO = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0,\"message\": \"\"},\"result\": {\"current_balance\": \"123.45\",\"used_minutes\": 0,\"used_data\": \"0.00\",\"used_sms\": 0,\"user_email\": null,\"account_type\": 1}}";

    public static final String RESP_GET_OFFER_LIST  = "{ \"id\": \"1446760808058\", \"error\": { \"code\": 0, \"message\": \"\" }, \"result\": [ { \"offer_id\": \"3\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/robojet_banner.jpg\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/robojet_icon.png\", \"description\": \"Mission game with amazing 3D futuristic graphics that will keep you hooked!\", \"action_button_text\": \"Get it!\" }, { \"offer_id\": \"4\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/jumpjumpmonster_banner.jpg\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/jumpjumpmonster_icon.png\", \"description\": \"Jump Jump Monster is one of the cutest and most addicting arcade games ever!\", \"action_button_text\": \"Install\" }, { \"offer_id\": \"5\", \"url_image\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/trafficAdventures_banner.png\", \"url_icon\": \"http://images-cdn.skillsweet.com/static/skillrewards/img/trafficAdventures_icon.png\", \"description\": \"Addicting cross the road game that keeps you entertained for hours!\", \"action_button_text\": \"Get it!\" } ] }";

    public static final String RESP_GET_RECENT_ACTIVITY = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0,\"message\": \"\"},\"result\": [{\"activity_id\": \"45\",\"activity_type\": 9,\"api_method\": \"transfer_balance\",\"activity_status\": 2,\"request\": \"{\"session_id\":\"code-review-563d4b13af1fb2.81690544\",\"app_token\":\"beta-54818722c0a62bc20c9d84273365e6e523cf823e\",\"target_msisdn\":\"520000000001\",\"transfer_amount\":10}\",\"response\": \"{\"session_id\":\"code-review-563d4b13af1fb2.81690544\"}\"}]}";

    public static final String RESP_LOGIN = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"session_id\":\"code-review-563c0248101646.95925520\"}}";

    public static final String RESP_LOGOUT = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"}}";

    public static final String RESP_CURRENT_BALANCE = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"current_balance\":\"250.00\"}}";

    public static final String RESP_RECHARGE_ACCOUNT = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"session_id\":\"code-review-563c0248101646.95925520\"}}";

    public static final String RESP_SUBMIT_APP_INFO = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0, \"message\": \"\"},\"result\": {\"app_key\": \"beta-63be2793729b\"}}";

}
