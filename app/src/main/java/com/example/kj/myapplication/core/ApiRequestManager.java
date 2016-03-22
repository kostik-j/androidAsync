package com.example.kj.myapplication.core;

import android.os.Handler;

import com.example.kj.myapplication.core.request.RequestEmptyInt;
import com.example.kj.myapplication.core.request.RequestEmptyString;
import com.example.kj.myapplication.core.request.RequestThread;

public class ApiRequestManager {
    private EventBus mEventBus;
    private Handler mMainHandler;

    public ApiRequestManager(EventBus eventBus, Handler mainHandler) {
        mMainHandler = mainHandler;
        mEventBus = eventBus;
    }

    public void loadTestString() {
        RequestThread thread = new RequestThread(new RequestEmptyString(), mEventBus, mMainHandler);
        thread.start();
    }

    public int onLoadTestString(Callback<String> callback) {
        return mEventBus.subscribe((new RequestEmptyString()).getName(), callback);
    }

    public void loadTestInt() {
        RequestThread thread = new RequestThread(new RequestEmptyInt(), mEventBus, mMainHandler);
        thread.start();
    }

    public int onLoadTestInt(Callback<Integer> callback) {
        return mEventBus.subscribe((new RequestEmptyInt()).getName(), callback);
    }
}
