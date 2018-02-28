package com.alirezabdn.networking;

import android.util.Log;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shadoWalker on 5/9/17.
 */

public class ApiClient {

    private static final int TIMEOUT = 15;

    private static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .proxy(Proxy.NO_PROXY)
                    .build();
        }
        return okHttpClient;
    }

    public static Retrofit getClient(String baseUrl) {
        if (baseUrl.isEmpty()) {
            Log.e("Developer attention: ", "Base url is empty, have you forgot to initialize base url?");
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
