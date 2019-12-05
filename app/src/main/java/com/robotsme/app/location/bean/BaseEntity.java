package com.robotsme.app.location.bean;

public class BaseEntity<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
