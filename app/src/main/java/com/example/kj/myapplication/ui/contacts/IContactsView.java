package com.example.kj.myapplication.ui.contacts;

import com.example.kj.myapplication.core.IMvpView;
import com.example.kj.myapplication.entity.Contact;

import java.util.ArrayList;

interface IContactsView extends IMvpView {

    void showContacts(ArrayList<Contact> contacts);

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();
}
