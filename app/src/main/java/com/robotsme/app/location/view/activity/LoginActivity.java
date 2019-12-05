package com.robotsme.app.location.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.robotsme.app.location.R;
import com.robotsme.app.location.model.LoginModel;
import com.robotsme.app.location.presenter.LoginPresenter;
import com.robotsme.app.location.view.ILoginView;

public class LoginActivity extends BaseActivity<ILoginView, LoginModel, LoginPresenter> implements ILoginView {

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.login("", "");
    }

    @Override
    public void showViewLogin() {

    }

    @Override
    public void failedView(String msg, int tag, boolean isRefresh) {
        showToast(msg);
    }
}
