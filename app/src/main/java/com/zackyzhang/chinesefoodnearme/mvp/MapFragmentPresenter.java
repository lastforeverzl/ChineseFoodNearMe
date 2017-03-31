package com.zackyzhang.chinesefoodnearme.mvp;

import com.zackyzhang.chinesefoodnearme.data.BusinessDataProvider;

/**
 * Created by lei on 3/29/17.
 */

public class MapFragmentPresenter extends MvpPresenter<MapFragmentContract.View> implements MapFragmentContract.Presenter {

    private static final int RADIUS = 16093;
    private BusinessDataProvider mBusinessDataProvider = BusinessDataProvider.instance();

    @Override
    public void provideBusinessData() {
        getView().provideBusinessData(mBusinessDataProvider.getBusinessData());
    }

    @Override
    public void moveMapAndAddMarker() {
        getView().moveMapAndAddMarker();
    }
}
