package com.mitelcel.pack.api;

import android.content.SharedPreferences;

import com.mitelcel.pack.FakeData;
import com.mitelcel.pack.api.bean.req.BeanGetAccountInfo;
import com.mitelcel.pack.api.bean.req.BeanGetCurrentBalance;
import com.mitelcel.pack.api.bean.req.BeanGetFrequentNumbers;
import com.mitelcel.pack.api.bean.req.BeanGetOfferList;
import com.mitelcel.pack.api.bean.req.BeanGetRecentActivity;
import com.mitelcel.pack.api.bean.req.BeanLogin;
import com.mitelcel.pack.api.bean.req.BeanLogout;
import com.mitelcel.pack.api.bean.req.BeanRechargeAccount;
import com.mitelcel.pack.api.bean.req.BeanSetFrequentNumber;
import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.req.BeanTransferBalance;
import com.mitelcel.pack.api.bean.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetFrequentNumbersResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetOfferListResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse;
import com.mitelcel.pack.api.bean.resp.BeanLoginResponse;
import com.mitelcel.pack.api.bean.resp.BeanLogoutResponse;
import com.mitelcel.pack.api.bean.resp.BeanRechargeAccountResponse;
import com.mitelcel.pack.api.bean.resp.BeanSetFrequentNumberResponse;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;

import com.google.gson.Gson;
import com.mitelcel.pack.api.bean.resp.BeanTransferBalanceResponse;

import java.util.Collections;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.mime.TypedByteArray;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class MockMiApiClient implements MiApiClient {

    @Inject
    public MockMiApiClient(){}

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void get_account_info(@Body BeanGetAccountInfo beanInput, Callback<BeanGetAccountInfoResponse> callback) {
        BeanGetAccountInfoResponse beanResponse = new Gson().fromJson(FakeData.RESP_GET_ACCOUNT_INFO, BeanGetAccountInfoResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_GET_ACCOUNT_INFO.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void get_current_balance(@Body BeanGetCurrentBalance beanInput, Callback<BeanGetCurrentBalanceResponse> callback) {
        BeanGetCurrentBalanceResponse beanResponse = new Gson().fromJson(FakeData.RESP_CURRENT_BALANCE, BeanGetCurrentBalanceResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_CURRENT_BALANCE.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void get_frequent_numbers(@Body BeanGetFrequentNumbers beanInput, Callback<BeanGetFrequentNumbersResponse> callback) {
        BeanGetFrequentNumbersResponse beanResponse = new Gson().fromJson(FakeData.RESP_GET_FREQUENT_NUMBERS, BeanGetFrequentNumbersResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_GET_FREQUENT_NUMBERS.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public BeanGetOfferListResponse get_offer_list(@Body BeanGetOfferList beanInput) {
        BeanGetOfferListResponse beanResponse = new Gson().fromJson(FakeData.RESP_GET_OFFER_LIST, BeanGetOfferListResponse.class);
        return beanResponse;
    }

    @Override
    public void get_recent_activity(@Body BeanGetRecentActivity beanInput, Callback<BeanGetRecentActivityResponse> callback) {
        BeanGetRecentActivityResponse beanResponse = new Gson().fromJson(FakeData.RESP_GET_RECENT_ACTIVITY, BeanGetRecentActivityResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_GET_RECENT_ACTIVITY.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void login(@Body BeanLogin beanInput, Callback<BeanLoginResponse> callback) {
        BeanLoginResponse beanResponse = new Gson().fromJson(FakeData.RESP_LOGIN, BeanLoginResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_LOGIN.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void logout(@Body BeanLogout beanInput, Callback<BeanLogoutResponse> callback) {
        BeanLogoutResponse beanResponse = new Gson().fromJson(FakeData.RESP_LOGOUT, BeanLogoutResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_LOGOUT.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void recharge_account(@Body BeanRechargeAccount beanInput, Callback<BeanRechargeAccountResponse> callback) {
        BeanRechargeAccountResponse beanResponse  = new Gson().fromJson(FakeData.RESP_RECHARGE_ACCOUNT, BeanRechargeAccountResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_RECHARGE_ACCOUNT.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void set_frequent_number(@Body BeanSetFrequentNumber beanInput, Callback<BeanSetFrequentNumberResponse> callback) {
        BeanSetFrequentNumberResponse beanResponse = new Gson().fromJson(FakeData.RESP_LOGOUT, BeanSetFrequentNumberResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_LOGOUT.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void submit_app_info(@Body BeanSubmitAppInfo beanInput, Callback<BeanSubmitAppInfoResponse> callback) {
        BeanSubmitAppInfoResponse beanResponse = new Gson().fromJson(FakeData.RESP_SUBMIT_APP_INFO, BeanSubmitAppInfoResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_SUBMIT_APP_INFO.getBytes()));
        callback.success(beanResponse, response);
    }

    @Override
    public void transfer_balance(@Body BeanTransferBalance beanInput, Callback<BeanTransferBalanceResponse> callback) {
        BeanTransferBalanceResponse beanResponse = new Gson().fromJson(FakeData.RESP_RECHARGE_ACCOUNT, BeanTransferBalanceResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_RECHARGE_ACCOUNT.getBytes()));
        callback.success(beanResponse, response);
    }

    /*@Override
    public void authenticate(@Body BeanAuthenticate beanAuthenticate, Callback<BeanAuthenticateResponse> callback) {
        BeanAuthenticateResponse beanAuthenticateResponse = new Gson().fromJson(FakeData.RESP_AUTHENTICATE, BeanAuthenticateResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_AUTHENTICATE.getBytes()));
        callback.success(beanAuthenticateResponse, response);
    }*/

    @Override
    public Observable<BeanSubmitAppInfoResponse> submit_app_info(@Body BeanSubmitAppInfo beanInput) {
        return Observable.create(new Observable.OnSubscribe<BeanSubmitAppInfoResponse>() {
            @Override
            public void call(Subscriber<? super BeanSubmitAppInfoResponse> subscriber) {
                BeanSubmitAppInfoResponse beanResponse = new Gson().fromJson(FakeData.RESP_SUBMIT_APP_INFO, BeanSubmitAppInfoResponse.class);
                subscriber.onNext(beanResponse);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<BeanGetCurrentBalanceResponse> get_current_balance(@Body BeanGetCurrentBalance beanInput) {
        return Observable.create(new Observable.OnSubscribe<BeanGetCurrentBalanceResponse>() {
            @Override
            public void call(Subscriber<? super BeanGetCurrentBalanceResponse> subscriber) {
                BeanGetCurrentBalanceResponse beanResponse = new Gson().fromJson(FakeData.RESP_CURRENT_BALANCE, BeanGetCurrentBalanceResponse.class);
                subscriber.onNext(beanResponse);
                subscriber.onCompleted();
            }
        });
    }

    /*@Override
    public Observable<BeanAuthenticateResponse> authenticate(@Body BeanAuthenticate beanAuthenticate) {
        return Observable.create(new Observable.OnSubscribe<BeanAuthenticateResponse>() {
            @Override
            public void call(Subscriber<? super BeanAuthenticateResponse> subscriber) {
                BeanAuthenticateResponse beanAuthenticateResponse = new Gson().fromJson(FakeData.RESP_AUTHENTICATE, BeanAuthenticateResponse.class);
                subscriber.onNext(beanAuthenticateResponse);
                subscriber.onCompleted();
            }
        });
    }*/

}
