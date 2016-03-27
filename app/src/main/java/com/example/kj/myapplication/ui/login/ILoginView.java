package com.example.kj.myapplication.ui.login;

import com.example.kj.myapplication.core.IMvpView;

public interface ILoginView extends IMvpView {

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();


    void close();

    void showError(String errorMessage);
}
