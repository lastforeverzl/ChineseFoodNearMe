package com.zackyzhang.chinesefoodnearme.network;

import com.zackyzhang.chinesefoodnearme.data.entity.Directions;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by lei on 4/2/17.
 */

public interface GoogleDirectionApiService {

    @GET
    Observable<Directions> getDirections(@Url String url,
                                         @Query("origin") String origin,
                                         @Query("destination") String destination);

}
