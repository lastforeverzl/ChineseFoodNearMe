package com.zackyzhang.chinesefoodnearme.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by lei on 3/29/17.
 */

public abstract class MvpActivity<V extends MvpContract.MvpView, P extends MvpContract.MvpPresenter> extends AppCompatActivity implements MvpContract.MvpView{

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    protected abstract P createPresenter();

    @LayoutRes
    protected abstract int getLayout();
}
