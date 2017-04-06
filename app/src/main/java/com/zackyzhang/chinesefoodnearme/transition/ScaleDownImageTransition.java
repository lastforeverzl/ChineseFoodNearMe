package com.zackyzhang.chinesefoodnearme.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by lei on 3/30/17.
 */

public class ScaleDownImageTransition extends Transition {
    private static final String TAG = "ScaleDownImageTransition";
    private static final int DEFAULT_SCALE_DOWN_FACTOR = 8;
    private static final String PROPNAME_SCALE_X= "transitions:scale_down:scale_x";
    private static final String PROPNAME_SCALE_Y= "transitions:scale_down:scale_y";

    private int targetScaleFactor = DEFAULT_SCALE_DOWN_FACTOR;

    private Context mContext;
    private Bitmap mBitmap;

    public ScaleDownImageTransition(Context context) {
        this.mContext = context;
        setInterpolator(new DecelerateInterpolator());
    }

    public ScaleDownImageTransition(Context context, Bitmap bitmap) {
        this(context);
        this.mBitmap = bitmap;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues, transitionValues.view.getScaleX(), transitionValues.view.getScaleY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues, targetScaleFactor, targetScaleFactor);
    }

    private void captureValues(TransitionValues values, final float scaleX, final float scaleY) {
        values.values.put(PROPNAME_SCALE_X, scaleX);
        values.values.put(PROPNAME_SCALE_Y, scaleY);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (endValues == null) {
            return null;
        }
        View view = endValues.view;
        if (view instanceof ImageView) {
            if (mBitmap != null) {
                view.setBackground(new BitmapDrawable(mContext.getResources(), mBitmap));
            }
            float scaleX = (float) startValues.values.get(PROPNAME_SCALE_X);
            float scaleY = (float) startValues.values.get(PROPNAME_SCALE_Y);

            float targetScaleX = (float) endValues.values.get(PROPNAME_SCALE_X);
            float targetScaleY = (float) endValues.values.get(PROPNAME_SCALE_Y);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, targetScaleX, scaleX);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, targetScaleY, scaleY);
            AnimatorSet parallelSet = new AnimatorSet();
            parallelSet.playTogether(scaleXAnimator, scaleYAnimator, ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f));
            AnimatorSet sequentialSet = new AnimatorSet();
            sequentialSet.playSequentially(parallelSet, ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f));
            return sequentialSet;
        }
        return null;
    }
}
