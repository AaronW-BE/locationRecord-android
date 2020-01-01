package com.robotsme.app.location.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.robotsme.app.location.R;
import com.robotsme.app.location.presenter.BasePresenter;
import com.robotsme.app.location.utils.SharedPreferencesUtil;

public class StartActivity extends BaseActivity {

    private SharedPreferencesUtil spu;

    @Override
    protected int layoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        spu = SharedPreferencesUtil.getInstance(mContext);
        String token = spu.getToken();
        if (TextUtils.isEmpty(token)) {
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            startActivity(new Intent(mContext, MainActivity.class));
        }
    }
}
