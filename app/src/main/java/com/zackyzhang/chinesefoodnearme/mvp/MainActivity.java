package com.zackyzhang.chinesefoodnearme.mvp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.zackyzhang.chinesefoodnearme.App;
import com.zackyzhang.chinesefoodnearme.GoogleApiHelper;
import com.zackyzhang.chinesefoodnearme.R;
import com.zackyzhang.chinesefoodnearme.data.BusinessDataProvider;

/**
 * Created by lei on 3/29/17.
 */

public class MainActivity extends MvpActivity<MainContract.View, MainContract.Presenter>
        implements MainContract.View, OnMapReadyCallback, GoogleApiHelper.GoogleApiListener {

    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LatLngBounds mLatLngBounds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient(this);
        Log.d(TAG, mGoogleApiClient.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getGoogleApiHelper().disconnect();
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                presenter.provideMapLatLngBounds(currentLocation);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
                mMap.setOnMapLoadedCallback(() -> mMap.snapshot(presenter::saveBitmap));
            }
        }
    }

    private void getBusinessData() {
        Log.d(TAG, "currentLocation: " + mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
        presenter.fetchBusinessData(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setMapLatLngBounds(LatLngBounds latLngBounds) {
        this.mLatLngBounds = latLngBounds;
    }

    @Override
    public void dataFinished() {
        navigateToMapFragment();
    }

    private void navigateToMapFragment() {
        Log.d(TAG, "At the end of MainActivity, total number of SearchResponse: " + BusinessDataProvider.instance().getSearchResponse().getTotal());
        if (getSupportFragmentManager().findFragmentByTag(MapFragment.TAG) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.container, MapFragment.newInstance(this), MapFragment.TAG)
                    .addToBackStack(MapFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void clientConnected() {
        setUpMap();
        getBusinessData();
    }
}
