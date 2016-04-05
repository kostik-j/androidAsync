package com.example.kj.myapplication.ui;

import com.example.kj.myapplication.core.MVP.BasePresenter;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.ui.albums.AlbumsPresenter;
import com.example.kj.myapplication.ui.anketa.AnketaPresenter;
import com.example.kj.myapplication.ui.contacts.ContactsPresenter;
import com.example.kj.myapplication.ui.login.LoginPresenter;
import com.example.kj.myapplication.ui.splash.SplashActivity;
import com.example.kj.myapplication.ui.splash.SplashPresenter;

public class PresenterFactory {
    ApiRequestManager mRequestManager;
    IPreferenceProvider mPreferenceProvider;

    public PresenterFactory(
        ApiRequestManager requestManager,
        IPreferenceProvider preferenceProvider
    ) {
        mRequestManager = requestManager;
        mPreferenceProvider = preferenceProvider;
    }

    public BasePresenter getPresenter(String className) {

        if (className.equals(SplashActivity.class.getSimpleName())) {
            return new SplashPresenter(mRequestManager, mPreferenceProvider);
        }

        if (className.equals(ContactsPresenter.class.getSimpleName())) {
            return new ContactsPresenter(mRequestManager);
        }

        if (className.equals(LoginPresenter.class.getSimpleName())) {
            return new LoginPresenter(mRequestManager, mPreferenceProvider);
        }

        if (className.equals(AnketaPresenter.class.getSimpleName())) {
            return new AnketaPresenter(mRequestManager, mPreferenceProvider);
        }

        if (className.equals(AlbumsPresenter.class.getSimpleName())) {
            return new AlbumsPresenter(mRequestManager, mPreferenceProvider);
        }

        return null;
    }
}
