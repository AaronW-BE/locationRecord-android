package com.robotsme.app.location.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface IHttpBack {

    void onFailure(@NotNull Call call, @NotNull IOException e);

    void onResponse(@NotNull Call call, @NotNull Response response) throws IOException;
}
