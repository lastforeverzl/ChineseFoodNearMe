package com.zackyzhang.chinesefoodnearme.mvp;

import android.graphics.Bitmap;

import com.zackyzhang.chinesefoodnearme.MapBitmapCache;
import com.zackyzhang.chinesefoodnearme.data.BusinessDataProvider;
import com.zackyzhang.chinesefoodnearme.data.entity.SearchResponse;
import com.zackyzhang.chinesefoodnearme.network.AccessToken;
import com.zackyzhang.chinesefoodnearme.network.ApiService;

import timber.log.Timber;

/**
 * Created by lei on 3/29/17.
 */

public class MainPresenter extends MvpPresenter<MainContract.View> implements MainContract.Presenter {

    public static final String TAG = "MainPresenter";

    private SearchResponse mSearchResponse;
    private ApiService mApiService;

    public MainPresenter() {
        mApiService = ApiService.instance();
    }

    @Override
    public void saveBitmap(Bitmap googleMap) {
        MapBitmapCache.instance().putBitmap(googleMap);
    }

    public void fetchBusinessData(double currentLatitude, double currentLongitude) {
        getAccessToken(currentLatitude, currentLongitude);
    }

    private void getAccessToken(double currentLatitude, double currentLongitude) {
        mApiService.getAccessToken()
                .subscribe(accessToken -> {
                    createService(accessToken, currentLatitude, currentLongitude);
                });
    }

    private void createService(AccessToken accessToken, double currentLatitude, double currentLongitude) {
        mApiService.getBusinessSearch(accessToken, currentLatitude, currentLongitude)
                .subscribe(searchResponse -> {
                    mSearchResponse = searchResponse;
                },
                error -> Timber.tag(TAG).d(error.getMessage()),
                () -> {
                    BusinessDataProvider.instance().initialize(mSearchResponse);
                    getView().dataFinished();
                });
    }
}
