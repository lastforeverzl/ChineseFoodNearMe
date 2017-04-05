package com.zackyzhang.chinesefoodnearme.data.entity;

import com.google.gson.annotations.SerializedName;

public class Polyline {

    @SerializedName("points")
    String points;

    public String getPoints() {
        return points;
    }
}
