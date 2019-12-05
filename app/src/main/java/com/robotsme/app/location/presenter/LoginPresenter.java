package com.robotsme.app.location.presenter;

import com.robotsme.app.location.model.LoginModel;
import com.robotsme.app.location.view.ILoginView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginPresenter extends BasePresenter<ILoginView, LoginModel> {

    public LoginPresenter(ILoginView mView) {
        super(mView);
    }

    @Override
    public LoginModel createModel() {
        return new LoginModel();
    }

    public void login(String username, String password) {
        mModel.login(username, password, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (mReference == null) {
                    return;
                }
                getView().failedView(e.getMessage(), 0, true);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (mReference == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    String jsonStr = response.body().string();
                }
            }
        });
    }
}
