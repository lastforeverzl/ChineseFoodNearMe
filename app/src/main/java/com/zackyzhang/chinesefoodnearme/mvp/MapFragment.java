package com.zackyzhang.chinesefoodnearme.mvp;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.zackyzhang.chinesefoodnearme.MapBitmapCache;
import com.zackyzhang.chinesefoodnearme.R;
import com.zackyzhang.chinesefoodnearme.network.entity.Business;
import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;

import java.util.List;

/**
 * Created by lei on 3/29/17.
 */

public class MapFragment extends MvpFragment<MapFragmentContract.View, MapFragmentContract.Presenter>
        implements MapFragmentContract.View, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = " ";
    private static final String BUSSINESS_DATA = "BUSSINESS_DATA";

    private SearchResponse mSearchResponse;
    private List<Business> mBusinesses;
    private GoogleMap mMap;

    public static Fragment newInstance(Context context) {
        MapFragment fragment = new MapFragment();
        ScaleDownImageTransition transition = new ScaleDownImageTransition(context, MapBitmapCache.instance().getBitmap());
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBusinessData();
        setupGoogleMap();
    }

    private void getBusinessData() {
//        Bundle bundle = getArguments();
//        mSearchResponse = Parcels.unwrap(bundle.getParcelable(BUSSINESS_DATA));
//        Log.d(TAG, "mSearchResponse: " + mSearchResponse.getTotal());
        presenter.provideBusinessData();
    }

    private void setupGoogleMap() {
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void provideBusinessData(List<Business> places) {
        mBusinesses = places;
        Log.d(TAG, "provideBusinessData: first business name: " + mBusinesses.get(0).getName());
    }

    @Override
    public void moveMapAndAddMarker(LatLngBounds latLngBounds) {

    }

    @Override
    protected MapFragmentContract.Presenter createPresenter() {
        return new MapFragmentPresenter();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_map;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
