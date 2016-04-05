package com.example.kj.myapplication.ui.anketa;

import com.example.kj.myapplication.core.MVP.IMvpView;
import com.example.kj.myapplication.entity.Anketa;

interface  IAnketaView extends IMvpView {
    /**
     * Отобрахзить анкету
     * @param anketa сама анкета
     * @param isOwnAnketa true если отображаем собственную анкету
     */
    void showAnketa(Anketa anketa, boolean isOwnAnketa);

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();

    void showButtonsBlock();

    /**
     * убираем прелоадер с скнопки альбомов и показываем их количество
     * @param count
     */
    void showAlbumsCount(int count);

    /**
     * убираем прелоадер с скнопки альбомов и показываем их количество
     * @param count
     */
    void showContactsCount(int count);

    /**
     * аоказывает кнопку назад в тулбаре
     */
    void showBackButton();
}
