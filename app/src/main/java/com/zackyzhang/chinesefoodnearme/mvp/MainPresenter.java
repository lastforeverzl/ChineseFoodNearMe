package com.zackyzhang.chinesefoodnearme.mvp;

import android.graphics.Bitmap;
import android.util.Log;

import com.zackyzhang.chinesefoodnearme.MapBitmapCache;
import com.zackyzhang.chinesefoodnearme.data.BusinessDataProvider;
import com.zackyzhang.chinesefoodnearme.data.entity.SearchResponse;
import com.zackyzhang.chinesefoodnearme.network.AccessToken;
import com.zackyzhang.chinesefoodnearme.network.ApiService;

import java.util.HashMap;
import java.util.Map;

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
        Log.d(TAG, "ApiService id: " + mApiService.toString());
    }

    @Override
    public void saveBitmap(Bitmap googleMap) {
        Log.d(TAG, googleMap.toString());
        MapBitmapCache.instance().putBitmap(googleMap);
    }

    public void fetchBusinessData(double currentLatitude, double currentLongitude) {
        getAccessToken(currentLatitude, currentLongitude);
    }

    private void getAccessToken(double currentLatitude, double currentLongitude) {
        mApiService.getAccessToken()
                .subscribe(accessToken -> {
                    Log.d(TAG, "AccessToken onResponse: " + accessToken.getAccessToken());
                    createService(accessToken, currentLatitude, currentLongitude);
                });
    }

    private void createService(AccessToken accessToken, double currentLatitude, double currentLongitude) {
        Map<String, String> params = new HashMap<>();
        params.put("term", "chinese food");
        params.put("latitude", String.valueOf(currentLatitude));
        params.put("longitude", String.valueOf(currentLongitude));
        mApiService.getBusinessSearch(accessToken, params)
                .subscribe(searchResponse -> {
                    Log.d(TAG, "searchResponse total: " + searchResponse.getTotal());
                    mSearchResponse = searchResponse;
                },
                error -> Timber.tag(TAG).d(error.getMessage()),
                () -> {
                    BusinessDataProvider.instance().initialize(mSearchResponse);
                    getView().dataFinished();
                });
    }
}
