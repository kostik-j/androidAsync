package com.example.kj.myapplication.ui.login;

import android.app.Activity;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.AuthIdentity;

final public class LoginPresenter extends BasePresenter<ILoginView> {
    private IPreferenceProvider mPreferenceProvider;

    public LoginPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    @Override
    protected void onViewAttached() {
        regSubscribe(
            getRequestManager().onAuth(new Callback<AuthData>() {
                @Override
                public void execute(AuthData result) {
                    mPreferenceProvider.setSecretToken(result.getSecret());
                    getView().hideProgress();
                    getView().close(Activity.RESULT_OK);
                }
            })
        );

        regSubscribe(
                getRequestManager().onApiError(new Callback<ApiError>() {
                    @Override
                    public void execute(ApiError result) {
                        getView().hideProgress();
                    }
                })
        );
    }

    public void login(String login, String password) {
        if (isViewAttached()) {
            getView().showProgress();
            getRequestManager().auth(new AuthIdentity(login, password));
        }
    }
}
