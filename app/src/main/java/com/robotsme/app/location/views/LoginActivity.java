package com.robotsme.app.location.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robotsme.app.location.MainActivity;
import com.robotsme.app.location.R;
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
        BaseApi.getInstance().post("http://34.92.192.218:8000/auth", data, new IRequestCallback() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(IOException e) {
                System.out.println("on failure");
                ToastUtil.show(getApplication(), "登录失败");
            }

            @Override
            public void willStart() {
                System.out.println("start");
            }

            @Override
            public void finished() {
                System.out.println("finished");
            }
        });
    }
}
