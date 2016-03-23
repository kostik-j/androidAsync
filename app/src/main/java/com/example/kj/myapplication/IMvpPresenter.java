package com.example.kj.myapplication;

public interface IMvpPresenter<V> {
    void attachView(V view);
    void detachView();
}
