package com.zackyzhang.chinesefoodnearme.mvp;

/**
 * Created by lei on 3/29/17.
 */

public interface MvpContract {

    interface MvpView {
    }

    interface MvpPresenter<V extends MvpView> {
        void attachView(V view);
        void detachView();
    }
}
