package com.mitelcel.pack.api;

import com.mitelcel.pack.bean.api.req.BeanConfirmPin;
import com.mitelcel.pack.bean.api.req.BeanDeleteFrequentNumber;
import com.mitelcel.pack.bean.api.req.BeanGetAccountInfo;
import com.mitelcel.pack.bean.api.req.BeanGetCurrentBalance;
import com.mitelcel.pack.bean.api.req.BeanGetFrequentNumbers;
import com.mitelcel.pack.bean.api.req.BeanGetOfferList;
import com.mitelcel.pack.bean.api.req.BeanGetRecentActivity;
import com.mitelcel.pack.bean.api.req.BeanGetServiceList;
import com.mitelcel.pack.bean.api.req.BeanLogin;
import com.mitelcel.pack.bean.api.req.BeanLogout;
import com.mitelcel.pack.bean.api.req.BeanRechargeAccount;
import com.mitelcel.pack.bean.api.req.BeanRequestPin;
import com.mitelcel.pack.bean.api.req.BeanSetFrequentNumber;
import com.mitelcel.pack.bean.api.req.BeanSubmitAppInfo;
import com.mitelcel.pack.bean.api.req.BeanTransferBalance;
import com.mitelcel.pack.bean.api.req.BeanUpdateUserInfo;
import com.mitelcel.pack.bean.api.resp.BeanConfirmPinResponse;
import com.mitelcel.pack.bean.api.resp.BeanDeleteFrequentNumberResponse;
import com.mitelcel.pack.bean.api.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.bean.api.resp.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.bean.api.resp.BeanGetFrequentNumbersResponse;
import com.mitelcel.pack.bean.api.resp.BeanGetOfferListResponse;
import com.mitelcel.pack.bean.api.resp.BeanGetRecentActivityResponse;
import com.mitelcel.pack.bean.api.resp.BeanGetServiceListResponse;
import com.mitelcel.pack.bean.api.resp.BeanLoginResponse;
import com.mitelcel.pack.bean.api.resp.BeanLogoutResponse;
import com.mitelcel.pack.bean.api.resp.BeanRechargeAccountResponse;
import com.mitelcel.pack.bean.api.resp.BeanRequestPinResponse;
import com.mitelcel.pack.bean.api.resp.BeanSetFrequentNumberResponse;
import com.mitelcel.pack.bean.api.resp.BeanSubmitAppInfoResponse;
import com.mitelcel.pack.bean.api.resp.BeanTransferBalanceResponse;
import com.mitelcel.pack.bean.api.resp.BeanUpdateUserInfoResponse;

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
    void confirm_pin(@Body BeanConfirmPin beanConfirmPin, Callback<BeanConfirmPinResponse> callback);

    @POST(REQUEST_URI)
    void delete_frequent_number(@Body BeanDeleteFrequentNumber beanDeleteFrequentNumber, Callback<BeanDeleteFrequentNumberResponse> callback);

    @POST(REQUEST_URI)
    void get_account_info(@Body BeanGetAccountInfo beanGetAccountInfo, Callback<BeanGetAccountInfoResponse> callback);

    @POST(REQUEST_URI)
    void get_current_balance(@Body BeanGetCurrentBalance beanGetCurrentBalance, Callback<BeanGetCurrentBalanceResponse> callback);

    @POST(REQUEST_URI)
    void get_frequent_numbers(@Body BeanGetFrequentNumbers beanGetFrequentNumbers, Callback<BeanGetFrequentNumbersResponse> callback);

    @POST(REQUEST_URI)
    void get_offer_list(@Body BeanGetOfferList beanGetOfferList, Callback<BeanGetOfferListResponse> callback);

    @POST(REQUEST_URI)
    BeanGetOfferListResponse get_offer_list_async(@Body BeanGetOfferList beanGetOfferList);

    @POST(REQUEST_URI)
    void get_recent_activity(@Body BeanGetRecentActivity beanGetRecentActivity, Callback<BeanGetRecentActivityResponse> callback);

    @POST(REQUEST_URI)
    void get_service_list(@Body BeanGetServiceList beanGetServiceList, Callback<BeanGetServiceListResponse> callback);

    @POST(REQUEST_URI)
    void login(@Body BeanLogin beanLogin, Callback<BeanLoginResponse> callback);

    @POST(REQUEST_URI)
    void logout(@Body BeanLogout beanLogout, Callback<BeanLogoutResponse> callback);

    @POST(REQUEST_URI)
    void recharge_account(@Body BeanRechargeAccount beanRechargeAccount, Callback<BeanRechargeAccountResponse> callback);

    @POST(REQUEST_URI)
    void request_pin(@Body BeanRequestPin beanRequestPin, Callback<BeanRequestPinResponse> callback);

    @POST(REQUEST_URI)
    void set_frequent_number(@Body BeanSetFrequentNumber beanSetFrequentNumber, Callback<BeanSetFrequentNumberResponse> callback);

    @POST(REQUEST_URI)
    void submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo, Callback<BeanSubmitAppInfoResponse> callback);

    @POST(REQUEST_URI)
    void transfer_balance(@Body BeanTransferBalance beanTransferBalance, Callback<BeanTransferBalanceResponse> callback);

    @POST(REQUEST_URI)
    void update_user_info(@Body BeanUpdateUserInfo beanUpdateUserInfo, Callback<BeanUpdateUserInfoResponse> callback);

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
