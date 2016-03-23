package com.example.kj.myapplication.data.api;

import android.os.Handler;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.core.RequestThread;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.data.api.parser.JsonAuthParser;
import com.example.kj.myapplication.data.api.parser.JsonProfileParser;
import com.example.kj.myapplication.data.api.request.LoginPassAuthRequest;
import com.example.kj.myapplication.data.api.request.ProfileRequest;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.data.api.request.SecretAuthRequest;
import com.example.kj.myapplication.entity.AuthData;

public class ApiRequestManager {
    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;

    public ApiRequestManager(EventDispatcher eventDispatcher, Handler mainHandler) {
        mMainHandler = mainHandler;
        mEventDispatcher = eventDispatcher;
    }

    private RequestThread buildThread(Request request) {
        return new RequestThread(request, mEventDispatcher, mMainHandler);
    }

    public void auth(AuthIdentity authIdentity) {
        LoginPassAuthRequest request = new LoginPassAuthRequest(authIdentity, new JsonAuthParser());
        buildThread(request).start();
    }

    public void profile(String sid) {
        ProfileRequest request = new ProfileRequest(sid, new JsonProfileParser());
        buildThread(request).start();
    }

    public void authBySecret(SecretToken secretToken) {
        SecretAuthRequest request = new SecretAuthRequest(secretToken, new JsonAuthParser());
        buildThread(request).start();
    }

    public int onAuth(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe("LoginPassAuthRequest", callback);
    }

    public void unbindCallback(int id) {
        mEventDispatcher.unsubcribe(id);
    }
}
