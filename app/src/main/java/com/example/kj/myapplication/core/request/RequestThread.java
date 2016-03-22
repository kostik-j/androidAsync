package com.example.kj.myapplication.core.request;

import android.animation.Animator;
import android.os.Handler;
import android.support.v4.animation.ValueAnimatorCompat;

import com.example.kj.myapplication.core.EventBus;
import com.example.kj.myapplication.core.request.Request;

import java.util.Random;

public class RequestThread extends Thread{
    final public static String NAME = "";
    private Request mRequest;

    private EventBus mEventBus;
    private Handler mMainHandler;

    public RequestThread(Request request, EventBus eventBus, Handler mainHandler) {
        super();
        mRequest = request;
        mEventBus = eventBus;
        mMainHandler = mainHandler;
    }

    @Override
    public void run() {
        final Object data = mRequest.loadData();
        final String name = mRequest.getName();

        mMainHandler.post(new Runnable() {
            @Override
            public void run() { mEventBus.dispatch(name, data); }
        });
    }
}
