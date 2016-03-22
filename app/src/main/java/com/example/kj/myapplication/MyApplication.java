package com.example.kj.myapplication;

import android.app.Application;
import android.os.Handler;

import com.example.kj.myapplication.core.ApiRequestManager;
import com.example.kj.myapplication.core.EventBus;

public class MyApplication extends Application {

    private ApiRequestManager mApiRequestManager;
    private EventBus mEventBus;

    public EventBus getEventBus() {
        if (mEventBus == null) {
            mEventBus = new EventBus();
        }
        return mEventBus;
    }

    public ApiRequestManager getApiRequestManager() {
        if (mApiRequestManager == null) {
            mApiRequestManager = new ApiRequestManager(getEventBus(), new Handler(getMainLooper()));
        }
        return mApiRequestManager;
    }
}
