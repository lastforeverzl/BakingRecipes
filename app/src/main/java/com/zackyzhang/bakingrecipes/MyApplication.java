package com.zackyzhang.bakingrecipes;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by lei on 7/12/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
