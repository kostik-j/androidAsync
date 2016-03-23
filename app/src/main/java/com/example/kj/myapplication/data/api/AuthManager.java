package com.example.kj.myapplication.data.api;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.entity.SidToken;

public class AuthManager {
    private SidToken mSidToken;
    private SecretToken mSecretToken;
    ApiRequestManager mRequestManager;~

    /**
     * проверяем авторизован пользователь или нет
     * @return
     */
    public boolean isUserAuth() {

        return false;
    }

    public AuthManager(ApiRequestManager requestManager) {
        mRequestManager = requestManager;

        mRequestManager.onAuthBySecret(new Callback<AuthData>() {
            @Override
            public void execute(AuthData i) {
                if (i != null) {
                    mSecretToken = i.getSecret();
                    mSidToken = i.getSid();
                }
            }
        });
    }

    public boolean tryAuthBySecret() {
        if (mSecretToken == null) {
            return false;
        }
        return
    }
}
