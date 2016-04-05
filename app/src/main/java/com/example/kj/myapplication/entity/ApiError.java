package com.example.kj.myapplication.entity;

public class ApiError {
    public int mErrorCode;
    public String mDetailMessage = "";
    public String mTag = "";

    public ApiError(int errorCode, String detailMessage, String tag) {
        mErrorCode = errorCode;
        mDetailMessage = detailMessage == null ? "" :detailMessage;
        mTag = tag;
    }
    public ApiError(int errorCode) {
        mErrorCode = errorCode;
    }

    public String getTag() {
        return mTag;
    }


    public int getErrorCode() {
        return mErrorCode;
    }

    public String getDetailMessage() {
        return mDetailMessage;
    }
}
