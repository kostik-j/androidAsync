package com.example.kj.myapplication.ui.contacts;

import com.example.kj.myapplication.core.MVP.IMvpView;
import com.example.kj.myapplication.entity.Contact;

import java.util.ArrayList;

interface IContactsView extends IMvpView {

    void appendContacts(ArrayList<Contact> contacts);

    /**
     *
     * @param contacts
     */
    void showContacts(ArrayList<Contact> contacts);

    void setTitle(int count);

    /**
     * Показывем крутилку, мол работаем
     */
    void showProgress();

    /**
     * Скрываем крутилку
     */
    void hideProgress();
}
