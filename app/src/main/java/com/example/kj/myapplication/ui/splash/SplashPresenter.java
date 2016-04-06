package com.example.kj.myapplication.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.kj.myapplication.core.MVP.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.network.NetworkUtils;
import com.example.kj.myapplication.data.api.ApiErrorHelper;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthData;

public class SplashPresenter extends BasePresenter<ISplashView> {
    private final int ID = 1;

    private IPreferenceProvider mPreferenceProvider;

    public SplashPresenter(ApiRequestManager requestManager, IPreferenceProvider preferenceProvider) {
        super(requestManager);
        mPreferenceProvider = preferenceProvider;
    }

    @Override
    protected void onViewAttached() {
        regSubscribe(
                getRequestManager().onAuth(new Callback<AuthData>() {
                    @Override
                    public void execute(AuthData result) {
                        showProfile();
                    }
                })
        );

        NetworkUtils utils = new NetworkUtils(getView().getViewContext());
        if (!utils.hasConnection()) {
            getView().showLoginForm(ID);
        } else {
            getRequestManager().tryAuth();
        }
    }

    @Override
    public void onApiError(ApiError apiError) {
        if (ApiErrorHelper.isAuthError(apiError)) {
            getView().showLoginForm(ID);
        } else {
            super.onApiError(apiError);
        }
    }

    private void showProfile() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("mamba", "anketa", ""));
        intent.putExtra("anketaId", mPreferenceProvider.getAnketaId());
        Context context = getView().getViewContext();
        getView().close();
        context.startActivity(intent);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ID) {
            if (resultCode == Activity.RESULT_OK) {
                showProfile();
            }
        }
    }


}
