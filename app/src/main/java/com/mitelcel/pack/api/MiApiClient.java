package com.mitelcel.pack.api;

import com.mitelcel.pack.api.bean.req.BeanGetAccountInfo;
import com.mitelcel.pack.api.bean.req.BeanGetCurrentBalance;
import com.mitelcel.pack.api.bean.req.BeanGetRecentActivity;
import com.mitelcel.pack.api.bean.req.BeanLogin;
import com.mitelcel.pack.api.bean.req.BeanLogout;
import com.mitelcel.pack.api.bean.req.BeanRechargeAccount;
import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.req.BeanTransferBalance;
import com.mitelcel.pack.api.bean.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse;
import com.mitelcel.pack.api.bean.resp.BeanLoginResponse;
import com.mitelcel.pack.api.bean.resp.BeanLogoutResponse;
import com.mitelcel.pack.api.bean.resp.BeanRechargeAccountResponse;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;
import com.mitelcel.pack.api.bean.resp.BeanTransferBalanceResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface MiApiClient {

    String URL_BETA = "http://betamitelcel.acoground.com";
    String URL_LIVE = "http://mitelcel.acoground.com";
    String REQUEST_URI = "/api.php";

    /*@POST("/")
    void authenticate(@Body BeanAuthenticate beanAuthenticate, Callback<BeanAuthenticateResponse> callback);*/

    @POST(REQUEST_URI)
    void get_account_info(@Body BeanGetAccountInfo beanGetAccountInfo, Callback<BeanGetAccountInfoResponse> callback);

    @POST(REQUEST_URI)
    void get_current_balance(@Body BeanGetCurrentBalance beanGetCurrentBalance, Callback<BeanGetCurrentBalanceResponse> callback);

    @POST(REQUEST_URI)
    void get_recent_activity(@Body BeanGetRecentActivity beanGetRecentActivity, Callback<BeanGetRecentActivityResponse> callback);

    @POST(REQUEST_URI)
    void login(@Body BeanLogin beanLogin, Callback<BeanLoginResponse> callback);

    @POST(REQUEST_URI)
    void logout(@Body BeanLogout beanLogout, Callback<BeanLogoutResponse> callback);

    @POST(REQUEST_URI)
    void recharge_account(@Body BeanRechargeAccount beanRechargeAccount, Callback<BeanRechargeAccountResponse> callback);

    @POST(REQUEST_URI)
    void submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo, Callback<BeanSubmitAppInfoResponse> callback);

    @POST(REQUEST_URI)
    void transfer_balance(@Body BeanTransferBalance beanTransferBalance, Callback<BeanTransferBalanceResponse> callback);

    /**
     * RxJava call
     */

    @POST(REQUEST_URI)
    Observable<BeanGetCurrentBalanceResponse> get_current_balance(@Body BeanGetCurrentBalance beanGetCurrentBalance);

    @POST(REQUEST_URI)
    Observable<BeanSubmitAppInfoResponse> submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo);

    /*@POST("/")
    Observable<BeanAuthenticateResponse> authenticate(@Body BeanAuthenticate beanAuthenticate);*/

}
