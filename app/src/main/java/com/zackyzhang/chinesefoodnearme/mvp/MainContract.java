package com.zackyzhang.chinesefoodnearme.mvp;

import android.graphics.Bitmap;

/**
 * Created by lei on 3/29/17.
 */

public interface MainContract {

    interface View extends MvpContract.MvpView{

        void dataFinished();
    }

    interface Presenter extends MvpContract.MvpPresenter<View> {

        void saveBitmap(Bitmap googleMap);

        void fetchBusinessData(double currentLatitude, double currentLongitude);
    }
}
