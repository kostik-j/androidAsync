package com.example.kj.myapplication.ui.albums.create;

import com.example.kj.myapplication.core.IMvpView;
import com.example.kj.myapplication.entity.Album;

import java.util.ArrayList;

interface INewAlbumView extends IMvpView {

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();
}
