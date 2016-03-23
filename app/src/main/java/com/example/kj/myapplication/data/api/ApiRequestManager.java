package com.example.kj.myapplication.data.api;

import android.os.Handler;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.core.RequestThread;
import com.example.kj.myapplication.data.local.PreferenceProvider;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.Profile;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.data.api.parser.JsonAuthParser;
import com.example.kj.myapplication.data.api.parser.JsonProfileParser;
import com.example.kj.myapplication.data.api.request.LoginPassAuthRequest;
import com.example.kj.myapplication.data.api.request.ProfileRequest;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.data.api.request.SecretAuthRequest;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.SidToken;

public class ApiRequestManager {
    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;
    private PreferenceProvider mPreferenceProvider;

    private SidToken mSidToken;
    private SecretToken mSecretToken;

    public ApiRequestManager(
            EventDispatcher eventDispatcher,
            Handler mainHandler,
            PreferenceProvider preferenceProvider
    ) {
        mMainHandler = mainHandler;
        mEventDispatcher = eventDispatcher;
        mPreferenceProvider = preferenceProvider;
        init();
    }

    /**
     * @todo: перенести в auth manager
     */
    private void init() {
        mSidToken = mPreferenceProvider.getSidToken();
        mSecretToken = mPreferenceProvider.getSecretToken();

        onAuth(new Callback<AuthData>() {
            @Override
            public void execute(AuthData i) {
                if (i == null) {
                    ApiRequestManager.this.setSidToken(null);
                    ApiRequestManager.this.setSecretToken(null);
                } else {
                    ApiRequestManager.this.setSidToken(i.getSid());
                    ApiRequestManager.this.setSecretToken(i.getSecret());
                }
            }
        });
    }

    private SecretToken getSecretToken() {
        return mSecretToken;
    }

    private void setSecretToken(SecretToken secretToken) {
        mPreferenceProvider.setSecretToken(secretToken);
    }

    private SidToken getSidToken() {
        return mSidToken;
    }

    private void setSidToken(SidToken sidToken) {
        mPreferenceProvider.setSidToken(sidToken);
    }

    private RequestThread buildThread(Request request) {
        return new RequestThread(request, mEventDispatcher, mMainHandler);
    }

    public int onAuth(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(LoginPassAuthRequest.class.getSimpleName(), callback);
    }

    public void auth(AuthIdentity authIdentity) {
        LoginPassAuthRequest request = new LoginPassAuthRequest(authIdentity, new JsonAuthParser());
        buildThread(request).start();
    }

    public void getProfile() {
        ProfileRequest request = new ProfileRequest(new JsonProfileParser());
        request.setSid(getSidToken());
        buildThread(request).start();
    }

    public int onGetProfile(Callback<Profile> callback) {
        return mEventDispatcher.subscribe(ProfileRequest.class.getSimpleName(), callback);
    }


    public void authBySecret(SecretToken secretToken) {
        SecretAuthRequest request = new SecretAuthRequest(secretToken, new JsonAuthParser());
        buildThread(request).start();
    }

    public int onAuthBySecret(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(SecretAuthRequest.class.getSimpleName(), callback);
    }

    public void setmSidToken(SidToken sidToken) {
        this.mSidToken = sidToken;
    }

    public void unbindCallback(int id) {
        mEventDispatcher.unsubcribe(id);
    }
}
