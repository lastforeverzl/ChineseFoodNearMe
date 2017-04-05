package com.zackyzhang.chinesefoodnearme;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.zackyzhang.chinesefoodnearme.data.entity.Bounds;

/**
 * Created by lei on 3/29/17.
 */

public class MapsUtil {

    private static final double LATITUDE_INCREASE_FACTOR = 0.7;
    public static int DEFAULT_MAP_PADDING = 500;

    public static String increaseLatitude(final Bounds bounds) {
        double southwestLat = bounds.getSouthwest().getLatD();
        double northeastLat = bounds.getNortheast().getLatD();
        double updatedLat = LATITUDE_INCREASE_FACTOR * Math.abs(northeastLat - southwestLat);
        Log.d("MapUtil", "southwestLat: " + southwestLat);
        Log.d("MapUtil", "northeastLat: " + northeastLat);
        Log.d("MapUtil", "updatedLat: " + updatedLat);
        Log.d("MapUtil", "southwestLat - updatedLat: " + (southwestLat - updatedLat));
        return String.valueOf(southwestLat - updatedLat);
    }

    public static int calculateWidth(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int calculateHeight(WindowManager windowManager, int paddingBottom) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels - paddingBottom;
    }

    public static int calculateHeight(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
