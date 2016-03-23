package com.example.kj.myapplication.ui.login;

import com.example.kj.myapplication.IMvpView;

public interface ILoginView extends IMvpView {

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();

    void showError(String errorMessage);
}
