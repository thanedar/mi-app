package com.mitelcel.pack.api;

import android.content.SharedPreferences;

import com.mitelcel.pack.FakeData;
import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;
import com.mitelcel.pack.dagger.module.SharedPrefModule;

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
    SharedPreferences  sharedPreferences;

    @Override
    public void submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo, Callback<BeanSubmitAppInfoResponse> callback) {
        BeanSubmitAppInfoResponse beanSubmitAppInfoResponse = new Gson().fromJson(FakeData.RESP_SUBMIT_APP_INFO, BeanSubmitAppInfoResponse.class);
        Response response = new Response("http://fake", 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json",FakeData.RESP_SUBMIT_APP_INFO.getBytes()));
        callback.success(beanSubmitAppInfoResponse, response);
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
