package com.zackyzhang.chinesefoodnearme.mvp;

import java.lang.ref.SoftReference;

/**
 * Created by lei on 3/29/17.
 */

public class MvpPresenter<V extends MvpContract.MvpView> implements MvpContract.MvpPresenter<V> {

    private SoftReference<V> viewReference;
    @Override
    public void attachView(V view) {
        viewReference = new SoftReference(view);
    }

    public V getView() {
        return viewReference == null ? null : viewReference.get();
    }

    @Override
    public void detachView() {
        if (viewReference != null) {
            viewReference.clear();
            viewReference = null;
        }
    }
}
