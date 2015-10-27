package com.mitelcel.pack.api;

import retrofit.RestAdapter;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class MiRestClient {

    public static synchronized MiApiClient init(){

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(MiApiClient.URL_LIVE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return adapter.create(MiApiClient.class);

    }
}
