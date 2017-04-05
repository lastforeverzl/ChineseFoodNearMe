package com.zackyzhang.chinesefoodnearme.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Leg {
    @SerializedName("steps") List<Step> steps;
    @SerializedName("duration") Duration duration;

    public Duration getDuration() {
        return duration;
    }
}
