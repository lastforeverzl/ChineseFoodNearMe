package com.zackyzhang.chinesefoodnearme;

import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;

import com.zackyzhang.chinesefoodnearme.transition.TransitionBuilder;

/**
 * Created by lei on 3/31/17.
 */

public class ShowDetailsTransitionSet extends TransitionSet {
    private static final String TITLE_TEXT_VIEW_TRANSITION_NAME = "titleTextView";
    private static final String CARD_VIEW_TRANSITION_NAME = "cardView";
    private final String transitionName;
    private final View from;
    private final BusinessDetailsLayout to;
    private final Context context;

    public ShowDetailsTransitionSet(final Context ctx, final String transitionName, final View from, final BusinessDetailsLayout to) {
        context = ctx;
        this.transitionName = transitionName;
        this.from = from;
        this.to = to;
//        addTransition(textResize());
        addTransition(slide());
        addTransition(shared());
    }

    private String titleTransitionName() {
        return transitionName + TITLE_TEXT_VIEW_TRANSITION_NAME;
    }

    private String cardviewTransitionName() {
        return transitionName + CARD_VIEW_TRANSITION_NAME;
    }

    private Transition textResize() {
        return null;
    }

    private Transition slide() {
        return new TransitionBuilder(TransitionInflater.from(context).inflateTransition(R.transition.business_details_enter_transition))
                .excludeTarget(transitionName, true)
                .excludeTarget(to.textViewTitle, true)
                .excludeTarget(to.cardViewContainer, true)
                .build();
    }

    private Transition shared() {
        return new TransitionBuilder(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
                .link(from.findViewById(R.id.rv_headerImage), to.imageViewPlaceDetails, transitionName)
                .link(from, to.cardViewContainer, cardviewTransitionName())
                .build();
    }
}
