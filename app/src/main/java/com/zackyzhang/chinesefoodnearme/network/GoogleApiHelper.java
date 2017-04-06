package com.zackyzhang.chinesefoodnearme.network;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import timber.log.Timber;

/**
 * Created by lei on 3/30/17.
 */

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();

    private static GoogleApiHelper sInstance;

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiListener mGoogleApiListener;

    public GoogleApiHelper(Context context) {
        this.context = context;
        buildGoogleApiClient();
    }

    public static GoogleApiHelper instance(Context context) {
        if (sInstance == null) {
            sInstance = new GoogleApiHelper(context);
            return sInstance;
        }
        return sInstance;
    }

    public GoogleApiClient getGoogleApiClient(GoogleApiListener googleApiListener) {
        if (googleApiListener != null) {
            this.mGoogleApiListener = googleApiListener;
        }
        return this.mGoogleApiClient;
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.tag(TAG).d("onConnected.");
        mGoogleApiListener.clientConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public interface GoogleApiListener {
        void clientConnected();
    }
}
