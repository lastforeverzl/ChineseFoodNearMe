package com.zackyzhang.chinesefoodnearme.mvp;

import com.zackyzhang.chinesefoodnearme.network.entity.Business;

import java.util.List;

/**
 * Created by lei on 3/29/17.
 */

public interface MapFragmentContract {

    interface View extends MvpContract.MvpView {
        void provideBusinessData(List<Business> places);

        void moveMapAndAddMarker();

        void onBackPressedWithScene();
    }

    interface Presenter extends MvpContract.MvpPresenter<View> {
        void provideBusinessData();

        void moveMapAndAddMarker();

        void onBackPressedWithScene();
    }

}
