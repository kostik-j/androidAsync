package com.example.kj.myapplication.data.api;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.entity.ApiError;

/**
 * alias
 */
public abstract class ApiErrorCallback implements Callback<ApiError> {

    public abstract void onApiError(ApiError apiError);

    @Override
    public void execute(ApiError result) {
        onApiError(result);
    }
}
