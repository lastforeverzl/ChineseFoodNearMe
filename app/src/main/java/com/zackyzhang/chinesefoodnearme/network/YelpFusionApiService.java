package com.zackyzhang.chinesefoodnearme.network;

import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by lei on 3/28/17.
 */

public interface YelpFusionApiService {

    @GET("v3/businesses/search")
    Observable<SearchResponse> getBusinessSearch(@QueryMap Map<String, String> params);

}
