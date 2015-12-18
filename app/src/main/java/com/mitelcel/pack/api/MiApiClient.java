package com.mitelcel.pack.api;

import com.mitelcel.pack.bean.api.request.BeanConfirmPin;
import com.mitelcel.pack.bean.api.request.BeanDeleteFrequentNumber;
import com.mitelcel.pack.bean.api.request.BeanGetAccountInfo;
import com.mitelcel.pack.bean.api.request.BeanGetCurrentBalance;
import com.mitelcel.pack.bean.api.request.BeanGetFrequentNumbers;
import com.mitelcel.pack.bean.api.request.BeanGetOfferList;
import com.mitelcel.pack.bean.api.request.BeanGetRecentActivity;
import com.mitelcel.pack.bean.api.request.BeanGetServiceList;
import com.mitelcel.pack.bean.api.request.BeanLogin;
import com.mitelcel.pack.bean.api.request.BeanLogout;
import com.mitelcel.pack.bean.api.request.BeanRechargeAccount;
import com.mitelcel.pack.bean.api.request.BeanRequestPin;
import com.mitelcel.pack.bean.api.request.BeanSetFrequentNumber;
import com.mitelcel.pack.bean.api.request.BeanSubmitAppInfo;
import com.mitelcel.pack.bean.api.request.BeanTransferBalance;
import com.mitelcel.pack.bean.api.request.BeanUpdateUserInfo;
import com.mitelcel.pack.bean.api.response.BeanConfirmPinResponse;
import com.mitelcel.pack.bean.api.response.BeanDeleteFrequentNumberResponse;
import com.mitelcel.pack.bean.api.response.BeanGetAccountInfoResponse;
import com.mitelcel.pack.bean.api.response.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.bean.api.response.BeanGetFrequentNumbersResponse;
import com.mitelcel.pack.bean.api.response.BeanGetOfferListResponse;
import com.mitelcel.pack.bean.api.response.BeanGetRecentActivityResponse;
import com.mitelcel.pack.bean.api.response.BeanGetServiceListResponse;
import com.mitelcel.pack.bean.api.response.BeanLoginResponse;
import com.mitelcel.pack.bean.api.response.BeanLogoutResponse;
import com.mitelcel.pack.bean.api.response.BeanRechargeAccountResponse;
import com.mitelcel.pack.bean.api.response.BeanRequestPinResponse;
import com.mitelcel.pack.bean.api.response.BeanSetFrequentNumberResponse;
import com.mitelcel.pack.bean.api.response.BeanSubmitAppInfoResponse;
import com.mitelcel.pack.bean.api.response.BeanTransferBalanceResponse;
import com.mitelcel.pack.bean.api.response.BeanUpdateUserInfoResponse;

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
