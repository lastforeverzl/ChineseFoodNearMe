package com.zackyzhang.chinesefoodnearme.transition;

/**
 * Created by lei on 3/30/17.
 */

public class TransitionUtils {
    private static final String DEFAULT_TRANSITION_NAME = "transition";

    public static int getItemPositionFromTransition(final String transitionName) {
        return Integer.parseInt(transitionName.substring(DEFAULT_TRANSITION_NAME.length()));
    }

    public static String getRecyclerViewTransition(final int position) {
        return DEFAULT_TRANSITION_NAME + position;
    }
}
