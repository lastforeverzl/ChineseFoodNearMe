package com.zackyzhang.chinesefoodnearme.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lei on 4/2/17.
 */

public class Directions {

    @SerializedName("routes")
    List<Route> routes;
    @SerializedName("status")
    String status;

    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }
}

