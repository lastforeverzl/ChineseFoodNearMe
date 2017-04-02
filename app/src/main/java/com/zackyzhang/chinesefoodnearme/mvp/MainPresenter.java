package com.zackyzhang.chinesefoodnearme.mvp;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.zackyzhang.chinesefoodnearme.MapBitmapCache;
import com.zackyzhang.chinesefoodnearme.data.BusinessDataProvider;
import com.zackyzhang.chinesefoodnearme.network.AccessToken;
import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;
import com.zackyzhang.chinesefoodnearme.network.ServiceGenerator;
import com.zackyzhang.chinesefoodnearme.network.YelpFusionApiService;
import com.zackyzhang.chinesefoodnearme.network.YelpFusionAuthService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by lei on 3/29/17.
 */

public class MainPresenter extends MvpPresenter<MainContract.View> implements MainContract.Presenter {

    private static final String CLIENT_ID = "HRvqjoSkF7dZCKfoxT9vJA";
    private static final String CLIENT_SECRET = "EQ8Crg9FaoTLUMV9B9fYRTwTgcWy4YrhjkKwgKzSY9o7EmYUtXZTPLNvI51BXP2R";

    public static final String TAG = "MainPresenter";
    private static final int RADIUS = 16093;
    private YelpFusionApiService mYelpFusionApiService;
    private YelpFusionAuthService mYelpFusionAuthService;
    private SearchResponse mSearchResponse;

    @Override
    public void saveBitmap(Bitmap googleMap) {
        Log.d(TAG, googleMap.toString());
        MapBitmapCache.instance().putBitmap(googleMap);
    }

    @Override
    public void provideMapLatLngBounds(LatLng currentLocation) {
        getView().setMapLatLngBounds(BusinessDataProvider.instance().provideLatLngBoundsForAllPlaces(RADIUS, currentLocation));
    }

    public void fetchBusinessData(double currentLatitude, double currentLongitude) {
        getAccessToken(currentLatitude, currentLongitude);
    }

    private void getAccessToken(double currentLatitude, double currentLongitude) {
        mYelpFusionAuthService = ServiceGenerator.getAccessToken();
        mYelpFusionAuthService.getAccessToken("client_credentials", CLIENT_ID, CLIENT_SECRET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
        mYelpFusionApiService = ServiceGenerator.createService(YelpFusionApiService.class, accessToken);
        mYelpFusionApiService.getBusinessSearch(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchResponse -> {
                            Log.d(TAG, "searchResponse total: " + searchResponse.getTotal());
                            mSearchResponse = searchResponse;
//                            getView().dataProvided(searchResponse);
                        },
                        error -> Timber.tag(TAG).d(error.getMessage()),
                        () -> {
                            BusinessDataProvider.instance().initialize(mSearchResponse);
                            getView().dataFinished();
                        }
                );
    }
}
