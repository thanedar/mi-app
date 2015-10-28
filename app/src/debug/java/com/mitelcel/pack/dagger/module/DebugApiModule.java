package com.mitelcel.pack.dagger.module;

import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.MockMiApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Module(includes = ApiModule.class)
public class DebugApiModule {


    @Singleton
    @Provides
    Endpoint provideEndpoint(){
        return Endpoints.newFixedEndpoint(MiApiClient.URL_STAGE);
    }

    @Singleton
    @Provides
    MiApiClient provideMiApiClient(
            RestAdapter restAdapter,
            MockRestAdapter mockRestAdapter,
            MockMiApiClient mockMiApiClient,
            @PerDebug boolean hasDebug){

        if(hasDebug){
            return mockRestAdapter.create(MiApiClient.class, mockMiApiClient);
        }

        return restAdapter.create(MiApiClient.class);
    }

    @Singleton
    @Provides
    MockRestAdapter provideMockRestAdapter(RestAdapter restAdapter){
        return MockRestAdapter.from(restAdapter);
    }


}
