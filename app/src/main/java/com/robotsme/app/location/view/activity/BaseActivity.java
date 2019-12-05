package com.robotsme.app.location.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.robotsme.app.location.model.BaseModel;
import com.robotsme.app.location.presenter.BasePresenter;
import com.robotsme.app.location.view.IBaseView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
