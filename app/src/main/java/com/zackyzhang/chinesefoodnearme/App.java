package com.zackyzhang.chinesefoodnearme;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by lei on 3/29/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

    }

}
