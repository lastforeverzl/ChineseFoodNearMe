package com.zackyzhang.chinesefoodnearme.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lei on 4/2/17.
 */

public class Route {

    @SerializedName("legs")
    List<Leg> legs;
    @SerializedName("overview_polyline")
    Polyline polyline;
    @SerializedName("bounds")
    com.zackyzhang.chinesefoodnearme.data.entity.Bounds bounds;

    public List<Leg> getLegs() {
        return legs;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public com.zackyzhang.chinesefoodnearme.data.entity.Polyline getOverviewPolyline() {
        return polyline;
    }
}
