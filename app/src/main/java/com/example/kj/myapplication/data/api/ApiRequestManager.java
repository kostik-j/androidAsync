package com.example.kj.myapplication.data.api;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.kj.myapplication.core.Callback;
import com.example.kj.myapplication.core.EventDispatcher;
import com.example.kj.myapplication.core.NetworkRequest;
import com.example.kj.myapplication.core.RequestThread;
import com.example.kj.myapplication.data.api.request.AlbumsRequest;
import com.example.kj.myapplication.data.api.request.ContactsRequest;
import com.example.kj.myapplication.data.api.request.TryAuthRequest;
import com.example.kj.myapplication.data.local.IPreferenceProvider;
import com.example.kj.myapplication.entity.Album;
import com.example.kj.myapplication.entity.ApiError;
import com.example.kj.myapplication.entity.AuthIdentity;
import com.example.kj.myapplication.entity.Anketa;
import com.example.kj.myapplication.entity.Contact;
import com.example.kj.myapplication.data.api.request.LoginPassAuthRequest;
import com.example.kj.myapplication.data.api.request.AnketaRequest;
import com.example.kj.myapplication.data.api.request.Request;
import com.example.kj.myapplication.entity.AuthData;
import com.example.kj.myapplication.entity.ContactCollection;
import com.example.kj.myapplication.entity.SecretToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ApiRequestManager {
    private final static String LOG_TAG = ApiRequestManager.class.getSimpleName();
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

        mEventDispatcher.subscribe(ApiEvents.ERROR_UPDATE_COOKIE, new Callback<List<String>>() {
            @Override
            public void execute(List<String> result) {
                Log.d(LOG_TAG, "Update cookie");
                mPreferenceProvider.setCookie(result);
            }
        });

        onAuth(new Callback<AuthData>() {
            @Override
            public void execute(AuthData result) {
                Log.d(LOG_TAG, "Save secret");
                mPreferenceProvider.setSecretToken(result.getSecret());
            }
        });
    }

    private RequestThread buildThread(Request request) {
        request.setNetworkRequest(new NetworkRequest());
        request.setCookie(mPreferenceProvider.getCookie());
        return new RequestThread(request, mEventDispatcher, mMainHandler);
    }

    public int onAuth(Callback<AuthData> callback) {
        return mEventDispatcher.subscribe(ApiEvents.REQUEST_AUTH, callback);
    }

    public int onGetAnketa(Callback<Anketa> callback) {
        return mEventDispatcher.subscribe(AnketaRequest.class.getSimpleName(), callback);
    }

    public int onGetContacts(Callback<ContactCollection> callback) {
        return mEventDispatcher.subscribe(ContactsRequest.class.getSimpleName(), callback);
    }

    public int onGetAlbums(Callback<ArrayList<Album>> callback) {
        return mEventDispatcher.subscribe(AlbumsRequest.class.getSimpleName(), callback);
    }

    public int onApiError(Callback<ApiError> callback) {
        return mEventDispatcher.subscribe(ApiEvents.ERROR_EVENT, callback);
    }

    public void unbindCallback(int id) {
        mEventDispatcher.unsubcribe(id);
    }



    public ApiRequestManager tryAuth() {
        SecretToken token = mPreferenceProvider.getSecretToken();
        buildThread(new TryAuthRequest(token)).start();

        return this;
    }

    public ApiRequestManager auth(AuthIdentity authIdentity) {
        buildThread(new LoginPassAuthRequest(authIdentity)).start();

        return this;
    }

    public ApiRequestManager getAnketa(long anketaId) {
        buildThread(new AnketaRequest(anketaId)).start();

        return this;
    }

    public ApiRequestManager getContacts(int offset) {
        buildThread(new ContactsRequest(offset)).start();

        return this;
    }

    public ApiRequestManager getContacts(int offset, int limit) {
        buildThread(new ContactsRequest(offset, limit)).start();

        return this;
    }

    public ApiRequestManager getAlbums(long anketaId) {
        buildThread(new AlbumsRequest(anketaId)).start();

        return this;
    }
}
