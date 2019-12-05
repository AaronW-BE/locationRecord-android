package com.robotsme.app.location.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.robotsme.app.location.R;
import com.robotsme.app.location.model.LoginModel;
import com.robotsme.app.location.presenter.LoginPresenter;
import com.robotsme.app.location.utils.SharedPreferencesUtil;
import com.robotsme.app.location.view.ILoginView;

public class LoginActivity extends BaseActivity<ILoginView, LoginModel, LoginPresenter> implements ILoginView, View.OnClickListener {

    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;

    private SharedPreferencesUtil spu;

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
        usernameEt = findViewById(R.id.login_username);
        passwordEt = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        usernameEt.setText("aaronwb");
        passwordEt.setText("123456");

        spu = SharedPreferencesUtil.getInstance(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                String username = usernameEt.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    showToast(usernameEt.getHint().toString().trim());
                    return;
                }
                String pwd = passwordEt.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    showToast(passwordEt.getHint().toString().trim());
                    return;
                }
                mPresenter.login(username, pwd);
                break;
        }
    }

    @Override
    public void showViewLogin(String token) {
        spu.putToken(token);
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void failedView(String msg, int tag, boolean isRefresh) {
        showToast(msg);
    }

}
