package com.example.kj.myapplication;

import android.app.Application;
import android.os.Handler;

import com.example.kj.myapplication.core.IMvpPresenter;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.data.local.PreferenceProvider;
import com.example.kj.myapplication.ui.PresenterFactory;

public class MyApplication extends Application {

    private ApiRequestManager mApiRequestManager;
    private EventDispatcher mEventDispatcher;
    private PresenterFactory mPresenterFactory;
    private PreferenceProvider mPreferenceProvider;

    public IMvpPresenter getPresenter(String name) {
        if (mPresenterFactory == null) {
            mPresenterFactory = new PresenterFactory(getApiRequestManager());
        }
        return mPresenterFactory.getPresenter(name);
    }

    public EventDispatcher getEventBus() {
        if (mEventDispatcher == null) {
            mEventDispatcher = new EventDispatcher();
        }
        return mEventDispatcher;
    }

    public PreferenceProvider getPreferenceProvider() {
        if (mPreferenceProvider == null) {
            mPreferenceProvider = new PreferenceProvider(getSharedPreferences("app_cache", MODE_PRIVATE));
        }
        return mPreferenceProvider;
    }

    public ApiRequestManager getApiRequestManager() {
        if (mApiRequestManager == null) {
            mApiRequestManager = new ApiRequestManager(
                    getEventBus(), new Handler(getMainLooper()), getPreferenceProvider());
        }
        return mApiRequestManager;
    }
}
