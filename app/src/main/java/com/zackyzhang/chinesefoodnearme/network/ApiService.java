package com.zackyzhang.chinesefoodnearme.network;

import com.zackyzhang.chinesefoodnearme.data.entity.Directions;
import com.zackyzhang.chinesefoodnearme.data.entity.SearchResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lei on 3/29/17.
 */

public class ApiService {

    private static final String TAG = "GetBusiness";
    private static final String CLIENT_ID = "HRvqjoSkF7dZCKfoxT9vJA";
    private static final String CLIENT_SECRET = "EQ8Crg9FaoTLUMV9B9fYRTwTgcWy4YrhjkKwgKzSY9o7EmYUtXZTPLNvI51BXP2R";
    private static final String GOOGLE_DIRECTION_BASE_URL = "https://maps.googleapis.com/maps/api/directions/json";

    private static ApiService sInstance;
    private YelpFusionApiService mYelpFusionApiService;
    private YelpFusionAuthService mYelpFusionAuthService;
    private GoogleDirectionApiService mGoogleDirectionApiService;

    public static ApiService instance() {
        if (sInstance == null) {
            sInstance = new ApiService();
            return sInstance;
        }
        return sInstance;
    }

    private ApiService() {

    }

    public Observable<AccessToken> getAccessToken() {
        mYelpFusionAuthService = ServiceGenerator.createService(YelpFusionAuthService.class);
        return mYelpFusionAuthService.getAccessToken("client_credentials", CLIENT_ID, CLIENT_SECRET)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SearchResponse> getBusinessSearch(AccessToken accessToken, double currentLatitude, double currentLongitude) {
        Map<String, String> params = new HashMap<>();
        params.put("term", "chinese food");
        params.put("limit", "50");
        params.put("sort_by", "distance");
        params.put("open_now", "true");
        params.put("latitude", String.valueOf(currentLatitude));
        params.put("longitude", String.valueOf(currentLongitude));
        mYelpFusionApiService = ServiceGenerator.createService(YelpFusionApiService.class, accessToken);
        return mYelpFusionApiService.getBusinessSearch(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Directions> getGoogleMapDirections(String original, String destination) {
        mGoogleDirectionApiService = ServiceGenerator.createService(GoogleDirectionApiService.class);
        return mGoogleDirectionApiService.getDirections(GOOGLE_DIRECTION_BASE_URL, original, destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
