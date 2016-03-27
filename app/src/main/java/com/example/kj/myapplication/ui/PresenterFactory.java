package com.example.kj.myapplication.ui;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.ui.albums.create.NewAlbumPresenter;
import com.example.kj.myapplication.ui.albums.list.AlbumsPresenter;
import com.example.kj.myapplication.ui.anketa.AnketaPresenter;
import com.example.kj.myapplication.ui.contacts.ContactsPresenter;
import com.example.kj.myapplication.ui.login.LoginPresenter;

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
        if (className.equals(ContactsPresenter.class.getSimpleName())) {
            return new ContactsPresenter(mRequestManager);
        }

        if (className.equals(LoginPresenter.class.getSimpleName())) {
            return new LoginPresenter(mRequestManager);
        }

        if (className.equals(AnketaPresenter.class.getSimpleName())) {
            return new AnketaPresenter(mRequestManager, mPreferenceProvider);
        }

        if (className.equals(AlbumsPresenter.class.getSimpleName())) {
            return new AlbumsPresenter(mRequestManager, mPreferenceProvider);
        }

        if (className.equals(NewAlbumPresenter.class.getSimpleName())) {
            return new NewAlbumPresenter(mRequestManager, mPreferenceProvider);
        }

        return null;
    }
}
