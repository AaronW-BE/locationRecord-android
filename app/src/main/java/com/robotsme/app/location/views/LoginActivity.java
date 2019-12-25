package com.robotsme.app.location.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robotsme.app.location.MainActivity;
import com.robotsme.app.location.R;
import com.robotsme.app.location.api.AUser;
import com.robotsme.app.location.api.BaseApi;
import com.robotsme.app.location.constract.IRequestCallback;
import com.robotsme.app.location.utils.MLog;
import com.robotsme.app.location.utils.ToastUtil;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);

        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                handleLogin(v);
                return true;
            }
        });
    }

    public void handleLogin(View view) {
        MLog.i(inputUsername.getText().toString(), inputPassword.getText().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("username", inputUsername.getText().toString());
        data.put("password", inputPassword.getText().toString());
        new AUser().login(data, new IRequestCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user", MODE_PRIVATE);
                    MLog.i("登录成功");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putLong("loginAt", System.currentTimeMillis());
                    editor.apply();

                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtil.show(getApplication(), "登录失败");
            }

            @Override
            public void willStart() {

            }

            @Override
            public void finished() {

            }
        });
    }
}
