package com.zackyzhang.chinesefoodnearme.mvp;

import android.location.Location;

import com.google.maps.android.PolyUtil;
import com.zackyzhang.chinesefoodnearme.MapsUtil;
import com.zackyzhang.chinesefoodnearme.data.BusinessDataProvider;
import com.zackyzhang.chinesefoodnearme.data.entity.Bounds;
import com.zackyzhang.chinesefoodnearme.data.entity.Route;
import com.zackyzhang.chinesefoodnearme.network.ApiService;

import java.util.ArrayList;

/**
 * Created by lei on 3/29/17.
 */

public class MapFragmentPresenter extends MvpPresenter<MapFragmentContract.View> implements MapFragmentContract.Presenter {

    private static final String TAG = "MapFragmentPresenter";
    private static final int RADIUS = 16093;
    private BusinessDataProvider mBusinessDataProvider = BusinessDataProvider.instance();
    private ApiService mApiService;

    public MapFragmentPresenter() {
        mApiService = ApiService.instance();
    }

    @Override
    public void provideBusinessData() {
        getView().provideBusinessData(mBusinessDataProvider.getBusinessData());
    }

    @Override
    public void moveMapAndAddMarker() {
        getView().moveMapAndAddMarker();
    }

    @Override
    public void onBackPressedWithScene() {
        getView().onBackPressedWithScene();
    }

    @Override
    public void getRoutePoints(Location current, int position) {
        String original = current.getLatitude() + "," + current.getLongitude();
        String destination = mBusinessDataProvider.getLatByPosition(position) + "," + mBusinessDataProvider.getLngByPosition(position);

        mApiService.getGoogleMapDirections(original, destination)
                .subscribe(directions -> {
                    Route route = directions.getRoutes().get(0);
                    String duration = route.getLegs().get(0).getDuration().getTimeString();
                    updateMapZoomAndRegion(route.getBounds(), duration);
                    providePolylineToDraw(route.getOverviewPolyline().getPoints());
                });
    }

    private void providePolylineToDraw(String points) {
        getView().drawPolylinesOnMap(new ArrayList<>(PolyUtil.decode(points)));
    }

    private void updateMapZoomAndRegion(Bounds bounds, String duration) {
        bounds.getSouthwest().setLat(MapsUtil.increaseLatitude(bounds));
        getView().updateMapZoomAndRegion(bounds.getNortheastLatLng(), bounds.getSouthwestLatLng(), duration);
    }
}
