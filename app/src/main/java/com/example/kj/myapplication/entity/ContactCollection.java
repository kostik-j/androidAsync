package com.example.kj.myapplication.entity;

import java.util.ArrayList;

public class ContactCollection {
    /**
     * Общее количество контактов у пользователя
     */
    private int mTotalCount;

    /**
     * Количество контактов загружаемое с сервера за один запрос
     */
    private int mLimit;

    /**
     * сдвиг
     */
    private int mOffset;

    /**
     * Контакты
     */
    private ArrayList<Contact> mContacts;

    public ContactCollection(int totalCount, int limit, int offset, ArrayList<Contact> contacts) {
        mTotalCount = totalCount;
        mLimit = limit;
        mOffset = offset;
        mContacts = contacts;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public int getLimit() {
        return mLimit;
    }

    public int getOffset() {
        return mOffset;
    }

    public ArrayList<Contact> getContacts() {
        return mContacts;
    }

    public Boolean isLastPart() {
        return mTotalCount > mOffset + getContacts().size();
    }
}