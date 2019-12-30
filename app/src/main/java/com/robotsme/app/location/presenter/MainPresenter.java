package com.robotsme.app.location.presenter;

import com.robotsme.app.location.api.IHttpBack;
import com.robotsme.app.location.model.MainModel;
import com.robotsme.app.location.view.IMainView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainPresenter extends BasePresenter<IMainView, MainModel> {

    public MainPresenter(IMainView mView) {
        super(mView);
    }

    @Override
    public MainModel createModel() {
        return new MainModel();
    }

    public void getFootpoints() {
        mModel.getFootPoints(new IHttpBack() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }
}
