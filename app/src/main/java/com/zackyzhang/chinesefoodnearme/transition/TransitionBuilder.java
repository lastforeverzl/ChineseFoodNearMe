package com.zackyzhang.chinesefoodnearme.transition;

import android.transition.Transition;
import android.view.View;

/**
 * Created by lei on 3/31/17.
 */

public class TransitionBuilder {

    private Transition transition;

    public TransitionBuilder(final Transition transition) {
        this.transition = transition;
    }

    public TransitionBuilder target(View view) {
        transition.addTarget(view);
        return this;
    }

    public TransitionBuilder target(Class clazz) {
        transition.addTarget(clazz);
        return this;
    }

    public TransitionBuilder target(String target) {
        transition.addTarget(target);
        return this;
    }

    public TransitionBuilder target(int targetId) {
        transition.addTarget(targetId);
        return this;
    }

    public TransitionBuilder excludeTarget(final View view, final boolean exclude){
        transition.excludeTarget(view, exclude);
        return this;
    }

    public TransitionBuilder excludeTarget(final String targetName, final boolean exclude) {
        transition.excludeTarget(targetName, exclude);
        return this;
    }

    public TransitionBuilder link(final View from, final View to, final String transitionName) {
        from.setTransitionName(transitionName);
        to.setTransitionName(transitionName);
        transition.addTarget(transitionName);
        return this;
    }

    public Transition build() {
        return transition;
    }
}
