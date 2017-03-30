package com.zackyzhang.chinesefoodnearme.network;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lei on 3/28/17.
 */

public interface YelpFusionAuthService {

    @POST("oauth2/token")
    Observable<AccessToken> getAccessToken(@Query("grant_type") String grantType,
                                           @Query("client_id") String clientId,
                                           @Query("client_secret") String clientSecret);
}
