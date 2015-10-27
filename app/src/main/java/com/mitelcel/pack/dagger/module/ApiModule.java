package com.mitelcel.pack.dagger.module;

import com.mitelcel.pack.MiApp;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Module
public class ApiModule {

    private static final int TIMEOUT = 60;

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Singleton
    @Provides
    public RestAdapter provideRestAdapter(OkHttpClient okHttpClient, Endpoint endpoint){
        return new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(endpoint)
                .build();
    }

    /**
     *
     * OkHttp supports the SPDY protocol. SPDY is the basis for HTTP 2.0 and allows multiple HTTP requests
     * to be multiplexed over one socket connection.
     *
     * https://it.wikipedia.org/wiki/SPDY
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideClient(MiApp app){

        OkHttpClient client = new OkHttpClient();

        client.setConnectTimeout(TIMEOUT, SECONDS);
        client.setReadTimeout(TIMEOUT, SECONDS);
        client.setWriteTimeout(TIMEOUT, SECONDS);

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = null;
        try{
            cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(cache != null)
            client.setCache(cache);

        return client;
    }

}
