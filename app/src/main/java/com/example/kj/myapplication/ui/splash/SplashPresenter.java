package com.example.kj.myapplication.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.kj.myapplication.core.BasePresenter;
import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.data.api.ApiRequestManager;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.ui.login.LoginActivity;

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
            getRequestManager().onApiError(new Callback<ApiError>() {
                @Override
                public void execute(ApiError result) {
                    // да да да константы это плохо
                    if (result.getErrorCode() == 31) {
                        Intent intent = new Intent(getView().getViewContext(), LoginActivity.class);
                        ((SplashActivity) getView().getViewContext())
                                .startActivityForResult(intent, ID);
                    }
                }
            })
        );

        regSubscribe(
            getRequestManager().onAuth(new Callback<AuthData>() {
                @Override
                public void execute(AuthData result) {
                    showProfile();
                }
            })
        );

        getRequestManager().tryAuth();
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
