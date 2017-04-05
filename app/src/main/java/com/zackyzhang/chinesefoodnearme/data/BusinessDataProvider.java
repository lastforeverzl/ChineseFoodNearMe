package com.zackyzhang.chinesefoodnearme.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;
import com.zackyzhang.chinesefoodnearme.data.entity.Business;
import com.zackyzhang.chinesefoodnearme.data.entity.SearchResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lei on 3/29/17.
 */

public class BusinessDataProvider {

    private static final String TAG = "BusinessDataProvider";

    private static BusinessDataProvider sInstance;

    private SearchResponse BusinessData;
    private List<Business> mBusinesses;

    public BusinessDataProvider() {
    }

    public static BusinessDataProvider instance() {
        if (sInstance == null) {
            sInstance = new BusinessDataProvider();
            return sInstance;
        }
        return sInstance;
    }

    public void initialize(SearchResponse data) {
        this.BusinessData = data;
    }

    public SearchResponse getSearchResponse() {
        return this.BusinessData;
    }

    public List<Business> getBusinessData() {
        this.mBusinesses = new ArrayList<>();
        this.mBusinesses = this.BusinessData.getBusinesses();
        return this.mBusinesses;
    }

    public LatLngBounds provideLatLngBoundsForAllPlaces(int radius, LatLng center) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    public double getLatByPosition(int position) {
        return mBusinesses.get(position).getCoordinates().getLatitude();
    }

    public double getLngByPosition(int position) {
        return mBusinesses.get(position).getCoordinates().getLongitude();
    }
}
