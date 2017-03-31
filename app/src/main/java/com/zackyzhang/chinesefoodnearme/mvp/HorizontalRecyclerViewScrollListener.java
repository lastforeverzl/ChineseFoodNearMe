package com.zackyzhang.chinesefoodnearme.mvp;

import android.support.v7.widget.RecyclerView;

/**
 * Created by lei on 3/30/17.
 */

public class HorizontalRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final double COVER_FACTOR = 0.9;
    private static final int OFFSET_RANGE = 50;

    private int[] mItemRanges = null;
    private final OnShowScaleAnimationListener listener;

    public interface OnShowScaleAnimationListener {
        void onShowAnimation(final int position);
    }

    public HorizontalRecyclerViewScrollListener(final OnShowScaleAnimationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mItemRanges == null) fillItemRanges(recyclerView.getAdapter().getItemCount(), recyclerView);
        for (int i = 0; i < mItemRanges.length; i++) {
            if (isInChildItemsRange(recyclerView.computeHorizontalScrollOffset(), mItemRanges[i], OFFSET_RANGE)) listener.onShowAnimation(i);
        }
    }

    private void fillItemRanges(int placesCount, RecyclerView recyclerView) {
        mItemRanges = new int[placesCount];
        int childWidth = (recyclerView.computeHorizontalScrollRange() - recyclerView.computeHorizontalScrollExtent()) / placesCount;
        for (int i = 0; i < placesCount; i++) {
            mItemRanges[i] = (int) (((childWidth * i + childWidth * (i + 1)) / 2) * COVER_FACTOR);
        }
    }

    private boolean isInChildItemsRange(final int offset, final int itemBound, final int range) {
        int rangeMin = itemBound - range;
        int rangeMax = itemBound + range;
        return (Math.min(rangeMin, rangeMax) <= offset) && (Math.max(rangeMin, rangeMax) >= offset);
    }

}
