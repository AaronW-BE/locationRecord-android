package com.robotsme.app.location.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.robotsme.app.location.model.BaseModel;
import com.robotsme.app.location.presenter.BasePresenter;
import com.robotsme.app.location.view.IBaseView;

public abstract class BaseActivity<V extends IBaseView, M extends BaseModel, P extends BasePresenter<V, M>> extends AppCompatActivity{

    protected Activity mContext;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(layoutId());
        mPresenter = initPresenter();
        initView(savedInstanceState);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(View view, String msg, String action, View.OnClickListener onClickListener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction(action, onClickListener)
                .show();
    }

    public void showView(View view) {
        if (View.VISIBLE != view.getVisibility()) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void hideView (View view) {
        hideView(view, View.GONE);
    }

    public void hideView(View view, int visibility) {
        if (View.VISIBLE == view.getVisibility()) {
            view.setVisibility(visibility);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    protected abstract int layoutId();

    protected abstract P initPresenter();

    protected abstract void initView(@Nullable Bundle savedInstanceState);
}
