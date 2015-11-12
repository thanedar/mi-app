package com.mitelcel.pack;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class FakeData{

    public static final String RESP_SUBMIT_APP_INFO = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0, \"message\": \"\"},\"result\": {\"app_key\": \"beta-63be2793729b\"}}";

    public static final String RESP_GET_ACCOUNT_INFO = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0,\"message\": \"\"},\"result\": {\"current_balance\": \"123.45\",\"used_minutes\": 0,\"used_data\": \"0.00\",\"used_sms\": 0,\"user_email\": null,\"account_type\": 1}}";

    public static final String RESP_GET_RECENT_ACTIVITY = "{\"id\": \"1446760808058\",\"error\": {\"code\": 0,\"message\": \"\"},\"result\": [{\"activity_id\": \"45\",\"activity_type\": 9,\"api_method\": \"transfer_balance\",\"activity_status\": 2,\"request\": \"{\"session_id\":\"code-review-563d4b13af1fb2.81690544\",\"app_token\":\"beta-54818722c0a62bc20c9d84273365e6e523cf823e\",\"target_msisdn\":\"520000000001\",\"transfer_amount\":10}\",\"response\": \"{\"session_id\":\"code-review-563d4b13af1fb2.81690544\"}\"}]}";

    public static final String RESP_LOGIN = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"session_id\":\"code-review-563c0248101646.95925520\"}}";

    public static final String RESP_LOGOUT = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"}}}";

    public static final String RESP_CURRENT_BALANCE = "{\"id\":\"1446760808058\",\"error\":{\"code\":0,\"message\":\"\"},\"result\":{\"current_balance\":\"250.00\"}}";

}
