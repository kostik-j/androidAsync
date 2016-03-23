package com.example.kj.myapplication.core;

public interface IMvpPresenter<V> {
    void attachView(V view);
    void detachView();
}
