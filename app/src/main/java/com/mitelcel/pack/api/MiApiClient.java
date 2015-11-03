package com.mitelcel.pack.api;

import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;

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
    void submit_app_info(@Body BeanSubmitAppInfo beanSubmitAppInfo, Callback<BeanSubmitAppInfoResponse> callback);

    /**
     * RxJava call
     */

    /*@POST("/")
    Observable<BeanAuthenticateResponse> authenticate(@Body BeanAuthenticate beanAuthenticate);*/

}
