package com.zackyzhang.chinesefoodnearme.transition;

import android.util.Log;

/**
 * Created by lei on 3/30/17.
 */

public class TransitionUtils {
    private static final String DEFAULT_TRANSITION_NAME = "transition";

    public static int getItemPositionFromTransition(final String transitionName) {
        Log.d("TransitionUtils: ", transitionName + ", " + Integer.parseInt(transitionName.substring(DEFAULT_TRANSITION_NAME.length())));
        return Integer.parseInt(transitionName.substring(DEFAULT_TRANSITION_NAME.length()));
    }

    public static String getRecyclerViewTransition(final int position) {
        return DEFAULT_TRANSITION_NAME + position;
    }
}
