package com.example.kj.myapplication.core;

import android.os.Handler;
import android.util.Log;

import com.example.kj.myapplication.data.api.ApiErrorException;
import com.example.kj.myapplication.data.api.ApiEvents;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.entity.ApiError;

public class RequestThread extends Thread{
    private final static String LOG_TAG = RequestThread.class.getSimpleName();

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
        final String name = mRequest.getEventName();
        try {
            final Object data = mRequest.loadData();
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mEventDispatcher.dispatch(name, data);
                }
            });

            if (mRequest.isPost()) {
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEventDispatcher.dispatch(ApiEvents.ERROR_UPDATE_COOKIE, mRequest.getCookie());
                    }
                });
            }
        } catch (ApiErrorException e) {
            final ApiError errorObject = new ApiError(e.getErrorCode(), e.getMessage());
            Log.d(LOG_TAG, String.format(
                "Api error: code='%d' message='%s' eventName='%s')",
                e.getErrorCode(),
                e.getMessage(),
                name
            ));
            mMainHandler.post(new Runnable() {
                @Override
                public void run() { mEventDispatcher.dispatch(ApiEvents.ERROR_EVENT, errorObject); }
            });
        }

    }
}