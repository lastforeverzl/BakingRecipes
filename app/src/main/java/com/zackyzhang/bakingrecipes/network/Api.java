package com.zackyzhang.bakingrecipes.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by lei on 7/12/17.
 */

public class Api {

    private static Api sInstance;
    private static OkHttpClient mOkHttpClient;
    private static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static Api instance() {
        if (sInstance == null) {
            sInstance = new Api();
        }
        return sInstance;
    }

    private Api() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private HttpLoggingInterceptor logInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.tag("OkHttp").d(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    public Response execute() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();
        try {
            return mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            Timber.tag("OkHttp").e(e.getMessage());
            return null;
        }
    }
}
