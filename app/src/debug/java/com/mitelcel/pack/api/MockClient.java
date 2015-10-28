package com.mitelcel.pack.api;

import android.net.Uri;


import java.io.IOException;
import java.util.Collections;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 *
 * used following link:
 * http://stackoverflow.com/questions/17544751/square-retrofit-server-mock-for-testing
 *
 * This is the use
 *
 * RestAdapter.Builder builder = new RestAdapter.Builder();
 * builder.setClient(new MockClient());
 *
 */
public class MockClient implements Client{
    @Override
    public Response execute(Request request) throws IOException {

        Uri uri = Uri.parse(request.getUrl());

        String responseString = "";

        responseString = "{}";

        return new Response(
                request.getUrl(),
                200,
                "nothing",
                Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }
}
