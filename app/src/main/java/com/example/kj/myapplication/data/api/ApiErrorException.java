package com.example.kj.myapplication.data.api;

/**
 * Базовый класс ошибок апи вь соответствии с докой
 */
public class ApiErrorException extends Exception {
    private int mErrorCode;

    public ApiErrorException(int errorCode) {
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
