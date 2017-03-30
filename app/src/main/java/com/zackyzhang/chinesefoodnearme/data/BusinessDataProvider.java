package com.zackyzhang.chinesefoodnearme.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;
import com.zackyzhang.chinesefoodnearme.network.entity.Business;
import com.zackyzhang.chinesefoodnearme.network.entity.SearchResponse;

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
    private int radius;
    private LatLng center;

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
}
