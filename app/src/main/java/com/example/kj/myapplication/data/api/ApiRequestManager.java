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
import java.util.List;

public class ApiRequestManager {
    private EventDispatcher mEventDispatcher;
    private Handler mMainHandler;
    private IPreferenceProvider mPreferenceProvider;
    private List<String> cookie;

    public ApiRequestManager(
            EventDispatcher eventDispatcher,
            Handler mainHandler,
            IPreferenceProvider preferenceProvider
    ) {
        mMainHandler = mainHandler;
        mEventDispatcher = eventDispatcher;
        mPreferenceProvider = preferenceProvider;

        mEventDispatcher.subscribe(ApiEvents.ERROR_UPDATE_COOKIE, new Callback<List<String>>() {
            @Override
            public void execute(List<String> result) {
                cookie = result;
            }
        });

        onAuth(new Callback<AuthData>() {
            @Override
            public void execute(AuthData result) {
                mPreferenceProvider.setSecretToken(result.getSecret());
            }
        });
    }

    private RequestThread buildThread(Request request) {
        request.setCookie(cookie);
        return new RequestThread(request, mEventDispatcher, mMainHandler);
    }

    public int onAuth(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(LoginPassAuthRequest.class.getSimpleName(), callback);
    }

    public int onGetAnketa(Callback<Anketa> callback) {
        return mEventDispatcher.subscribe(AnketaRequest.class.getSimpleName(), callback);
    }

    public int onGetContacts(Callback<ArrayList<Contact>> callback) {
        return mEventDispatcher.subscribe(ContactsRequest.class.getSimpleName(), callback);
    }

    public int onGetAlbums(Callback<ArrayList<Album>> callback) {
        return mEventDispatcher.subscribe(AlbumsRequest.class.getSimpleName(), callback);
    }

    public int onAuthBySecret(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(SecretAuthRequest.class.getSimpleName(), callback);
    }

    public int onApiError(Callback<ApiError> callback) {
        return mEventDispatcher.subscribe(ApiEvents.ERROR_EVENT, callback);
    }

    public void unbindCallback(int id) {
        mEventDispatcher.unsubcribe(id);
    }



    public void auth(AuthIdentity authIdentity) {
        buildThread(new LoginPassAuthRequest(authIdentity, new JsonAuthParser())).start();
    }

    public void getAnketa(long anketaId) {
        buildThread(new AnketaRequest(anketaId, new JsonAnketaParser())).start();
    }

    public void authBySecret(SecretToken secretToken) {
        buildThread(new SecretAuthRequest(secretToken, new JsonAuthParser())).start();
    }

    public void getContacts() {
        buildThread(new ContactsRequest(new JsonContactsParser())).start();
    }

    public void getAlbums(long anketaId) {
        buildThread(new AlbumsRequest(anketaId, new JsonAlbumsParser())).start();
    }
}
