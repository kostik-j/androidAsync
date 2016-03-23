package com.example.kj.myapplication.core;

import android.os.Handler;

import com.example.kj.myapplication.data.api.request.Request;

public class RequestThread extends Thread{
    final public static String NAME = "";
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
        final Object data = mRequest.loadData();
        final String name = mRequest.getName();

        mMainHandler.post(new Runnable() {
            @Override
            public void run() { mEventDispatcher.dispatch(name, data); }
        });
    }
}
