package com.example.kj.myapplication.entity;

public class ApiError {
    public int mErrorCode;
    public String mDetailMessage = "";

    public ApiError(int errorCode, String detailMessage) {
        mErrorCode = errorCode;
        mDetailMessage = detailMessage;
    }

    public ApiError(int errorCode) {
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getDetailMessage() {
        return mDetailMessage;
    }
}
