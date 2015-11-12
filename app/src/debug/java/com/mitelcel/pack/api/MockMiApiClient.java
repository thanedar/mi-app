package com.mitelcel.pack.api;

import android.content.SharedPreferences;

import com.mitelcel.pack.FakeData;
import com.mitelcel.pack.api.bean.req.BeanGetAccountInfo;
import com.mitelcel.pack.api.bean.req.BeanGetCurrentBalance;
import com.mitelcel.pack.api.bean.req.BeanGetRecentActivity;
import com.mitelcel.pack.api.bean.req.BeanLogin;
import com.mitelcel.pack.api.bean.req.BeanLogout;
import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.resp.BeanGetAccountInfoResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.api.bean.resp.BeanGetRecentActivityResponse;
import com.mitelcel.pack.api.bean.resp.BeanLoginResponse;
import com.mitelcel.pack.api.bean.resp.BeanLogoutResponse;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;

import com.google.gson.Gson;

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
    public void submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo, Callback<BeanSubmitAppInfoResponse> callback) {
        BeanSubmitAppInfoResponse beanSubmitAppInfoResponse = new Gson().fromJson(FakeData.RESP_SUBMIT_APP_INFO, BeanSubmitAppInfoResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_SUBMIT_APP_INFO.getBytes()));
        callback.success(beanSubmitAppInfoResponse, response);
    }

    @Override
    public void get_account_info(@Body BeanGetAccountInfo beanGetAccountInfo, Callback<BeanGetAccountInfoResponse> callback) {
        BeanGetAccountInfoResponse beanGetAccountInfoResponse = new Gson().fromJson(FakeData.RESP_GET_ACCOUNT_INFO, BeanGetAccountInfoResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_GET_ACCOUNT_INFO.getBytes()));
        callback.success(beanGetAccountInfoResponse, response);
    }

    @Override
    public void get_recent_activity(@Body BeanGetRecentActivity beanGetRecentActivity, Callback<BeanGetRecentActivityResponse> callback) {
        BeanGetRecentActivityResponse beanGetRecentActivityResponse = new Gson().fromJson(FakeData.RESP_GET_RECENT_ACTIVITY, BeanGetRecentActivityResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_GET_RECENT_ACTIVITY.getBytes()));
        callback.success(beanGetRecentActivityResponse, response);
    }

    @Override
    public void login(@Body BeanLogin beanLogin, Callback<BeanLoginResponse> callback) {
        BeanLoginResponse beanLoginResponse = new Gson().fromJson(FakeData.RESP_LOGIN, BeanLoginResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_LOGIN.getBytes()));
        callback.success(beanLoginResponse, response);
    }

    @Override
    public void logout(@Body BeanLogout beanLogout, Callback<BeanLogoutResponse> callback) {
        BeanLogoutResponse beanLogoutResponse = new Gson().fromJson(FakeData.RESP_LOGOUT, BeanLogoutResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_LOGOUT.getBytes()));
        callback.success(beanLogoutResponse, response);
    }

    /*@Override
    public void authenticate(@Body BeanAuthenticate beanAuthenticate, Callback<BeanAuthenticateResponse> callback) {
        BeanAuthenticateResponse beanAuthenticateResponse = new Gson().fromJson(FakeData.RESP_AUTHENTICATE, BeanAuthenticateResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_AUTHENTICATE.getBytes()));
        callback.success(beanAuthenticateResponse, response);
    }*/

    @Override
    public Observable<BeanSubmitAppInfoResponse> submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo) {
        return Observable.create(new Observable.OnSubscribe<BeanSubmitAppInfoResponse>() {
            @Override
            public void call(Subscriber<? super BeanSubmitAppInfoResponse> subscriber) {
                BeanSubmitAppInfoResponse beanSubmitAppInfoResponse = new Gson().fromJson(FakeData.RESP_SUBMIT_APP_INFO, BeanSubmitAppInfoResponse.class);
                subscriber.onNext(beanSubmitAppInfoResponse);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<BeanGetCurrentBalanceResponse> get_current_balance(@Body BeanGetCurrentBalance beanGetCurrentBalance) {
        return Observable.create(new Observable.OnSubscribe<BeanGetCurrentBalanceResponse>() {
            @Override
            public void call(Subscriber<? super BeanGetCurrentBalanceResponse> subscriber) {
                BeanGetCurrentBalanceResponse beanGetCurrentBalanceResponse = new Gson().fromJson(FakeData.RESP_CURRENT_BALANCE, BeanGetCurrentBalanceResponse.class);
                subscriber.onNext(beanGetCurrentBalanceResponse);
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
