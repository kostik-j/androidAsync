package com.example.kj.myapplication.core;

import android.os.Handler;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.entity.ApiError;

public class RequestThread extends Thread{
    private Request mRequest;

    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;

    public RequestThread(Request request, EventDispatcher eventDispatcher, Handler mainHandler) {
        super();
        mRequest = request;
        mEventDispatcher = eventDispatcher;
        mMainHandler = mainHandler;
    }

    @Override
    public void run() {
        final Object data;
        try {
            final String name = mRequest.getClass().getSimpleName();
            data = mRequest.loadData();
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mEventDispatcher.dispatch(ApiEvents.ERROR_UPDATE_COOKIE, mRequest.getCookie());
                }
            });
            mMainHandler.post(new Runnable() {
                @Override
                public void run() { mEventDispatcher.dispatch(name, data); }
            });
        } catch (ApiErrorException e) {
            // TODO: 26.03.16 "api.error" перенести константу куданить
            final ApiError errorObject = new ApiError(e.getErrorCode(), e.getMessage());
            mMainHandler.post(new Runnable() {
                @Override
                public void run() { mEventDispatcher.dispatch(ApiEvents.ERROR_EVENT, errorObject); }
            });
        }

    }
}
