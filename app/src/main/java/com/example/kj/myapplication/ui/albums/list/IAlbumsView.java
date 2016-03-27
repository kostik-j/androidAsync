package com.example.kj.myapplication.ui.albums.list;

import com.example.kj.myapplication.core.IMvpView;
import com.example.kj.myapplication.entity.Album;

import java.util.ArrayList;

interface IAlbumsView extends IMvpView {

    void showAlbums(ArrayList<Album> albumss);

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();
}
