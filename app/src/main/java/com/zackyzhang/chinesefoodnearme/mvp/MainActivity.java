package com.zackyzhang.chinesefoodnearme.mvp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

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
    protected void onStart() {
        super.onStart();
        App.getGoogleApiHelper().connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getGoogleApiHelper().disconnect();
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        Log.d(TAG, "Map Ready");
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
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        setUpMap();
        getBusinessData();
    }

    public void superOnBackPressed() {
        finish();
    }

    @Override
    public void onBackPressed() {
        Log.d("FragmentBackStack", "getBackStackEntryCount: " + getSupportFragmentManager().getBackStackEntryCount());
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            Log.d("FragmentBackStack", "fragment name in backstack: " + getSupportFragmentManager().getBackStackEntryAt(i).getName());
        }
        if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
            triggerFragmentBackPress(getSupportFragmentManager().getBackStackEntryCount());
        } else {
            Log.d("FragmentBackStack", "call activity finish");
            finish();
        }
    }

    private void triggerFragmentBackPress(final int count) {
        Log.d("FragmentBackStack", "trigger: " + getSupportFragmentManager().getBackStackEntryAt(count - 1).getName());
        ((MvpFragment)getSupportFragmentManager().findFragmentByTag(getSupportFragmentManager().getBackStackEntryAt(count - 1).getName())).onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult called");
                    setUpMap();
                    getBusinessData();
                } else {
                    Toast.makeText(MainActivity.this, "LOCATION_PERMISSION Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
