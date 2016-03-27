package com.example.kj.myapplication.data.api;

/**
 * Базовый класс ошибок апи вь соответствии с докой
 */
public class ApiErrorException extends Exception {
    private int mErrorCode;
    private Throwable mThrowable;

    public ApiErrorException(int errorCode) {
        mErrorCode = errorCode;
    }

    public ApiErrorException(int errorCode, Throwable throwable) {
        mErrorCode = errorCode;
        mThrowable = throwable;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public String getMessage() {
        if (mThrowable == null) {
            return "";
        }
        return mThrowable.getMessage();
    }
}
