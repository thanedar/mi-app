package com.mitelcel.pack.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface MiApiClient {

    String URL_STAGE = "http://stage.skillsweet.com/api/SkillAPI";
    String URL_LIVE = "http://www.skillsweet.com/api/SkillAPI";

    /*@POST("/")
    void authenticate(@Body BeanAuthenticate beanAuthenticate, Callback<BeanAuthenticateResponse> callback);*/

    /**
     * RxJava call
     */

    /*@POST("/")
    Observable<BeanAuthenticateResponse> authenticate(@Body BeanAuthenticate beanAuthenticate);*/

}
