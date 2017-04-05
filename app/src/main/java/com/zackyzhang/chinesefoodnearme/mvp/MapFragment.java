package com.zackyzhang.chinesefoodnearme.mvp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

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
import com.zackyzhang.chinesefoodnearme.BusinessDetailsLayout;
import com.zackyzhang.chinesefoodnearme.MapBitmapCache;
import com.zackyzhang.chinesefoodnearme.R;
import com.zackyzhang.chinesefoodnearme.maps.PulseOverlayLayout;
import com.zackyzhang.chinesefoodnearme.network.entity.Business;
import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;
import com.zackyzhang.chinesefoodnearme.transition.ScaleDownImageTransition;
import com.zackyzhang.chinesefoodnearme.transition.TransitionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lei on 3/29/17.
 */

public class MapFragment extends MvpFragment<MapFragmentContract.View, MapFragmentContract.Presenter>
        implements MapFragmentContract.View, OnMapReadyCallback, HorizontalRecyclerViewScrollListener.OnShowScaleAnimationListener, PlacesAdapter.OnPlaceClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final String TAG = "MapFragment";

    private GoogleApiClient mGoogleApiClient;
    private SearchResponse mSearchResponse;
    private Location mLastLocation;
    private List<Business> mBusinesses;
    private GoogleMap mMap;
    private PlacesAdapter mPlacesAdapter;
    private String currentTransitionName;
    private Scene detailsScene;

    @BindView(R.id.mapOverlayLayout)
    PulseOverlayLayout mapOverlayLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.container)
    FrameLayout containerLayout;

    public static Fragment newInstance(Context context) {
        MapFragment fragment = new MapFragment();
        Log.d(TAG, "run Bitmap transition");
        ScaleDownImageTransition transition = new ScaleDownImageTransition(context, MapBitmapCache.instance().getBitmap());
        transition.addTarget(context.getString(R.string.mapPlaceholderTransition));
        transition.setDuration(600);
        fragment.setEnterTransition(transition);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient(null);
        Log.d(TAG, mGoogleApiClient.toString());
        getBusinessData();
        setupMapFragment();
        setupRecyclerView();
    }

    @Override
    public void onStop() {
        super.onStop();
        App.getGoogleApiHelper().disconnect();
    }

    private void getBusinessData() {
        presenter.provideBusinessData();
    }

    private void setupMapFragment() {
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mPlacesAdapter = new PlacesAdapter(getActivity(), this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, "onMapReady");

        mapOverlayLayout.setupMap(googleMap);
        presenter.moveMapAndAddMarker();
        attachData();
    }

    private void attachData() {
        recyclerView.setAdapter(mPlacesAdapter);
        mPlacesAdapter.setPlaceList(mBusinesses);
        recyclerView.addOnScrollListener(new HorizontalRecyclerViewScrollListener(this));
    }

    @Override
    public void provideBusinessData(List<Business> places) {
        mBusinesses = places;
        Log.d(TAG, "provideBusinessData: first business name: " + mBusinesses.size());
    }

    @Override
    public void moveMapAndAddMarker() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
        mapOverlayLayout.setOnCameraIdleListener(() -> {
            for (int i = 0; i < mBusinesses.size(); i++) {
                mapOverlayLayout.createAndShowMarker(i, mBusinesses.get(i).getCoordinates());
            }
            mapOverlayLayout.setOnCameraIdleListener(null);
        });
        mapOverlayLayout.setOnCameraMoveListener(mapOverlayLayout::refresh);
    }

    @Override
    public void onBackPressedWithScene() {
        int childPosition = TransitionUtils.getItemPositionFromTransition(currentTransitionName);
        BusinessDetailsLayout.hideScene(getActivity(), containerLayout, getSharedViewByPosition(childPosition), currentTransitionName);
        notifyLayoutAfterBackPress(childPosition);
        mapOverlayLayout.onBackPressed(mLastLocation);
        detailsScene = null;
    }

    @Override
    public void drawPolylinesOnMap(ArrayList<LatLng> polylines) {
        mapOverlayLayout.addPolyline(polylines);
    }

    @Override
    public void updateMapZoomAndRegion(LatLng northeastLatLng, LatLng southwestLatLng, String duration) {
        mapOverlayLayout.animateCamera(new LatLngBounds(southwestLatLng, northeastLatLng));
        mapOverlayLayout.setOnCameraIdleListener(() -> mapOverlayLayout.drawStartAndFinishMarker(duration));
    }

    private void notifyLayoutAfterBackPress(final int childPosition) {
        containerLayout.removeAllViews();
        containerLayout.addView(recyclerView);
        recyclerView.requestLayout();
        mPlacesAdapter.notifyItemChanged(childPosition);
    }

    private View getSharedViewByPosition(final int childPosition) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            if (childPosition == recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i))) {
                return recyclerView.getChildAt(i);
            }
        }
        return null;
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
    public void onShowAnimation(int position) {
        mapOverlayLayout.showMarker(position);
    }

    @Override
    public void onPlaceClicked(View sharedImage, String transitionName, int position) {
        currentTransitionName = transitionName;
        detailsScene = BusinessDetailsLayout.showScene(getActivity(), containerLayout, sharedImage, currentTransitionName, mBusinesses.get(position));
        getRoutePointsAndAnimateMap(position);
        animateMap();
    }

    private void getRoutePointsAndAnimateMap(int position) {
        presenter.getRoutePoints(mLastLocation, position);
    }

    private void animateMap() {
        mapOverlayLayout.setOnCameraIdleListener(null);
        mapOverlayLayout.hideAllMarkers();
    }

    @Override
    public void onBackPressed() {
        if (detailsScene != null) {
            Log.d("FragmentBackStack", "detailsScene != null");
            presenter.onBackPressedWithScene();
        } else {
            Log.d("FragmentBackStack", "detailsScene == null");
            ((MainActivity) getActivity()).superOnBackPressed();
        }
    }
}
