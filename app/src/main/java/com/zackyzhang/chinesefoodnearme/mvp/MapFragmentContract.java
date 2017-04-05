package com.zackyzhang.chinesefoodnearme.mvp;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.zackyzhang.chinesefoodnearme.network.entity.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 3/29/17.
 */

public interface MapFragmentContract {

    interface View extends MvpContract.MvpView {
        void provideBusinessData(List<Business> places);

        void moveMapAndAddMarker();

        void onBackPressedWithScene();

        void drawPolylinesOnMap(ArrayList<LatLng> polylines);

        void updateMapZoomAndRegion(LatLng northeastLatLng, LatLng southwestLatLng, String duration);
    }

    interface Presenter extends MvpContract.MvpPresenter<View> {
        void provideBusinessData();

        void moveMapAndAddMarker();

        void onBackPressedWithScene();

        void getRoutePoints(Location current, int position);
    }

}
