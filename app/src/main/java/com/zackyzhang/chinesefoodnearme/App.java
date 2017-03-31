package com.zackyzhang.chinesefoodnearme;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by lei on 3/29/17.
 */

public class App extends Application {

    private static App sInstance;
    private GoogleApiHelper googleApiHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        sInstance = this;
        googleApiHelper = GoogleApiHelper.instance(sInstance);
    }

    private static synchronized App getInstance() {
        return sInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }


}
