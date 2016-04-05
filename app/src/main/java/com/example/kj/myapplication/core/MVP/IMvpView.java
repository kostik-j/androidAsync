package com.example.kj.myapplication.core.MVP;

import android.content.Context;

public interface IMvpView {
    Context getViewContext();

    /**
     * Отображаем ошибку
     * @param error текст ошибки
     */
    void showError(String error);
}
