package com.example.kj.myapplication.ui.contacts;

import com.example.kj.myapplication.core.IMvpView;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.entity.ContactCollection;

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
