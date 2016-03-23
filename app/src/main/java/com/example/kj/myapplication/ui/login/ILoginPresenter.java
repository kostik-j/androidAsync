package com.example.kj.myapplication.ui.login;


import com.example.kj.myapplication.core.IMvpPresenter;

public interface ILoginPresenter extends IMvpPresenter<ILoginView> {

    /**
     * пытаемся авторизоваться
     *
     * @param login email/mamba-логин
     * @param password пароль
     */
    void login(String login, String password);
}
