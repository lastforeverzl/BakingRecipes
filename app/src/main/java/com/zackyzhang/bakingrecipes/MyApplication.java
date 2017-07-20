package com.zackyzhang.bakingrecipes;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by lei on 7/12/17.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Timber.plant(new Timber.DebugTree());
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
}
