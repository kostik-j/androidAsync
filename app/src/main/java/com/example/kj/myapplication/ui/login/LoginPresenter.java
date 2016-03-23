package com.example.kj.myapplication.ui.login;

import android.widget.Toast;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.AuthIdentity;

final public class LoginPresenter implements ILoginPresenter {
    private ILoginView mView;
    ApiRequestManager mRequestManager;


    public LoginPresenter(ApiRequestManager requestManager) {
        mRequestManager = requestManager;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    private int authCallBackId = 0;
    @Override
    public void attachView(ILoginView view) {
        mView = view;
        authCallBackId = mRequestManager.onAuth(new Callback<AuthData>() {
            @Override
            public void execute(AuthData i) {
                Toast.makeText(LoginPresenter.this.mView.getViewContext(), i.getLogin(), Toast.LENGTH_SHORT).show();
                mView.hideProgress();
            }
        });
    }

    @Override
    public void detachView() {
        if (authCallBackId != 0) {
            mRequestManager.unbindCallback(authCallBackId);
        }
        mView = null;
    }

    @Override
    public void login(String login, String password) {
        if (isViewAttached()) {
            mView.showProgress();
            mRequestManager.auth(new AuthIdentity("kostik-j@yandex.ru", "iamworker666"));
//            mRequestManager.auth(new AuthIdentity(login, password));
        }
    }
}
