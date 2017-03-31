package com.zackyzhang.chinesefoodnearme.mvp;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by lei on 3/29/17.
 */

public interface MainContract {

    interface View extends MvpContract.MvpView{
        void setMapLatLngBounds(final LatLngBounds latLngBounds);

        void dataFinished();
    }

    interface Presenter extends MvpContract.MvpPresenter<View> {

        void saveBitmap(Bitmap googleMap);

        void provideMapLatLngBounds(LatLng currentLocation);

        void fetchBusinessData(double currentLatitude, double currentLongitude);
    }
}
