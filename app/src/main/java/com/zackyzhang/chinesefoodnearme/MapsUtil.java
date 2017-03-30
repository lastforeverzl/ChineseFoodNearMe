package com.zackyzhang.chinesefoodnearme;

import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by lei on 3/29/17.
 */

public class MapsUtil {

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
