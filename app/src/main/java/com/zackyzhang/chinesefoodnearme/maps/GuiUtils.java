package com.zackyzhang.chinesefoodnearme.maps;

import android.content.Context;

/**
 * Created by lei on 3/30/17.
 */

public class GuiUtils {

    public static float dpToPx(Context ctx, int dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (dp * density);
    }

    public static float pxToDp(Context ctx, int px) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (px / density);
    }

}
