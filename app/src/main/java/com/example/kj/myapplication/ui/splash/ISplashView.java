package com.example.kj.myapplication.ui.splash;

import com.example.kj.myapplication.core.MVP.IMvpView;

public interface ISplashView extends IMvpView {

    /**
     * закрываем вьюху
     */
    void close();

    void showLoginForm(int id);
}
