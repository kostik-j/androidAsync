package com.example.kj.myapplication.data.api;

import com.example.kj.myapplication.entity.ApiError;

final public class ApiErrorHelper {
    final public static int AUTH_ERROR_CODE = 31;

    public static boolean isAuthError(ApiError error) {
        return error.getErrorCode() == 31
                || error.getErrorCode() == 3;
    }
}
