package com.example.kj.myapplication.entity;

public class Anketa {
    private long mId;
    private String mName;
    // url к фотке
    private String mPhoto;
    // интересы
    private String[] mInterests;
    // возраст
    private int mAge;
    // Приветствие
    private String mHelloText;

    public Anketa(long id, String name, String photo, String[] interests, int age, String helloText) {
        mId = id;
        mName = name;
        mPhoto = photo;
        mInterests = interests;
        if (interests == null) {
            mInterests = new String[0];
        }
        mAge = age;
        mHelloText = helloText;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public String[] getInterests() {
        return mInterests;
    }

    public int getAge() {
        return mAge;
    }

    public String getHelloText() {
        return mHelloText;
    }
}
