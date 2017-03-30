package com.zackyzhang.chinesefoodnearme.network;

import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lei on 3/29/17.
 */

public class GetBusinessData {

    private static final String TAG = "GetBusiness";

    private YelpFusionApiService mYelpFusionApiService;
    private YelpFusionAuthService mYelpFusionAuthService;
    private String clientId;
    private String clientSecret;

    public GetBusinessData(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Observable<AccessToken> getAccessToken() {
        mYelpFusionAuthService = ServiceGenerator.getAccessToken();
        return mYelpFusionAuthService.getAccessToken("client_credentials", clientId, clientSecret)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SearchResponse> getSearch(AccessToken accessToken, Map<String, String> params) {
        mYelpFusionApiService = ServiceGenerator.createService(YelpFusionApiService.class, accessToken);
        return mYelpFusionApiService.getBusinessSearch(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
