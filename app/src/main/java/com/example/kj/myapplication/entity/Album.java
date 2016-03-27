package com.example.kj.myapplication.entity;

public class Album {

    private long mId;
    private int mPhotoCount;

    private String mName;

    private String mPhoto;

    public Album(long id, String name, String photo, int photosCount) {

        mId = id;
        mName = name;
        mPhoto = photo;
        mPhotoCount = photosCount;
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

    public int getPhotoCount() {
        return mPhotoCount;
    }
}
