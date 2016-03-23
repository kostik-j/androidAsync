package com.example.kj.myapplication.ui;

import com.example.kj.myapplication.core.IMvpPresenter;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.ui.login.LoginPresenter;

public class PresenterFactory {
    ApiRequestManager mRequestManager;

    public PresenterFactory(ApiRequestManager requestManager) {
        mRequestManager = requestManager;
    }

    public IMvpPresenter getPresenter(String className) {
        if (className.equals(LoginPresenter.class.getSimpleName())) {
            return new LoginPresenter(mRequestManager);
        }

        return null;
    }
}
