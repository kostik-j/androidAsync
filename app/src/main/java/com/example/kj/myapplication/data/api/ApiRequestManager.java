package com.example.kj.myapplication.data.api;

import android.os.Handler;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.core.RequestThread;
import com.example.kj.myapplication.data.api.parser.JsonAlbumsParser;
import com.example.kj.myapplication.data.api.parser.JsonContactsParser;
import com.example.kj.myapplication.data.api.request.AlbumsRequest;
import com.example.kj.myapplication.data.api.request.ContactsRequest;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.entity.SecretToken;
import com.example.kj.myapplication.data.api.parser.JsonAuthParser;
import com.example.kj.myapplication.data.api.parser.JsonAnketaParser;
import com.example.kj.myapplication.data.api.request.LoginPassAuthRequest;
import com.example.kj.myapplication.data.api.request.AnketaRequest;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.data.api.request.SecretAuthRequest;
import com.example.kj.myapplication.entity.AuthData;

import java.util.ArrayList;

public class ApiRequestManager {
    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;
    private IPreferenceProvider mPreferenceProvider;

    public ApiRequestManager(
            EventDispatcher eventDispatcher,
            Handler mainHandler,
            IPreferenceProvider preferenceProvider
    ) {
        mMainHandler = mainHandler;
        mEventDispatcher = eventDispatcher;
        mPreferenceProvider = preferenceProvider;
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

    public void getAnketa(long anketaId) {
        AnketaRequest request = new AnketaRequest(anketaId, new JsonAnketaParser());
        buildThread(request).start();
    }

    public int onGetAnketa(Callback<Anketa> callback) {
        return mEventDispatcher.subscribe(AnketaRequest.class.getSimpleName(), callback);
    }

    public void authBySecret(SecretToken secretToken) {
        SecretAuthRequest request = new SecretAuthRequest(secretToken, new JsonAuthParser());
        buildThread(request).start();
    }

    public int onAuthBySecret(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(SecretAuthRequest.class.getSimpleName(), callback);
    }

    public void unbindCallback(int id) {
        mEventDispatcher.unsubcribe(id);
    }

    final static public String errorEventName = "api.error";

    public int onApiError(Callback<ApiError> callback) {
        return mEventDispatcher.subscribe(errorEventName, callback);
    }

    public void getContacts() {
        ContactsRequest request = new ContactsRequest(new JsonContactsParser());
        buildThread(request).start();
    }

    public int onGetContacts(Callback<ArrayList<Contact>> callback) {
        return mEventDispatcher.subscribe(ContactsRequest.class.getSimpleName(), callback);
    }

    public void getAlbums(long anketaId) {
        AlbumsRequest request = new AlbumsRequest(anketaId, new JsonAlbumsParser());
        buildThread(request).start();
    }

    public int onGetAlbums(Callback<ArrayList<Album>> callback) {
        return mEventDispatcher.subscribe(AlbumsRequest.class.getSimpleName(), callback);
    }
}
