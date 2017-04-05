package com.zackyzhang.chinesefoodnearme.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lei on 4/3/17.
 */

public class Duration {

    @SerializedName("text")
    String timeString;
    @SerializedName("value")
    int value;

    public String getTimeString() {
        return timeString;
    }

    public int getValue() {
        return value;
    }
}