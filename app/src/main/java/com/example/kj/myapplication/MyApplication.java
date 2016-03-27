package com.example.kj.myapplication;

import android.app.Application;
import android.os.Handler;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.data.local.PreferenceProvider;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.ui.PresenterFactory;

public class MyApplication extends Application {

    private ApiRequestManager mApiRequestManager;
    private EventDispatcher mEventDispatcher;
    private PresenterFactory mPresenterFactory;
    private IPreferenceProvider mPreferenceProvider;

    public BasePresenter getPresenter(String name) {
        if (mPresenterFactory == null) {
            mPresenterFactory = new PresenterFactory(
                    getApiRequestManager(),
                    getPreferenceProvider()
            );
        }
        return mPresenterFactory.getPresenter(name);
    }

    public EventDispatcher getEventBus() {
        if (mEventDispatcher == null) {
            mEventDispatcher = new EventDispatcher();
        }
        return mEventDispatcher;
    }

    public IPreferenceProvider getPreferenceProvider() {
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

        mApiRequestManager.onAuth(new Callback<AuthData>() {
            @Override
            public void execute(AuthData result) {
            getPreferenceProvider().setAnketaId(result.getAnketaId());
            }
        });

        return mApiRequestManager;
    }
}
