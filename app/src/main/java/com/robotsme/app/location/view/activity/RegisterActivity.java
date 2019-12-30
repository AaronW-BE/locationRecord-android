package com.robotsme.app.location.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.robotsme.app.location.R;
import com.robotsme.app.location.presenter.BasePresenter;

public class RegisterActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }
}
