package com.example.kj.myapplication.entity;

public class Contact {
    private long mId;
    private String mName;
    private int mAge;
    private int mMessageCount;
    private String mPhoto;

    public Contact(long id, String name, int age, int messageCount, String photo) {
        mId = id;
        mName = name;
        mAge = age;
        mMessageCount = messageCount;
        mPhoto = photo;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    public int getMessageCount() {
        return mMessageCount;
    }

    public String getPhoto() {
        return mPhoto;
    }
}
